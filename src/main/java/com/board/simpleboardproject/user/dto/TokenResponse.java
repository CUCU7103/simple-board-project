package com.board.simpleboardproject.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
	private String token;
	private String tokenType = "Bearer";
	private long expiresIn;
}
