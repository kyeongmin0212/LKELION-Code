package com.likelion.mission09.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 멤버 도메인 — 이번 미션에서 두 개의 연관관계에 동시에 참여한다.
 *
 *  ▶ Team(1) : Member(N) — 멤버는 <b>연관관계의 주인(N 쪽)</b>.
 *      - {@code @ManyToOne} + {@code @JoinColumn(name = "team_id")} 로 <b>FK 컬럼을 이 테이블이 소유</b>한다.
 *      - {@code fetch = LAZY} 로 팀을 실제로 쓸 때만 조회한다(N+1/불필요 조인 예방).
 *
 *  ▶ Member(1) : Assignment(N) — 멤버가 여러 과제를 가진다.
 *      - {@code @OneToMany(mappedBy = "member")} — 여기서는 비주인(읽기 뷰). FK 는 {@link Assignment} 가 소유.
 *
 *  ▶ 연관관계의 주인/비주인 규칙:
 *      - 주인(FK 소유자)만 INSERT/UPDATE 시 FK 값을 반영한다.
 *      - 비주인(mappedBy)은 조회 전용이며, 양방향 동기화는 편의 메서드로 맞춘다.
 */
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int generation; // 기수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Part part;

    /**
     * 소속 팀 — Team(1) : Member(N) 의 <b>주인</b>. FK 컬럼 {@code team_id} 를 이 테이블이 소유한다.
     * 팀이 없는 멤버도 허용하므로 nullable(=optional) 로 둔다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    /**
     * 이 멤버가 제출/담당한 과제들 — Member(1) : Assignment(N) 의 <b>비주인</b>(mappedBy = "member").
     * FK({@code member_id})는 {@link Assignment} 가 소유한다.
     */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<>();

    /** JPA 전용 기본 생성자 — 직접 사용 금지. */
    protected Member() {
    }

    /** 신규 생성용 생성자 — 팀 미지정. 팀 배정은 {@link Team#addMember} 로 수행한다. */
    public Member(String name, String email, int generation, Part part) {
        validate(name, email, generation, part);
        this.name       = name;
        this.email      = email;
        this.generation = generation;
        this.part       = part;
    }

    /** 전체 필드 교체(PUT 수정). id/team/assignments 는 유지된다. */
    public void update(String name, String email, int generation, Part part) {
        validate(name, email, generation, part);
        this.name       = name;
        this.email      = email;
        this.generation = generation;
        this.part       = part;
    }

    /**
     * 연관관계 주인 쪽의 team 세팅 — {@link Team#addMember} 에서만 호출하도록 열어 둔다.
     * (양쪽 참조를 한 곳에서 동기화하기 위한 패키지 협력 메서드.)
     */
    void assignTeam(Team team) {
        this.team = team;
    }

    /** 과제 편의 메서드 — 양방향 참조를 함께 세팅한다({@link Assignment#getMember} 와 정합). */
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        assignment.assignTo(this);
    }

    public Long             getId()          { return id; }
    public String           getName()        { return name; }
    public String           getEmail()       { return email; }
    public int              getGeneration()  { return generation; }
    public Part             getPart()        { return part; }
    public Team             getTeam()        { return team; }
    public List<Assignment> getAssignments() { return assignments; }

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
        return String.format("Member{id=%d, name=%s, email=%s, generation=%d기, part=%s, teamId=%s}",
                id, name, email, generation, part, (team == null ? null : team.getId()));
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
