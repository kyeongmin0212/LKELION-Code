package com.likelion.mission09.repository;

import com.likelion.mission09.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 팀 저장소 — {@link JpaRepository} 상속으로 CRUD 를 자동 제공.
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
}
