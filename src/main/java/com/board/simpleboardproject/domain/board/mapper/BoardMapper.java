package com.board.simpleboardproject.domain.board.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.board.simpleboardproject.domain.board.domain.Board;
import com.board.simpleboardproject.domain.board.dto.create.BoardCreateRequestDto;
import com.board.simpleboardproject.domain.board.dto.create.BoardCreateResponseDto;
import com.board.simpleboardproject.domain.board.dto.search.BoardAllSearchResponse;
import com.board.simpleboardproject.domain.board.dto.search.BoardSearchByIdResponse;
import com.board.simpleboardproject.domain.board.dto.update.BoardUpdateResponseDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper {

	Board toEntity(BoardCreateRequestDto dto);

	BoardCreateResponseDto toCreateResponseDto(Board entity);

	List<BoardAllSearchResponse> toAllSearchResponseDto(List<Board> entity);

	BoardSearchByIdResponse toSearchByIdResponseDto(Board entity);

	BoardUpdateResponseDto toUpdateResponseDto(Board entity);
}
