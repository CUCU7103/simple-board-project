package com.board.simpleboardproject.board.dto.search;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

public record BoardAllSearchResponse(String title, String username, String post,
									 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") LocalDateTime createdAt) {

	@Builder
	public BoardAllSearchResponse(String title, String username, String post, LocalDateTime createdAt) {
		this.title = title;
		this.username = username;
		this.post = post;
		this.createdAt = createdAt;
	}
}
