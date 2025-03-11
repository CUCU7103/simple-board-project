package com.board.simpleboardproject.board.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.simpleboardproject.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
