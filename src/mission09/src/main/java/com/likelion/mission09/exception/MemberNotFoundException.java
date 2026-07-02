package com.likelion.mission09.exception;

/**
 * 주어진 id 의 멤버가 존재하지 않을 때 던지는 예외 → {@code GlobalExceptionHandler} 에서 404 로 변환.
 */
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("해당 멤버를 찾을 수 없습니다: id=" + id);
    }
}
