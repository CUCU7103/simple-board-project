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
		Board board = boardRepository.findByBoardIdAndDeletedYn(boardId,YnCode.N)
			.orElseThrow(()-> new CustomException(ErrorCode.BOARD_NOT_FOUND));
		return boardMapper.toSearchByIdResponseDto(board);

	}

	@Transactional
	public BoardUpdateResponseDto updateBoard(Long boardId, BoardUpdateRequestDto dto) {
		Board existingBoard = isAdmin()
			? findBoardByIdForAdmin(boardId)
			: findBoardWithValidation(boardId,dto.boardPassword());
		existingBoard.updateBoard(dto);
		return boardMapper.toUpdateResponseDto(existingBoard);
	}

	@Transactional
	public void deleteBoard(Long boardId, String boardPassword) {
		Board existingBoard = isAdmin()
			? findBoardByIdForAdmin(boardId)
			: findBoardWithValidation(boardId,boardPassword);
	    existingBoard.deleteBoard();
		log.info("Board deleted {} " , existingBoard);
		boardRepository.save(existingBoard);
	}

	// 관리자가 아닐 시 게시글 검증 로직
	private Board findBoardWithValidation(Long boardId, @NotBlank String boardPassword) {
		return boardRepository.findByBoardIdAndBoardPassword(boardId, boardPassword)
			.orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
	}

	// ID로 게시글 찾기 (관리자용)
	private Board findBoardByIdForAdmin(Long boardId) {
		return boardRepository.findByBoardIdAndDeletedYn(boardId,YnCode.N)
			.orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
	}

	// 관리자 권한 검증
	private boolean isAdmin() {
		return UserInfo.userRole().equals(Role.ADMIN.name());
	}


}
