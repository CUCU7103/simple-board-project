package com.board.simpleboardproject.domain.comment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.board.simpleboardproject.domain.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
