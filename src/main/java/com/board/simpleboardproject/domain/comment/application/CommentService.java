package com.board.simpleboardproject.domain.comment.application;

import static com.board.simpleboardproject.global.error.ErrorCode.*;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.simpleboardproject.domain.board.dao.BoardRepository;
import com.board.simpleboardproject.domain.comment.dao.CommentRepository;
import com.board.simpleboardproject.domain.comment.domain.Comment;
import com.board.simpleboardproject.domain.comment.dto.create.CommentCreateRequestDto;
import com.board.simpleboardproject.domain.comment.dto.create.CommentCreateResponseDto;
import com.board.simpleboardproject.domain.comment.mapper.CommentMapper;
import com.board.simpleboardproject.global.exception.CustomException;
import com.board.simpleboardproject.global.security.application.UserInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	private final CommentMapper commentMapper;

	@Transactional
	public CommentCreateResponseDto createComment(CommentCreateRequestDto dto) {
		UserInfo.username();
		boardRepository.findById(dto.boardId()).orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
		Comment comment = commentMapper.toCommentCreateResponseDto(dto);
		comment = comment.toBuilder()
			.username(UserInfo.username())
			.build();
		commentRepository.save(comment);
		return commentMapper.toCommentCreateResponseDto(comment);
	}
}
