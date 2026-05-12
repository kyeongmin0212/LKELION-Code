package mission04;

/**
 * 자기소개 출력 형식을 결정하는 정책 인터페이스 (Mission03 의 정책 분리 패턴을 그대로 가져온 것).
 *
 *  ▶ Mission04 의 본 주제는 "컬렉션" 이지만, 등록된 멤버 목록을 출력할 때
 *    여전히 "어떤 포맷으로 보여줄지" 가 필요하므로 같은 패턴을 유지한다.
 *  ▶ 새 포맷이 필요해지면 구현체만 추가하면 되고, Lion 계층은 손대지 않는다 (OCP).
 */
public interface IntroducePolicy {

    void introduce(String name, String major, int generation, String role);
}
