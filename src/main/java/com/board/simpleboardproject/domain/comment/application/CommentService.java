package com.board.simpleboardproject.domain.comment.application;

import static com.board.simpleboardproject.global.error.ErrorCode.*;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.simpleboardproject.domain.board.dao.BoardRepository;
import com.board.simpleboardproject.domain.comment.dao.CommentRepository;
import com.board.simpleboardproject.domain.comment.domain.Comment;
import com.board.simpleboardproject.domain.comment.dto.create.CommentCreateRequestDto;
import com.board.simpleboardproject.domain.comment.dto.create.CommentCreateResponseDto;
import com.board.simpleboardproject.domain.comment.dto.update.CommentUpdateRequestDto;
import com.board.simpleboardproject.domain.comment.dto.update.CommentUpdateResponseDto;
import com.board.simpleboardproject.domain.comment.mapper.CommentMapper;
import com.board.simpleboardproject.global.exception.CustomException;
import com.board.simpleboardproject.global.model.YnCode;
import com.board.simpleboardproject.global.security.application.UserInfo;

import jakarta.validation.Valid;
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

	@Transactional
	public CommentUpdateResponseDto updateComment(long commentId, @Valid CommentUpdateRequestDto dto) {
		Comment comment = commentRepository.findByCommentIdAndDeletedYn(commentId,YnCode.N)
			.orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
		if(!comment.getUsername().equals(UserInfo.username())){
			throw new CustomException(NO_MATCH_USER);
		}
		comment.updateComment(dto.post(),UserInfo.username());
		Comment result = commentRepository.save(comment);
		return commentMapper.toCommentUpdateResponseDto(result);
	}
}
