package com.board.simpleboardproject.domain.user.application;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.simpleboardproject.global.security.jwt.JwtTokenProvider;
import com.board.simpleboardproject.domain.user.dao.UserRepository;
import com.board.simpleboardproject.domain.user.domain.User;
import com.board.simpleboardproject.domain.user.dto.LoginRequest;
import com.board.simpleboardproject.domain.user.dto.SignupRequest;
import com.board.simpleboardproject.domain.user.dto.TokenResponse;
import com.board.simpleboardproject.domain.user.model.Role;

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
		//authenticationManager.authenticate() 호출은 Spring Security의 인증 과정을 시작합니다
		// 사용자를 찾고 인증이 성공하면, Spring Security는 UserDetailsService에서 반환된 UserDetails 객체(귀하의 경우 User 객체)를 사용합니다.
		// 이 시점에서 Spring Security는 User 객체의 getAuthorities() 메서드를 호출하여 인증된 사용자의 권한을 가져옵니다.
		Authentication authentication = authenticationManager.authenticate(
			// 사용자명과, 비밀번호 검증 진행
			new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
		);
		// 인증이 성공하면, 인증 객체(이미 getAuthorities()에서 가져온 권한 정보 포함)가 SecurityContextHolder에 저장됩니다.
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// authentication.getPrincipal() 메서드는 인증된 사용자의 주체(principal) 객체를 반환하는 역할
		/**
		 * Principal 개념:
		 * Spring Security에서 "principal"은 인증된 사용자를 나타내는 객체입니다.
		 * 일반적으로 이것은 UserDetails 인터페이스를 구현한 객체입니다(귀하의 코드에서는 User 클래스).
		 * 메서드 역할:
		 * authentication.getPrincipal()은 인증 과정에서 성공적으로 인증된 사용자 정보를 담고 있는 객체를 반환합니다.
		 * 반환 타입은 Object이기 때문에 (User) 캐스팅을 통해 구체적인 User 타입으로 변환하고 있습니다.
		 */
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
