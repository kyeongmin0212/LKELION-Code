package com.likelion.mission08.exception;

import com.likelion.mission08.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 전역 예외 → HTTP 상태 코드 매핑을 한 곳에 모은 핸들러.
 *
 *  ▶ 매핑:
 *      - {@link MemberNotFoundException}          → 404 Not Found
 *      - {@link MethodArgumentNotValidException}   → 400 Bad Request (@Valid 검증 실패)
 *      - {@link HttpMessageNotReadableException}   → 400 Bad Request (잘못된 JSON / 알 수 없는 enum 값 등)
 *      - {@link IllegalArgumentException}          → 400 Bad Request (도메인 불변식 위반)
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 존재하지 않는 자원 → 404. */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(MemberNotFoundException e) {
        ErrorResponse body = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /** @Valid 요청 DTO 검증 실패 → 400 (필드별 메시지 목록 포함). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        List<String> fieldErrors = e.getBindingResult().getFieldErrors().stream()
                .map(GlobalExceptionHandler::format)
                .toList();
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "요청 값이 올바르지 않습니다.", fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }

    /** 파싱 불가한 본문(잘못된 JSON, 정의되지 않은 Part enum 값 등) → 400. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(HttpMessageNotReadableException e) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "요청 본문을 해석할 수 없습니다. (JSON 형식 / 파트 값 확인)");
        return ResponseEntity.badRequest().body(body);
    }

    /** 도메인 불변식 위반 등 잘못된 인자 → 400. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
        ErrorResponse body = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    private static String format(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }
}
