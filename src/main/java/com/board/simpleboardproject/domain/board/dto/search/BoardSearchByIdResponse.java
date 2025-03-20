package com.board.simpleboardproject.domain.board.dto.search;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.board.simpleboardproject.domain.comment.domain.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

public record BoardSearchByIdResponse(Long boardId,String title, String username, String post,
									  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")  LocalDateTime createdAt,
									  List<Comment> comments) {

	@Builder
	public BoardSearchByIdResponse(Long boardId, String title, String username, String post,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") LocalDateTime createdAt,
		List<Comment> comments) {
		this.boardId = boardId;
		this.title = title;
		this.username = username;
		this.post = post;
		this.createdAt = createdAt;
		this.comments = comments == null ? new ArrayList<>() : comments;
	}
}
