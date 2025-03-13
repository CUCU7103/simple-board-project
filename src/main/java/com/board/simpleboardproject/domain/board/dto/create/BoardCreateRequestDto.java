package com.board.simpleboardproject.domain.board.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public record BoardCreateRequestDto(@NotBlank String title, @NotBlank String username, @NotBlank @Pattern(
	regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{4}$",
	message = "비밀번호는 알파벳과 숫자를 모두 포함한 4자리여야 합니다."
) String boardPassword, @NotBlank String post) {

	@Builder
	public BoardCreateRequestDto(String title, String username, String boardPassword, String post) {
		this.title = title;
		this.username = username;
		this.boardPassword = boardPassword;
		this.post = post;
	}
}
