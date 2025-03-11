package com.board.simpleboardproject.board.dto.create;

import com.board.simpleboardproject.global.model.YnCode;

public record BoardCreateResponseDto(String title, String username, String post, YnCode deletedYn) {

}
