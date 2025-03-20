package com.board.simpleboardproject.domain.board.application;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.simpleboardproject.domain.board.dao.BoardRepository;
import com.board.simpleboardproject.domain.board.domain.Board;
import com.board.simpleboardproject.domain.board.dto.create.BoardCreateRequestDto;
import com.board.simpleboardproject.domain.board.dto.create.BoardCreateResponseDto;
import com.board.simpleboardproject.domain.board.dto.search.BoardAllSearchResponse;
import com.board.simpleboardproject.domain.board.dto.search.BoardSearchByIdResponse;
import com.board.simpleboardproject.domain.board.dto.update.BoardUpdateRequestDto;
import com.board.simpleboardproject.domain.board.dto.update.BoardUpdateResponseDto;
import com.board.simpleboardproject.domain.board.mapper.BoardMapper;
import com.board.simpleboardproject.domain.user.model.Role;
import com.board.simpleboardproject.global.error.ErrorCode;
import com.board.simpleboardproject.global.exception.CustomException;
import com.board.simpleboardproject.global.model.YnCode;
import com.board.simpleboardproject.global.security.application.UserInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
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
		List<Board> board = boardRepository.findAllWithCommentsByDeletedYnOrderByCreatedAtDesc(YnCode.N);
		return boardMapper.toAllSearchResponseDto(board);
	}

	@Transactional(readOnly = true)
	public BoardSearchByIdResponse getBoardById(Long boardId) {
		Board board = boardRepository.findByBoardId(boardId)
			.orElseThrow(()-> new CustomException(ErrorCode.BOARD_NOT_FOUND));
		return boardMapper.toSearchByIdResponseDto(board);

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

	@Transactional
	public void deleteBoard(Long boardId, String boardPassword) {
		Optional<Board> board = boardRepository.findByBoardIdAndBoardPassword(boardId,boardPassword);
		if (board.isEmpty()) {
			throw new CustomException(ErrorCode.BOARD_NOT_FOUND);
		}
		Board deletedBoard = board.get()
			.toBuilder()
			.deletedYn(YnCode.Y)
			.build();
		boardRepository.save(deletedBoard);
	}
}
