package com.likelion.mission06.exception;

/**
 * 같은 정체성(이름 + 기수)을 가진 사자가 이미 등록되어 있을 때 던지는 도메인 예외.
 *
 *  ▶ Mission05 의 동일 예외를 그대로 이전했다. RuntimeException 상속이라 컨트롤러까지 자연 전파된다.
 */
public class DuplicateLionException extends RuntimeException {

    public DuplicateLionException(String message) {
        super(message);
    }
}
