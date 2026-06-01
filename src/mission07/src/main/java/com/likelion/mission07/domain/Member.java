package com.likelion.mission07.domain;

import java.util.Objects;

/**
 * 멤버 도메인(Entity). REST 자원 {@code /members} 의 실체이자, 저장소가 보관하는 단위이다.
 *
 *  ▶ DTO 와의 분리 원칙 (이 미션의 핵심):
 *      - Entity 는 "저장되는 식별 가능한 도메인" 이다 → 서버가 부여하는 {@code id} 를 가진다.
 *      - 요청 DTO 는 클라이언트가 보내는 입력이다 → {@code id} 를 받지 않는다(생성은 서버가 채번).
 *      - 응답 DTO 는 클라이언트에게 보여줄 표현이다 → Entity 를 그대로 노출하지 않고 골라 담는다.
 *    이렇게 분리하면 도메인 내부 구조가 바뀌어도 API 표현(요청/응답 계약)을 독립적으로 지킬 수 있다.
 *
 *  ▶ 식별자({@code id}) 는 저장 시점에 Repository 가 채번해 한 번만 세팅한다(이후 불변).
 *    나머지 필드(name/email/generation)는 PUT 수정으로 갱신될 수 있으므로 setter 를 제한적으로 연다.
 *
 *  ▶ 정체성(equals/hashCode)은 {@code id} 기준이다 — 저장된 Entity 를 식별하는 유일 키이기 때문.
 */
public class Member {

    private Long   id;          // 저장 시 Repository 가 채번. 영속 전에는 null.
    private String name;
    private String email;
    private int    generation;  // 기수

    /** 신규 생성용 생성자 — 아직 식별자가 없는 상태(id = null). */
    public Member(String name, String email, int generation) {
        validate(name, email, generation);
        this.name       = name;
        this.email      = email;
        this.generation = generation;
    }

    /**
     * 저장소가 채번한 식별자를 부여한다. 최초 저장 시 한 번만 호출하는 것을 전제로 한다.
     * @throws IllegalStateException 이미 id 가 부여된 Entity 에 다시 부여하려 할 때
     */
    public void assignId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("이미 식별자가 부여된 멤버입니다: id=" + this.id);
        }
        this.id = Objects.requireNonNull(id, "id 는 null 일 수 없습니다.");
    }

    /** PUT 수정 — 전체 필드를 새 값으로 교체한다. id 는 유지된다. */
    public void update(String name, String email, int generation) {
        validate(name, email, generation);
        this.name       = name;
        this.email      = email;
        this.generation = generation;
    }

    public Long   getId()         { return id; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public int    getGeneration() { return generation; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member other = (Member) o;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Member{id=%d, name=%s, email=%s, generation=%d기}",
                id, name, email, generation);
    }

    private static void validate(String name, String email, int generation) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 빈 값일 수 없습니다.");
        }
        if (generation < 1) {
            throw new IllegalArgumentException("기수는 1 이상이어야 합니다.");
        }
    }
}
