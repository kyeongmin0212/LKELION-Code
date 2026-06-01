package com.likelion.mission07.exception;

/**
 * 주어진 id 의 멤버가 존재하지 않을 때 던지는 예외.
 *
 *  ▶ 이 예외는 {@code GlobalExceptionHandler} 에서 HTTP 404 Not Found 로 변환된다.
 *    서비스/컨트롤러는 "없으면 던진다" 만 신경 쓰고, 상태 코드 매핑은 한 곳(핸들러)에 모은다.
 */
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("해당 멤버를 찾을 수 없습니다: id=" + id);
    }
}
