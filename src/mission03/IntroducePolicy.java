package mission03;

/**
 * 자기소개 출력 형식을 결정하는 정책 인터페이스.
 *
 *  ▶ "어떤 형식으로 출력할지"는 Lion 본체와는 다른 변경 축이다.
 *    Lion 계층은 "역할에 따라 무엇을 하는가(work)"를 책임지고,
 *    출력 포맷처럼 자주 바뀔 수 있는 부분은 정책 객체로 분리한다.
 *
 *  ▶ 새 포맷이 필요해지면 IntroducePolicy 구현체만 추가하면 되고,
 *    Lion 계층 코드는 한 줄도 손대지 않는다 (OCP — 개방·폐쇄 원칙).
 */
public interface IntroducePolicy {

    /**
     * 자기소개 한 건을 출력한다.
     *
     * @param name       이름
     * @param major      전공
     * @param generation 기수
     * @param role       역할명 (Lion 하위 클래스가 다형성으로 결정)
     */
    void introduce(String name, String major, int generation, String role);
}
