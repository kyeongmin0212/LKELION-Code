package com.likelion.mission10.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 통일된 에러 응답 DTO — 모든 실패 응답의 바디를 일관된 형태로 고정한다.
 *
 *  ▶ 이 미션의 핵심 산출물: {@code GlobalExceptionHandler} 가 어떤 예외를 잡든 항상 이 형식으로 내려준다.
 *    프론트엔드는 {@code status / message / errors} 만 알면 실패를 일관되게 표시할 수 있다.
 *
 *  <pre>
 *  {
 *    "status": 404,
 *    "message": "해당 멤버를 찾을 수 없습니다: id=999",
 *    "errors": [],
 *    "timestamp": "2026-07-06T12:34:56.789"
 *  }
 *  </pre>
 */
public class ErrorResponse {

    private final int           status;    // HTTP 상태 코드 (404, 400 ...)
    private final String        message;   // 사람이 읽을 대표 메시지
    private final List<String>  errors;    // 필드별 상세 메시지(검증 실패 시), 없으면 빈 배열
    private final LocalDateTime timestamp; // 발생 시각

    public ErrorResponse(int status, String message, List<String> errors) {
        this.status    = status;
        this.message   = message;
        this.errors    = errors == null ? List.of() : errors;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String message) {
        this(status, message, List.of());
    }

    public int           getStatus()    { return status; }
    public String        getMessage()   { return message; }
    public List<String>  getErrors()    { return errors; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
