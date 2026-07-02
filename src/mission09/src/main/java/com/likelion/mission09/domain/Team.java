package com.likelion.mission09.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 팀 도메인 — Team(1) : Member(N) 연관관계의 <b>1 쪽(비주인)</b>.
 *
 *  ▶ 이번 미션의 핵심(연관관계 매핑):
 *      - {@code @OneToMany(mappedBy = "team")} 로 {@link Member#team} 필드에 매핑을 위임한다.
 *        즉 <b>연관관계의 주인은 FK 를 들고 있는 N 쪽({@link Member})</b> 이고, Team 은 읽기 전용 뷰다.
 *      - {@code mappedBy} 가 있으므로 이 컬렉션에는 별도 FK 컬럼/조인 테이블이 생기지 않는다.
 *
 *  ▶ 양방향 연관관계의 편의 메서드({@link #addMember})로 양쪽 참조를 동시에 맞춰,
 *    객체 그래프와 DB FK 가 어긋나지 않도록 한다.
 */
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    /**
     * 팀에 속한 멤버들 — 연관관계의 <b>주인이 아님</b>(mappedBy = "team").
     * FK({@code team_id})는 {@link Member} 테이블이 들고 있고, 이 컬렉션은 그 반대편을 조회하는 뷰다.
     */
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    /** JPA 전용 기본 생성자 — 직접 사용 금지. */
    protected Team() {
    }

    public Team(String name) {
        validate(name);
        this.name = name;
    }

    /**
     * 양방향 연관관계 편의 메서드 — 팀에 멤버를 추가하면서 멤버의 team 참조도 함께 세팅한다.
     * 두 참조를 한 곳에서 동기화해 객체 그래프와 DB FK 의 정합성을 보장한다.
     */
    public void addMember(Member member) {
        members.add(member);
        member.assignTeam(this);
    }

    public void rename(String name) {
        validate(name);
        this.name = name;
    }

    public Long         getId()      { return id; }
    public String       getName()    { return name; }
    public List<Member> getMembers() { return members; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team other = (Team) o;
        return id != null && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Team{id=%d, name=%s, memberCount=%d}", id, name, members.size());
    }

    private static void validate(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("팀 이름은 빈 값일 수 없습니다.");
        }
    }
}
