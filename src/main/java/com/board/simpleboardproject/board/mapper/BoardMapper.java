package com.board.simpleboardproject.board.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.board.simpleboardproject.board.domain.Board;
import com.board.simpleboardproject.board.dto.create.BoardCreateRequestDto;
import com.board.simpleboardproject.board.dto.create.BoardCreateResponseDto;
import com.board.simpleboardproject.board.dto.search.BoardAllSearchResponse;
import com.board.simpleboardproject.board.dto.search.BoardSearchByIdResponse;
import com.board.simpleboardproject.board.dto.update.BoardUpdateRequestDto;
import com.board.simpleboardproject.board.dto.update.BoardUpdateResponseDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper {

	Board toEntity(BoardCreateRequestDto dto);

	BoardCreateResponseDto toCreateResponseDto(Board entity);

	List<BoardAllSearchResponse> toAllSearchResponseDto(List<Board> entity);

	BoardSearchByIdResponse toSearchByIdResponseDto(Board entity);

	@Mapping(source = "username", target = "modifiedBy")
	Board toEntity(BoardUpdateRequestDto dto);

	BoardUpdateResponseDto toUpdateResponseDto(Board entity);
}
