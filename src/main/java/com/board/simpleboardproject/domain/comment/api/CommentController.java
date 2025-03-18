package com.board.simpleboardproject.domain.comment.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.simpleboardproject.domain.comment.application.CommentService;
import com.board.simpleboardproject.domain.comment.dto.create.CommentCreateRequestDto;
import com.board.simpleboardproject.domain.comment.dto.create.CommentCreateResponseDto;
import com.board.simpleboardproject.global.message.Message;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Message> createComment(@Valid @RequestBody CommentCreateRequestDto dto) {
		CommentCreateResponseDto result = commentService.createComment(dto);
		return ResponseEntity.status(HttpStatus.OK).body(new Message("200","댓글 생성에 성공하였습니다",result));
	}


}
