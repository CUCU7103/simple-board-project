package com.board.simpleboardproject.domain.comment.dto.create;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record CommentCreateResponseDto(@NotBlank long commentId, @NotBlank String post,
									   @NotBlank LocalDateTime createdAt, @NotBlank String createdBy,@NotBlank String username) {

	@Builder
	public CommentCreateResponseDto(@NotBlank long commentId, @NotBlank String post,
		@NotBlank LocalDateTime createdAt, @NotBlank String createdBy, @NotBlank String username) {
		this.commentId = commentId;
		this.post = post;
		this.username = username;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
	}
}
