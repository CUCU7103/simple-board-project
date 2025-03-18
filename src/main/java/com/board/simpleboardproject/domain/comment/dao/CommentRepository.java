package com.board.simpleboardproject.domain.comment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.board.simpleboardproject.domain.comment.domain.Comment;
import com.board.simpleboardproject.global.model.YnCode;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	Optional<Comment> findByCommentIdAndDeletedYn(Long commentId, YnCode deletedYn);
}
