package mission05;

import java.util.Objects;

/**
 * 아기사자 추상 클래스 — Mission05 에서는 "순수 도메인 객체" 로 더 단순해졌다.
 *
 *  ▶ Mission04 와의 차이:
 *      - IntroducePolicy 의존을 제거했다.
 *        Mission04 에서는 Lion 이 자기소개 정책까지 들고 있었지만,
 *        Mission05 에서는 그 책임이 {@link LionService} 로 이동했다 (DI 의 자연스러운 이동).
 *      - 그 결과 Lion 의 생성자 파라미터는 (이름, 전공, 기수) 3개로 줄었고,
 *        도메인 객체는 "값" 에 가까워졌으며 외부 협력자(=정책) 와 결합하지 않는다.
 *
 *  ▶ 정체성 정의(equals/hashCode = 이름 + 기수) 는 Mission04 와 동일하다.
 *    Repository 의 중복 차단 로직(contains)이 이 정의에 그대로 의존한다.
 */
public abstract class Lion {

    protected final String name;
    protected final String major;
    protected final int    generation;

    protected Lion(String name, String major, int generation) {
        validate(name, major, generation);
        this.name       = name;
        this.major      = major;
        this.generation = generation;
    }

    // ─────────────────────────────────────────────────────────────
    // 추상 메서드 — 자식 클래스가 자기 역할에 맞게 구현한다 (다형성)
    // ─────────────────────────────────────────────────────────────

    /** 이 사자가 속한 파트 (그룹핑·필터링·역할명 산출의 키). */
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

    public String name()       { return name; }
    public String major()      { return major; }
    public int    generation() { return generation; }

    // ─────────────────────────────────────────────────────────────
    // 동등성 — Repository 의 중복 판정이 이 정의를 그대로 사용한다
    // ─────────────────────────────────────────────────────────────

    /** 정체성 키 = (이름, 기수). 동명이인이라도 기수가 다르면 다른 사자. */
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

    private static void validate(String name, String major, int generation) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        if (major == null || major.trim().isEmpty()) {
            throw new IllegalArgumentException("전공은 빈 값일 수 없습니다.");
        }
        if (generation < 1) {
            throw new IllegalArgumentException("기수는 1 이상이어야 합니다.");
        }
    }
}
