package com.board.simpleboardproject.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {

	/*
	 * 404 NOT_FOUND: 리소스를 찾을 수 없음
	 */
	BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
	NO_MATCH_USER(HttpStatus.NOT_FOUND, "관리자가 아닌경우 작성자만 삭제/수정할 수있습니다.");

	private final HttpStatus status;
	private final String message;

	ErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
