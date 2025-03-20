package com.board.simpleboardproject.domain.user.domain;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.board.simpleboardproject.global.model.YnCode;
import com.board.simpleboardproject.domain.user.model.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "delete_yn")
	private YnCode deletedYn;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_at")
	@CreatedDate
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "modified_at")
	@LastModifiedDate
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime modifiedAt;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public User(String username, String password, YnCode deletedYn, String createdBy,
		String modifiedBy, LocalDateTime modifiedAt, Role role) {
		this.username = username;
		this.password = password;
		this.modifiedAt = modifiedAt;
		this.createdBy = (createdBy == null) ? username : createdBy;
		this.modifiedBy = modifiedBy;
		this.deletedYn = (deletedYn == null) ? YnCode.N : deletedYn;
		this.role = role;
	}

	// 사용자가 애플리케이션에서 가지고 있는 권한(permission)의 컬랙션을 반환합니다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return YnCode.N.equals(this.deletedYn);
	}
}
