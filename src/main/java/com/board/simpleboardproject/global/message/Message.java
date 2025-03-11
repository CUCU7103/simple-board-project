package com.board.simpleboardproject.global.message;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Message {

	private String statusCode;
	private String message;
	private Object data;

	@Builder
	public Message(String statusCode, String message, Object data) {
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}

	@Builder
	public Message(String statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}
}
