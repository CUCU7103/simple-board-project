package com.board.simpleboardproject.global.security.application;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.board.simpleboardproject.domain.user.model.Role;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInfo {

	public static String username(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	public static String userRole(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() ||
			authentication.getAuthorities().isEmpty()) {
			return "권한 없음";
		}
		String authority = authentication.getAuthorities().toArray()[0].toString();
		return authority.substring(5);
	}

}
