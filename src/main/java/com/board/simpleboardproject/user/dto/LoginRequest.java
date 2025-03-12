package com.board.simpleboardproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotBlank(message = "사용자명은 필수 항목입니다.")
	private String username;

	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	private String password;

}
