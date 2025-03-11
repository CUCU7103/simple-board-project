package com.board.simpleboardproject.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.board.simpleboardproject.global.error.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
		log.error(e.getMessage());
		return ErrorResponse.errorResponse(e);
	}

	/**
	 * @Valid 통해 검증시 위반사항이 발생하여 이를 받는 예외처리
	 *
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> processValidationError(MethodArgumentNotValidException e) {
		log.error(e.getMessage());
		return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body("유효성 검사에서 문제가 발생하엿습니다.");
	}

	/**
	 *  예상하지 못한 예외를 처리하기 위해 지정함.
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<String> exception(Exception e) {
		log.error(e.getMessage());
		return ResponseEntity
			.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body("서버 측 오류가 발생하였습니다.");
	}


}
