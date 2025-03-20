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
	
	@Query("SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.comments WHERE b.boardId = :boardId AND b.deletedYn = :deletedYn")
	Optional<Board> findByBoardIdAndDeletedYn(@Param("boardId")Long boardId, @Param("deletedYn")YnCode deletedYn);

	@Query("SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.comments WHERE b.boardId = :boardId "
		+ "AND b.boardPassword = :boardPassword AND b.deletedYn = :deletedYn")
	Optional<Board> findByBoardIdAndBoardPassword(@Param("boardId") Long boardId,  String boardPassword);


}
