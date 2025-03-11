package com.board.simpleboardproject.board.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.board.simpleboardproject.board.application.BoardService;
import com.board.simpleboardproject.board.dto.create.BoardCreateRequestDto;
import com.board.simpleboardproject.board.dto.create.BoardCreateResponseDto;
import com.board.simpleboardproject.global.message.Message;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/v1/boards")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@PostMapping
	public ResponseEntity<Message> createBoard(@Valid @RequestBody BoardCreateRequestDto dto) {
		BoardCreateResponseDto result = boardService.createBoard(dto);
		return ResponseEntity.status(HttpStatus.OK).body(new Message("200","게시글 생성에 성공하였습니다",result));
	}



}
