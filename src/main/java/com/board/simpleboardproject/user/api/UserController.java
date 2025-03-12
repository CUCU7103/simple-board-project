package com.board.simpleboardproject.user.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.simpleboardproject.global.message.Message;
import com.board.simpleboardproject.user.application.UserService;
import com.board.simpleboardproject.user.dto.LoginRequest;
import com.board.simpleboardproject.user.dto.SignupRequest;
import com.board.simpleboardproject.user.dto.TokenResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<Message> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		userService.signup(signupRequest);
		return ResponseEntity.status(HttpStatus.OK).body(new Message("200","회원가입에 성공하였습니다"));
	}

	@PostMapping("/login")
	public ResponseEntity<Message> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		TokenResponse tokenResponse = userService.login(loginRequest);
		HttpHeaders headers = new HttpHeaders();
		// 토큰을 Header에 담아서 반환
		headers.set("Authorization", tokenResponse.getTokenType()+" "+tokenResponse.getToken());
		// 만료 시간 추가
		headers.add("Token-Expires-In", String.valueOf(tokenResponse.getExpiresIn()));
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(new Message("200","로그인에 성공하였습니다"));
	}


	@PostMapping("/logout")
	public ResponseEntity<Message> logout() {
		// 빈 토큰 또는 즉시 만료되는 토큰 생성
		String emptyToken = "";  // 또는 유효하지 않은 토큰 문자열

		return ResponseEntity.status(HttpStatus.OK)
			.header("Authorization", "Bearer " + emptyToken)
			.body(new Message("200", " 사용자가 로그아웃되었습니다."));
	}

}
