package com.board.simpleboardproject.user.application;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.simpleboardproject.global.security.jwt.JwtTokenProvider;
import com.board.simpleboardproject.user.dao.UserRepository;
import com.board.simpleboardproject.user.domain.User;
import com.board.simpleboardproject.user.dto.LoginRequest;
import com.board.simpleboardproject.user.dto.SignupRequest;
import com.board.simpleboardproject.user.dto.TokenResponse;
import com.board.simpleboardproject.user.model.Role;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;

	@Transactional
	public void signup(SignupRequest request) {
		// 중복 사용자 확인
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("이미 존재하는 사용자명입니다: " + request.getUsername());
		}

		// 새 사용자 생성
		User user = User.builder()
			.username(request.getUsername())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(Role.USER)
			.build();

		userRepository.save(user);
	}

	@Transactional
	public TokenResponse login(LoginRequest request) {
		// 인증 시도
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = (User)authentication.getPrincipal();

		// 토큰 생성
		String token = jwtTokenProvider.createToken(user);

		return TokenResponse.builder()
			.token(token)
			.tokenType("Bearer")
			.expiresIn(jwtTokenProvider.getTokenExpirationTime(token))
			.build();
	}

}
