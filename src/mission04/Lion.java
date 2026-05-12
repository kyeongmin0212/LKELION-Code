package mission04;

import java.util.Objects;

/**
 * 아기사자 추상 클래스 — Mission03 의 상속·다형성 구조를 그대로 유지하면서
 * Mission04 에서는 "컬렉션의 원소" 로서 필요한 책임을 두 가지 추가했다.
 *
 *  ▶ 추가 1) {@link #part()} 추상 메서드
 *           - Mission03 의 role() 은 화면 표시용 문자열이라 그룹핑 키로 쓰기엔 약하다 (오타 위험).
 *           - 컬렉션의 키(Map<Part, List<Lion>>) 로 안전하게 쓸 수 있도록 enum 으로 강제한다.
 *
 *  ▶ 추가 2) {@link #equals(Object)} / {@link #hashCode()} 오버라이딩
 *           - Mission04 의 핵심 요구 중 하나는 "등록 시 중복 확인" 이다.
 *           - List.contains, Set 멤버십, HashMap 키 비교 등 컬렉션의 모든 동등성 판단이
 *             equals/hashCode 에 의존하기 때문에, "같은 사자"의 정의를 클래스 자신이 책임진다.
 *           - 정체성 키는 (name, generation) 으로 잡는다 — 동명이인이라도 기수가 다르면 다른 사자.
 *
 *  ▶ work() / role() 의 다형성 책임은 그대로다. Mission04 가 컬렉션 주제라고 해서
 *    Mission03 에서 정리한 객체지향 구조를 깨뜨리지 않는다.
 */
public abstract class Lion {

    protected final String name;
    protected final String major;
    protected final int    generation;
    private   final IntroducePolicy introducePolicy;

    protected Lion(String name, String major, int generation, IntroducePolicy introducePolicy) {
        validate(name, major, generation, introducePolicy);
        this.name            = name;
        this.major           = major;
        this.generation      = generation;
        this.introducePolicy = introducePolicy;
    }

    // ─────────────────────────────────────────────────────────────
    // 추상 메서드 — 역할별로 반드시 다르게 구현해야 한다 (다형성)
    // ─────────────────────────────────────────────────────────────

    /** 이 사자가 속한 파트 (그룹핑 / 필터링 키로 사용된다). */
    public abstract Part part();

    /** 이 사자가 실제로 하는 일을 출력한다. */
    public abstract void work();

    // ─────────────────────────────────────────────────────────────
    // 공통 동작
    // ─────────────────────────────────────────────────────────────

    /** 역할명 — Part enum 이 보유한 풀네임을 그대로 위임한다. */
    public final String role() {
        return part().roleName();
    }

    /** 자기소개를 출력한다 — 출력 포맷은 외부 정책이 결정한다. */
    public final void introduce() {
        introducePolicy.introduce(name, major, generation, role());
    }

    // ─────────────────────────────────────────────────────────────
    // 게터 — 저장소(LionRepository) 가 검색 / 필터링에서 사용한다
    // ─────────────────────────────────────────────────────────────

    public String name()       { return name; }
    public String major()      { return major; }
    public int    generation() { return generation; }

    // ─────────────────────────────────────────────────────────────
    // 동등성 — "중복 확인" 의 의미를 클래스가 직접 정의한다
    // ─────────────────────────────────────────────────────────────

    /**
     * 정체성 키 = (이름, 기수).
     *
     *  ▶ 같은 이름의 사람이 다른 기수에 들어올 수도 있으므로 기수까지 같이 본다.
     *  ▶ 파트는 의도적으로 키에서 뺀다 — "같은 사람이 백엔드에서 디자인으로 옮기는" 경우는
     *    같은 사자로 취급해야 하기 때문 (즉, 파트는 정체성이 아니라 속성).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lion)) return false;
        Lion other = (Lion) o;
        return generation == other.generation
                && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, generation);
    }

    @Override
    public String toString() {
        return String.format("Lion{name=%s, major=%s, generation=%d기, part=%s}",
                name, major, generation, part());
    }

    // ─────────────────────────────────────────────────────────────

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
