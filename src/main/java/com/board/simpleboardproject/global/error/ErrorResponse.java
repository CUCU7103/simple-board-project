package com.board.simpleboardproject.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.board.simpleboardproject.global.exception.CustomException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

	private final HttpStatus status;
	private final String message;
	private final String codeName;


	// ErrorCode를 받는 정적 팩토리 메소드로 변경
	public static ErrorResponse of(ErrorCode error) {
		return ErrorResponse.builder()
			.status(error.getStatus())
			.message(error.getMessage())
			.codeName(error.name())
			.build();
	}

	public static ResponseEntity<ErrorResponse> errorResponse(CustomException e) {
		return ResponseEntity
			.status(e.getErrorCode().getStatus())
			.body(ErrorResponse.builder()
				.status(e.getErrorCode().getStatus())
				.message(e.getErrorCode().getMessage())
				.codeName(e.getErrorCode().name())
				.build());
	}

}
