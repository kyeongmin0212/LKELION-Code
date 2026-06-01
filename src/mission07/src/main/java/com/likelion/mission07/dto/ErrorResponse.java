package com.likelion.mission07.dto;

import java.util.List;

/**
 * 에러 응답 DTO — 실패 응답의 바디를 일관된 형태로 고정한다.
 *
 *  ▶ 정상 응답({@link MemberResponse})과 마찬가지로, 실패 표현도 Entity/예외 객체를 그대로 노출하지 않고
 *    필요한 정보(status / message / errors)만 골라 담는다.
 *
 *  ▶ {@code errors} 는 검증 실패(400)에서 필드별 메시지를 담는 용도이며, 그 외에는 비어 있다.
 */
public class ErrorResponse {

    private final int          status;
    private final String       message;
    private final List<String> errors;

    public ErrorResponse(int status, String message, List<String> errors) {
        this.status  = status;
        this.message = message;
        this.errors  = errors == null ? List.of() : errors;
    }

    public ErrorResponse(int status, String message) {
        this(status, message, List.of());
    }

    public int          getStatus()  { return status; }
    public String       getMessage() { return message; }
    public List<String> getErrors()  { return errors; }
}
