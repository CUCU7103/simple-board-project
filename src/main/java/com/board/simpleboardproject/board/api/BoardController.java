package com.board.simpleboardproject.board.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.board.simpleboardproject.board.application.BoardService;
import com.board.simpleboardproject.board.dto.create.BoardCreateRequestDto;
import com.board.simpleboardproject.board.dto.create.BoardCreateResponseDto;
import com.board.simpleboardproject.board.dto.search.BoardAllSearchResponse;
import com.board.simpleboardproject.board.dto.search.BoardSearchByIdResponse;
import com.board.simpleboardproject.board.dto.update.BoardUpdateRequestDto;
import com.board.simpleboardproject.board.dto.update.BoardUpdateResponseDto;
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

	@GetMapping
	public ResponseEntity<Message> searchBoard() {
		List<BoardAllSearchResponse> result = boardService.searchBoard();
		return ResponseEntity.status(HttpStatus.OK).body(new Message("200","게시글 조회에 성공하였습니다",result));
	}

	@GetMapping("/{boardId}")
	public ResponseEntity<Message> getBoardById(@PathVariable Long boardId) {
		BoardSearchByIdResponse result = boardService.getBoardById(boardId);
		return ResponseEntity.status(HttpStatus.OK).body(new Message("200","게시글 조회에 성공하였습니다",result));
	}

	@PatchMapping("/{boardId}")
	public ResponseEntity<Message> updateBoard(@PathVariable Long boardId, @Valid @RequestBody BoardUpdateRequestDto dto) {
		BoardUpdateResponseDto result = boardService.updateBoard(boardId,dto);
		return ResponseEntity.status(HttpStatus.OK).body(new Message("200","게시글 수정에 성공하였습니다",result));
	}

	@DeleteMapping("/{boardId}")
	public ResponseEntity<Message> deleteBoard(@PathVariable Long boardId, @RequestHeader String boardPassword) {
		boardService.deleteBoard(boardId,boardPassword);
		return ResponseEntity.status(HttpStatus.OK).body(new Message("200","게시글 삭제에 성공하였습니다"));
	}



}
