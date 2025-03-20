package com.board.simpleboardproject.domain.board.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.board.simpleboardproject.domain.board.domain.Board;
import com.board.simpleboardproject.global.model.YnCode;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	@Query("SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.comments WHERE b.deletedYn = :deletedYn ORDER BY b.createdAt DESC")
	List<Board> findAllWithCommentsByDeletedYnOrderByCreatedAtDesc(@Param("deletedYn") YnCode deletedYn);

	Optional<Board> findByBoardIdAndBoardPassword(Long boardId,String boardPassword);

}
