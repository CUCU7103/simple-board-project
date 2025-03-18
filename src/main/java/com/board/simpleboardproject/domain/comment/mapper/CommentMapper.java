package com.board.simpleboardproject.domain.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.board.simpleboardproject.domain.comment.domain.Comment;
import com.board.simpleboardproject.domain.comment.dto.create.CommentCreateRequestDto;
import com.board.simpleboardproject.domain.comment.dto.create.CommentCreateResponseDto;
import com.board.simpleboardproject.domain.comment.dto.update.CommentUpdateRequestDto;
import com.board.simpleboardproject.domain.comment.dto.update.CommentUpdateResponseDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

	Comment toEntity(CommentCreateRequestDto dto);

	Comment toCommentCreateResponseDto(CommentCreateRequestDto dto);

	CommentCreateResponseDto toCommentCreateResponseDto(Comment comment);

	CommentUpdateResponseDto toCommentUpdateResponseDto(Comment comment);
}
