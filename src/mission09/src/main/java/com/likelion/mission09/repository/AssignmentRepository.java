package com.likelion.mission09.repository;

import com.likelion.mission09.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 과제 저장소 — {@link JpaRepository} 상속 + 멤버별 과제 조회.
 *
 *  ▶ {@code findByMemberId} 는 {@code WHERE member_id = ?} 조회로 파생된다(연관관계 기반 조회).
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    /** 멤버별 과제 조회 — 연관 엔티티(Member)의 id 로 파생 조회. */
    List<Assignment> findByMemberId(Long memberId);
}
