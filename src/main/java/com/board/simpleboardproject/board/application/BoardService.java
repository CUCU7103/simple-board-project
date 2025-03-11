package com.board.simpleboardproject.board.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.simpleboardproject.board.dao.BoardRepository;
import com.board.simpleboardproject.board.domain.Board;
import com.board.simpleboardproject.board.dto.create.BoardCreateRequestDto;
import com.board.simpleboardproject.board.dto.create.BoardCreateResponseDto;
import com.board.simpleboardproject.board.dto.search.BoardAllSearchResponse;
import com.board.simpleboardproject.board.dto.search.BoardSearchByIdResponse;
import com.board.simpleboardproject.board.dto.update.BoardUpdateRequestDto;
import com.board.simpleboardproject.board.dto.update.BoardUpdateResponseDto;
import com.board.simpleboardproject.board.mapper.BoardMapper;
import com.board.simpleboardproject.global.error.ErrorCode;
import com.board.simpleboardproject.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final BoardMapper boardMapper;

	@Transactional
	public BoardCreateResponseDto createBoard(BoardCreateRequestDto dto) {
		Board board = boardRepository.save(boardMapper.toEntity(dto));
		return boardMapper.toCreateResponseDto(board);
	}

	@Transactional(readOnly = true)
	public List<BoardAllSearchResponse> searchBoard() {
		List<Board> board = boardRepository.findAllByOrderByCreatedAtDesc();
		return boardMapper.toAllSearchResponseDto(board);
	}

	@Transactional(readOnly = true)
	public BoardSearchByIdResponse getBoardById(Long boardId) {

		Optional<Board> board = boardRepository.findById(boardId);
		if (board.isEmpty()) {
			throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		}
		return boardMapper.toSearchByIdResponseDto(board.get());

	}

	@Transactional
	public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto dto) {
		Optional<Board> board = boardRepository.findByBoardIdAndBoardPassword(boardId,dto.boardPassword());
		if (board.isEmpty()) {
			throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		}
		Board updatedBoard = boardMapper.toEntity(dto);
		return boardMapper.toUpdateResponseDto(boardRepository.save(updatedBoard));
	}
}
