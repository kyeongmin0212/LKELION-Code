package com.likelion.mission06.domain;

import java.util.Objects;

/**
 * 아기사자 추상 도메인 객체. Mission05 의 Lion 을 그대로 이전했다.
 *
 *  ▶ 정체성 키 = (이름, 기수) — Repository 의 중복 차단 로직이 이 정의에 의존한다.
 *  ▶ Spring 빈으로 등록하지 않는다. Lion 인스턴스는 서비스가 도메인 입력으로부터 생성한다.
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

    public abstract Part part();

    public abstract void work();

    public final String role() {
        return part().roleName();
    }

    public String name()       { return name; }
    public String major()      { return major; }
    public int    generation() { return generation; }

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
