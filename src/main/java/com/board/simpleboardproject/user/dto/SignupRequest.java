package com.board.simpleboardproject.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

	@NotBlank(message = "사용자명은 필수 항목입니다.")
	@Pattern(regexp = "^[a-z0-9]{4,10}$")
	private String username;

	@NotBlank(message = "비밀번호는 필수 항목입니다.")
	@Pattern(regexp = "^[A-Za-z0-9]{8,15}$")
	private String password;

}
