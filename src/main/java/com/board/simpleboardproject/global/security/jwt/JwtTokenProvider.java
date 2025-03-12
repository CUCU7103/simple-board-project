package com.board.simpleboardproject.global.security.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.board.simpleboardproject.user.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	// 토큰 유효시간 상수 (초 단위)
	public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 3600; // 1시간

	// JWT 비밀키
	@Value("${jwt.secret}")
	private String secretKey;

	private Key key;

	@PostConstruct
	protected void init() {
		// 키를 Base64 인코딩
		String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
		// secretKey를 바이트 배열로 변환 후 Key 객체 생성
		this.key = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
	}

	// JWT 토큰 생성
	public String createToken(User user) {
		// 토큰 만료 시간 설정
		Date now = new Date();
		Date validity = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000);

		return Jwts.builder()
			.setSubject(user.getUsername())
			.claim("role", user.getRole().name())
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);
		String role = claims.get("role", String.class);

		return new UsernamePasswordAuthenticationToken(
			claims.getSubject(),
			null,
			Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
		);
	}

	// 토큰에서 회원 정보 추출
	public String getUsername(String token) {
		return parseClaims(token).getSubject();
	}

	// 토큰의 유효성, 만료일자 확인
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.error("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			log.error("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.error("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.error("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}

	// 토큰 파싱하여 Claims 객체 생성
	private Claims parseClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (ExpiredJwtException e) {
			// 만료된 토큰이어도 정보 가져오기
			return e.getClaims();
		}
	}

	// 토큰 남은 유효시간
	public long getTokenExpirationTime(String token) {
		Date expiration = parseClaims(token).getExpiration();
		return expiration.getTime() - new Date().getTime();
	}

}
