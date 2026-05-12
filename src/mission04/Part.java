package mission04;

/**
 * 아기사자가 속한 파트를 식별하는 열거형.
 *
 *  ▶ Mission03 까지는 "역할"이 String 한 줄로만 표현되어서
 *    문자열 비교(=="백엔드 개발자")로 그룹핑하면 오타 한 번에 통째로 깨질 수 있었다.
 *  ▶ 컬렉션을 본격적으로 다루는 Mission04 에서는,
 *    "그룹의 키" 처럼 코드 곳곳에서 비교 대상이 되는 값을 enum 으로 못박는다.
 *    → Map<Part, List<Lion>> 의 키, 필터 기준, switch 분기 등에서 안전하게 쓰인다.
 *
 *  ▶ label() 은 화면 출력용 한국어 라벨이고,
 *    enum 상수 자체(BACKEND/FRONTEND/DESIGN)는 코드 내부 식별자 역할만 한다.
 *    → 출력 문구가 바뀌어도 식별자는 안 바뀌므로, 비교 로직이 영향을 받지 않는다.
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

    /** 그룹 이름 출력용 라벨 — 예: "백엔드 파트" 처럼 짧게 쓰일 때. */
    public String label() {
        return label;
    }

    /** 역할명 — 자기소개에 들어가는 풀네임 ("백엔드 개발자", "디자이너" 등). */
    public String roleName() {
        return roleName;
    }
}
