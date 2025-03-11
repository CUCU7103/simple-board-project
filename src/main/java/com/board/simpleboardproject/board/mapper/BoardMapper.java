package com.board.simpleboardproject.board.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.board.simpleboardproject.board.domain.Board;
import com.board.simpleboardproject.board.dto.create.BoardCreateRequestDto;
import com.board.simpleboardproject.board.dto.create.BoardCreateResponseDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BoardMapper {

	Board toEntity(BoardCreateRequestDto dto);

	BoardCreateResponseDto toDto(Board entity);

}
