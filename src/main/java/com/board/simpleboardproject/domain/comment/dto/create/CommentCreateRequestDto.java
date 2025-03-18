package com.board.simpleboardproject.domain.comment.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


public record CommentCreateRequestDto(@NotBlank String post, @NotNull long boardId) {

	@Builder
	public CommentCreateRequestDto(@NotBlank String post,
		@NotBlank long boardId) {
		this.post = post;
		this.boardId = boardId;
	}
}
