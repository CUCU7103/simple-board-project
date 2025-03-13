package com.board.simpleboardproject.domain.board.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.simpleboardproject.domain.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	List<Board> findAllByOrderByCreatedAtDesc();
	Optional<Board> findByBoardIdAndBoardPassword(Long boardId,String boardPassword);

}
