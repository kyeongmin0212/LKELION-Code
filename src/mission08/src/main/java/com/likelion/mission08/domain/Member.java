package com.likelion.mission08.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * 멤버 도메인 — 이번 미션에서 JPA {@code @Entity} 로 승격되어 DB 테이블 {@code members} 와 매핑된다.
 *
 *  ▶ Mission07(인메모리 Map + AtomicLong 채번)과의 차이가 이 미션의 핵심:
 *      - {@code @Id @GeneratedValue(IDENTITY)} → 식별자 채번을 DB 의 auto-increment 에 위임한다.
 *        (Mission07 의 {@code assignId()} / {@code AtomicLong} 수동 채번이 사라진다.)
 *      - {@code @Enumerated(EnumType.STRING)} → {@link Part} 를 문자열 컬럼으로 매핑한다.
 *      - 영속성 컨텍스트의 변경 감지(dirty checking) 덕분에, 트랜잭션 안에서 필드를 바꾸기만 하면
 *        별도 save 호출 없이도 UPDATE SQL 이 자동으로 나간다({@link #update}).
 *
 *  ▶ JPA 규약: 프록시/리플렉션 생성을 위해 인자 없는 기본 생성자가 필요하다(외부 호출은 막도록 protected).
 */
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB auto-increment 가 채번 (Mission07 수동 채번 대체)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int generation; // 기수

    @Enumerated(EnumType.STRING) // DB 에 "BACKEND" 같은 문자열로 저장 (ORDINAL 대신 STRING — 체크리스트 항목)
    @Column(nullable = false, length = 20)
    private Part part;

    /** JPA 전용 기본 생성자 — 직접 사용 금지(채번 전 빈 객체 방지). */
    protected Member() {
    }

    /** 신규 생성용 생성자 — 아직 식별자가 없는 상태(id 는 저장 시 DB 가 채번). */
    public Member(String name, String email, int generation, Part part) {
        validate(name, email, generation, part);
        this.name       = name;
        this.email      = email;
        this.generation = generation;
        this.part       = part;
    }

    /**
     * 전체 필드 교체(PUT 수정). id 는 유지된다.
     *
     *  ▶ 영속 상태의 Entity 라면, 이 메서드 호출만으로 트랜잭션 커밋 시점에 UPDATE SQL 이 자동 실행된다
     *    (영속성 컨텍스트의 dirty checking). 별도 repository.save() 가 필요 없다.
     */
    public void update(String name, String email, int generation, Part part) {
        validate(name, email, generation, part);
        this.name       = name;
        this.email      = email;
        this.generation = generation;
        this.part       = part;
    }

    public Long   getId()         { return id; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public int    getGeneration() { return generation; }
    public Part   getPart()       { return part; }

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
        return String.format("Member{id=%d, name=%s, email=%s, generation=%d기, part=%s}",
                id, name, email, generation, part);
    }

    private static void validate(String name, String email, int generation, Part part) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 빈 값일 수 없습니다.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 빈 값일 수 없습니다.");
        }
        if (generation < 1) {
            throw new IllegalArgumentException("기수는 1 이상이어야 합니다.");
        }
        if (part == null) {
            throw new IllegalArgumentException("파트는 빈 값일 수 없습니다.");
        }
    }
}
