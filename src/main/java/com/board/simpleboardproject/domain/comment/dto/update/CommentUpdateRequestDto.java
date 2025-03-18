package com.board.simpleboardproject.domain.comment.dto.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public record CommentUpdateRequestDto(@NotBlank String post){

	@Builder
	public CommentUpdateRequestDto(@Valid String post) {
		this.post = post;
	}
}
