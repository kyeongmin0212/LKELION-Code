package mission05;

/**
 * 아기사자가 속한 파트를 식별하는 열거형 (Mission04 의 Part 를 그대로 이어쓴다).
 *
 *  ▶ Mission05 의 본 주제는 IoC/DI 지만, 도메인 데이터 구조 자체는 Mission04 와 동일하다.
 *    "역할 분기" 의 키 값으로 사용되므로 문자열이 아닌 enum 으로 못박는 원칙은 그대로다.
 *  ▶ Service 의 팩토리 메서드(enroll) 가 어떤 Lion 자식 클래스를 만들지 결정할 때
 *    이 enum 을 분기 키로 사용한다 → main 에서 BackendLion / FrontendLion 같은
 *    구체 클래스를 직접 new 할 필요가 사라진다.
 */
public enum Part {

    BACKEND ("백엔드",   "백엔드 개발자"),
    FRONTEND("프론트엔드", "프론트엔드 개발자"),
    DESIGN  ("디자인",   "디자이너");

    private final String label;
    private final String roleName;

    Part(String label, String roleName) {
        this.label    = label;
        this.roleName = roleName;
    }

    /** 그룹 이름 출력용 라벨 — 예: "백엔드 파트". */
    public String label() {
        return label;
    }

    /** 역할명 — 자기소개에 들어가는 풀네임 ("백엔드 개발자", "디자이너" 등). */
    public String roleName() {
        return roleName;
    }
}
