package com.likelion.mission09.repository;

import com.likelion.mission09.domain.Member;
import com.likelion.mission09.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 멤버 저장소 — {@link JpaRepository} 상속 + 연관관계 기반 쿼리 메서드.
 *
 *  ▶ {@code findByTeamId} 는 메서드 이름만으로 {@code WHERE team_id = ?} 조회로 파생된다.
 *    "팀별 멤버 조회 API" 의 백엔드 쿼리로 사용된다(연관관계 기반 조회 — 이 미션의 체크리스트).
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /** 파트별 조회 — WHERE part = ?. */
    List<Member> findByPart(Part part);

    /** 팀별 멤버 조회 — 연관 엔티티(Team)의 id 로 파생 조회(WHERE team_id = ?). */
    List<Member> findByTeamId(Long teamId);
}
