package mission05;

/**
 * 자기소개 출력 형식을 결정하는 정책 인터페이스.
 *
 *  ▶ Mission03/04 에서는 정책을 Lion 인스턴스마다 생성자로 들고 있었지만,
 *    Mission05 에서는 "Service 의 협력자" 로 위치를 옮긴다.
 *      - 이유 1: Lion 은 도메인 데이터(이름·전공·기수·파트)에만 집중시키고,
 *               "어떻게 출력할지" 는 애플리케이션 레이어(Service) 의 책임으로 분리한다.
 *      - 이유 2: AppConfig 한 곳에서 IntroducePolicy 구현체를 갈아끼우면
 *               Service 전체의 출력 포맷이 한 번에 바뀐다 (전형적인 DI 시나리오).
 */
public interface IntroducePolicy {

    void introduce(String name, String major, int generation, String role);
}
