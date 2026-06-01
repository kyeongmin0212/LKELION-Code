package com.likelion.mission07.exception;

import com.likelion.mission07.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 전역 예외 → HTTP 상태 코드 매핑을 한 곳에 모은 핸들러.
 *
 *  ▶ 이렇게 분리하면 컨트롤러는 "정상 흐름 + 상태 코드" 에만 집중하고,
 *    실패 응답(404/400/500)의 포맷과 코드는 여기서 일관되게 통제된다.
 *
 *  ▶ 매핑:
 *      - {@link MemberNotFoundException}        → 404 Not Found
 *      - {@link MethodArgumentNotValidException} → 400 Bad Request (@Valid 검증 실패, 필드별 메시지 동봉)
 *      - {@link IllegalArgumentException}        → 400 Bad Request (도메인 불변식 위반)
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
