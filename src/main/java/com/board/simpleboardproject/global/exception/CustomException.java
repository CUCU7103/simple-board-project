package com.board.simpleboardproject.global.exception;

import com.board.simpleboardproject.global.error.ErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

}
