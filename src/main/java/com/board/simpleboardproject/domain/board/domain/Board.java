package com.board.simpleboardproject.domain.board.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.board.simpleboardproject.domain.board.dto.update.BoardUpdateRequestDto;
import com.board.simpleboardproject.domain.comment.domain.Comment;
import com.board.simpleboardproject.global.model.YnCode;
import com.board.simpleboardproject.global.security.application.UserInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long boardId;

	@Column(name="board_password")
	private String boardPassword;

	@Column(name = "user_name")
	private String username;

	@Column(name = "title")
	private String title;

	@Column(name = "post")
	private String post;

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

	@Builder(toBuilder = true)
	public Board(Long boardId,String boardPassword, String username, String title,
		String post, String createdBy, String modifiedBy, YnCode deletedYn,List<Comment> comments) {
		this.boardId = boardId;
		this.boardPassword = boardPassword;
		this.username = username;
		this.title = title;
		this.post = post;
		this.createdBy = (createdBy == null) ? username : createdBy;
		this.modifiedBy = modifiedBy;
		this.deletedYn = (deletedYn == null) ? YnCode.N : deletedYn;
		this.comments = comments == null ? new ArrayList<>() : comments;
	}

	@OneToMany
	@JoinColumn(name="board_id", insertable = false, updatable = false)
	private List<Comment> comments;

	public void updateBoard(BoardUpdateRequestDto dto) {
		this.title = dto.title();
		this.post = dto.post();
		this.username = dto.username();
		this.modifiedBy = dto.username();
	}

	public void deleteBoard() {
		this.deletedYn = YnCode.Y;
		// 댓글 리스트가 null인 경우에도 안전하게 처리
		if (this.comments != null) {
			// 활성 상태인 댓글만 삭제 상태로 변경 (단일 스트림 작업으로)
			this.comments.stream()
				.filter(comment -> comment.getDeletedYn() != null && comment.getDeletedYn().equals(YnCode.N))
				.forEach(comment -> comment.deleteComment(YnCode.Y));
		}
		this.modifiedBy = UserInfo.username();
	}

}
