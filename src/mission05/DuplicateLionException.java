package mission05;

/**
 * 같은 정체성(이름 + 기수)을 가진 사자가 이미 등록되어 있을 때 던지는 도메인 예외.
 *
 *  ▶ Mission04 와 동일한 의도이지만, Mission05 에서는 Repository 인터페이스의 계약으로
 *    "save 는 중복이면 이 예외를 던진다" 가 명시된다 (인터페이스 자바독 참고).
 *    구현체(Memory / Mock) 가 달라져도 호출 측이 잡아야 할 예외 타입은 동일하다.
 */
public class DuplicateLionException extends RuntimeException {

    public DuplicateLionException(String message) {
        super(message);
    }
}
