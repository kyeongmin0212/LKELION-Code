package com.likelion.mission10.exception;

/**
 * 커스텀 예외 — 주어진 id 의 멤버가 존재하지 않을 때 던진다.
 *
 *  ▶ Service 계층에서 이 예외를 던지면 {@link GlobalExceptionHandler} 가 HTTP 404 Not Found 로 변환한다.
 *    (컨트롤러는 정상 흐름에만 집중하고, 예외 → 상태 코드 매핑은 전역 핸들러가 전담한다.)
 */
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("해당 멤버를 찾을 수 없습니다: id=" + id);
    }
}
