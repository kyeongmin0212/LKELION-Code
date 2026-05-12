package mission04;

/**
 * 같은 정체성(이름 + 기수)을 가진 사자가 이미 등록되어 있을 때 던지는 예외.
 *
 *  ▶ IllegalArgumentException 과 구분해서 따로 만든 이유:
 *    "입력값이 잘못됐다"(전공 빈칸 등) 와 "값 자체는 정상이지만 이미 있다" 는
 *    호출자가 보통 다르게 처리하고 싶기 때문이다 (전자는 입력 폼 재요청, 후자는 '이미 등록됨' 안내).
 */
public class DuplicateLionException extends RuntimeException {

    public DuplicateLionException(String message) {
        super(message);
    }
}
