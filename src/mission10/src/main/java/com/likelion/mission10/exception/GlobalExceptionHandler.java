package com.likelion.mission10.exception;

import com.likelion.mission10.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 전역 예외 처리기 — 이 미션의 핵심 산출물.
 *
 *  ▶ {@code @RestControllerAdvice} 로 모든 {@code @RestController} 의 예외를 한 곳에서 가로채,
 *    항상 통일된 {@link ErrorResponse} 형식으로 변환해 내려준다. 컨트롤러/서비스는 try-catch 없이
 *    비즈니스 로직에만 집중하고, 예외를 던지기만 하면 된다.
 *
 *  ▶ 예외 → HTTP 상태 코드 매핑:
 *      - {@link MemberNotFoundException}        → 404 Not Found (존재하지 않는 자원)
 *      - {@link MethodArgumentNotValidException} → 400 Bad Request (@Valid DTO 검증 실패, 필드별 메시지 포함)
 *      - {@link HttpMessageNotReadableException} → 400 Bad Request (깨진 JSON / 알 수 없는 enum 값 등)
 *      - {@link IllegalArgumentException}        → 400 Bad Request (도메인 불변식 위반)
 *      - 그 밖의 예상치 못한 예외                  → 500 Internal Server Error
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

    /** 그 밖에 예상하지 못한 모든 예외 → 500 (내부 메시지를 그대로 노출하지 않는다). */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception e) {
        ErrorResponse body = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류가 발생했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private static String format(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }
}
