package com.board.simpleboardproject.global.security.handler;


import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.board.simpleboardproject.global.security.error.SecurityErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		log.error("인증되지 않은 사용자 접근: {}", authException.getMessage());

		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드

		SecurityErrorResponse securityErrorResponse = new SecurityErrorResponse("FORBIDDEN",
			"해당 리소스에 접근할 권한이 없습니다.");

		response.getWriter().write(objectMapper.writeValueAsString(securityErrorResponse));
	}

}
