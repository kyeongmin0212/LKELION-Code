package com.likelion.mission09.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * 과제 도메인 — Member(1) : Assignment(N) 연관관계의 <b>주인(N 쪽)</b>.
 *
 *  ▶ 이번 미션의 체크리스트 "과제(Assignment) 엔티티와 멤버 간 1:N 관계":
 *      - {@code @ManyToOne} + {@code @JoinColumn(name = "member_id")} 로 <b>FK 를 이 테이블이 소유</b>한다.
 *      - 한 명의 멤버가 여러 과제를 가진다(1:N). 각 과제는 정확히 한 멤버에 속한다.
 *      - {@code fetch = LAZY} — 과제 조회 시 멤버를 항상 조인하지 않는다.
 *
 *  ▶ 반대편({@link Member#getAssignments})은 {@code mappedBy = "member"} 로 이 필드를 가리키는 읽기 뷰다.
 */
@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    /**
     * 과제를 담당/제출한 멤버 — 연관관계의 <b>주인</b>. FK 컬럼 {@code member_id} 를 이 테이블이 소유한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /** JPA 전용 기본 생성자 — 직접 사용 금지. */
    protected Assignment() {
    }

    /** 신규 생성용 생성자 — 멤버 연결은 {@link Member#addAssignment} 또는 {@link #assignTo} 로 수행한다. */
    public Assignment(String title, String description) {
        validate(title);
        this.title       = title;
        this.description = description;
    }

    /**
     * 연관관계 주인 쪽의 member 세팅 — {@link Member#addAssignment} 에서 양방향 동기화용으로 호출한다.
     */
    void assignTo(Member member) {
        this.member = member;
    }

    public void update(String title, String description) {
        validate(title);
        this.title       = title;
        this.description = description;
    }

    public Long   getId()          { return id; }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public Member getMember()      { return member; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Assignment)) return false;
        Assignment other = (Assignment) o;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Assignment{id=%d, title=%s, memberId=%s}",
                id, title, (member == null ? null : member.getId()));
    }

    private static void validate(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("과제 제목은 빈 값일 수 없습니다.");
        }
    }
}
