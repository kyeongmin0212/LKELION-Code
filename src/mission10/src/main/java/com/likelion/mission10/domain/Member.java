package com.likelion.mission10.domain;

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
 * 멤버 도메인 — JPA {@code @Entity} 로 DB 테이블 {@code members} 와 매핑된다.
 *
 *  ▶ 도메인 불변식은 {@link #validate} 에서 검증하고, 위반 시 {@link IllegalArgumentException} 을 던진다.
 *    이 예외는 {@code GlobalExceptionHandler} 에서 400 Bad Request 로 변환된다(이 미션의 예외 처리 흐름).
 */
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB auto-increment 가 채번
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int generation; // 기수

    @Enumerated(EnumType.STRING) // DB 에 "BACKEND" 같은 문자열로 저장
    @Column(nullable = false, length = 20)
    private Part part;

    /** JPA 전용 기본 생성자 — 직접 사용 금지. */
    protected Member() {
    }

    /** 신규 생성용 생성자 — id 는 저장 시 DB 가 채번. */
    public Member(String name, String email, int generation, Part part) {
        validate(name, email, generation, part);
        this.name       = name;
        this.email      = email;
        this.generation = generation;
        this.part       = part;
    }

    /** 전체 필드 교체(PUT 수정). id 는 유지된다. 영속 상태면 커밋 시 dirty checking 으로 UPDATE SQL 자동. */
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
