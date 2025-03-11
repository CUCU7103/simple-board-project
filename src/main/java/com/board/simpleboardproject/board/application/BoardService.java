package com.board.simpleboardproject.board.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.simpleboardproject.board.dao.BoardRepository;
import com.board.simpleboardproject.board.domain.Board;
import com.board.simpleboardproject.board.dto.create.BoardCreateRequestDto;
import com.board.simpleboardproject.board.dto.create.BoardCreateResponseDto;
import com.board.simpleboardproject.board.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardMapper boardMapper;

	@Transactional
	public BoardCreateResponseDto createBoard(BoardCreateRequestDto dto) {
		Board board = boardRepository.save(boardMapper.toEntity(dto));
		return boardMapper.toDto(board);
	}
}
