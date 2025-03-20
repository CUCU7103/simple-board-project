package com.board.simpleboardproject.domain.comment.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.board.simpleboardproject.global.model.YnCode;
import com.board.simpleboardproject.global.security.application.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long commentId;

	@Column(name = "post")
	private String post;

	@Column(name = "username")
	private String username;

	@Column(name = "created_at", updatable = false)
	@CreatedDate
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;

	@Column(name = "modified_at")
	@LastModifiedDate
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modifiedAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "deleted_yn")
	@Enumerated(EnumType.STRING)
	private YnCode deletedYn;

	@Column(name ="board_id")
	private Long boardId;

	@Builder(toBuilder = true)
	public Comment(Long commentId, String post, String username,
		String createdBy, String modifiedBy, YnCode deletedYn, Long boardId) {
		this.commentId = commentId;
		this.post = post;
		this.username = username;
		this.createdBy = (createdBy == null) ? username : createdBy;
		this.modifiedBy = modifiedBy;
		this.deletedYn = (deletedYn == null) ? YnCode.N : deletedYn;
		this.boardId = boardId;
	}

	public void updateComment(String post, String modifiedBy) {
		this.post = post;
		this.modifiedBy = modifiedBy;
	}

	public void deleteComment(YnCode deletedYn) {
		this.deletedYn = deletedYn;
		this.modifiedBy = UserInfo.username();
	}

}
