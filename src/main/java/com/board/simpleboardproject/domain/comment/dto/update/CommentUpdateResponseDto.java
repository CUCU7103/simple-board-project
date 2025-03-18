package com.board.simpleboardproject.domain.comment.dto.update;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record CommentUpdateResponseDto(@NotBlank long commentId, @NotBlank String post,
									   @NotBlank @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") LocalDateTime modifiedAt,
									   @NotBlank String modifiedBy, @NotBlank String username) {

	@Builder
	public CommentUpdateResponseDto(@NotBlank long commentId, @NotBlank String post,
		@NotBlank LocalDateTime modifiedAt, @NotBlank String modifiedBy, @NotBlank String username) {
		this.commentId = commentId;
		this.post = post;
		this.username = username;
		this.modifiedAt = modifiedAt;
		this.modifiedBy = modifiedBy;
	}
}
