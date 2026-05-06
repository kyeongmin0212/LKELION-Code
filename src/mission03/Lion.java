package mission03;

/**
 * 아기사자 추상 클래스 — 모든 역할(백엔드/프론트엔드/디자인...)이 공유하는 공통 뼈대.
 *
 *  ▶ 공통 속성(name, major, generation)과 공통 흐름(검증 → 자기소개)은 부모가 책임지고,
 *  ▶ "역할"과 "역할에 따른 동작(work)"은 자식 클래스가 다형성으로 결정한다.
 *
 *  ▶ work() / role() 은 역할마다 반드시 달라지므로 abstract 로 강제한다.
 *    → main 에서 instanceof 로 분기할 필요 없이, 그냥 lion.work() 만 호출하면
 *      JVM 이 실제 타입에 맞는 메서드를 자동으로 골라 실행한다 (동적 디스패치).
 *
 *  ▶ introduce() 는 템플릿 메서드 패턴을 살짝 빌려와서,
 *    "출력 포맷"이라는 변경되기 쉬운 책임만 IntroducePolicy 로 위임한다.
 */
public abstract class Lion {

    protected final String name;
    protected final String major;
    protected final int    generation;

    /**
     * 출력 정책은 외부에서 주입한다 (의존성 주입).
     * 같은 BackendLion 이라도 어떤 곳에서는 표준 포맷,
     * 다른 곳에서는 캐주얼 포맷으로 바꿀 수 있어야 하기 때문.
     */
    private final IntroducePolicy introducePolicy;

    protected Lion(String name, String major, int generation, IntroducePolicy introducePolicy) {
        validate(name, major, generation, introducePolicy);
        this.name            = name;
        this.major           = major;
        this.generation      = generation;
        this.introducePolicy = introducePolicy;
    }

    // ─────────────────────────────────────────────────────────────
    // 추상 메서드 — 역할별로 반드시 다르게 구현해야 한다 (다형성의 핵심)
    // ─────────────────────────────────────────────────────────────

    /** 이 사자의 역할명을 돌려준다. (예: "백엔드 개발자") */
    public abstract String role();

    /** 이 사자가 실제로 하는 일을 출력한다. */
    public abstract void work();

    // ─────────────────────────────────────────────────────────────
    // 공통 동작 — 자식이 굳이 다시 짤 필요가 없는 부분
    // ─────────────────────────────────────────────────────────────

    /**
     * 자기소개를 출력한다.
     *
     *  ▶ "어떤 형식으로 출력할지"는 IntroducePolicy 에 위임한다.
     *  ▶ "어떤 역할인지"는 자식이 오버라이딩한 role() 이 결정한다.
     *    → 이 한 줄 안에 "다형성 + 정책 분리" 두 개념이 같이 녹아 있다.
     */
    public final void introduce() {
        introducePolicy.introduce(name, major, generation, role());
    }

    /** 공통 검증 — Lion 자신이 책임진다 (Mission02 캡슐화 원칙 그대로 유지). */
    private static void validate(String name, String major, int generation, IntroducePolicy policy) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        if (major == null || major.trim().isEmpty()) {
            throw new IllegalArgumentException("전공은 빈 값일 수 없습니다.");
        }
        if (generation < 1) {
            throw new IllegalArgumentException("기수는 1 이상이어야 합니다.");
        }
        if (policy == null) {
            throw new IllegalArgumentException("자기소개 정책(IntroducePolicy)은 필수입니다.");
        }
    }
}
