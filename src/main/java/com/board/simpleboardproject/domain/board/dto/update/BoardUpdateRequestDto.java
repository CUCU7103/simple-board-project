package com.board.simpleboardproject.domain.board.dto.update;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record BoardUpdateRequestDto(@NotBlank String title, @NotBlank String post, @NotBlank String username,
									String boardPassword) {
	@Builder
	public BoardUpdateRequestDto(String title, String post, String username, String boardPassword) {
		this.title = title;
		this.post = post;
		this.username = username;
		this.boardPassword = boardPassword;
	}
}
