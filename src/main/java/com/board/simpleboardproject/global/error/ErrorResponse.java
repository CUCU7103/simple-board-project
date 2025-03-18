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
