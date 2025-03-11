package com.board.simpleboardproject.board.dto.update;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

public record BoardUpdateResponseDto(String title, String username, String post,
									 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") LocalDateTime modifiedAt,
									 String modifiedBy) {
}
