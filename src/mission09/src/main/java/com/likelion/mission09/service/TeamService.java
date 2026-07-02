package com.likelion.mission09.service;

import com.likelion.mission09.domain.Member;
import com.likelion.mission09.domain.Team;
import com.likelion.mission09.exception.TeamNotFoundException;
import com.likelion.mission09.repository.MemberRepository;
import com.likelion.mission09.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 팀 애플리케이션 서비스 — 팀 CRUD 와 "팀별 멤버 조회" 를 담당한다.
 *
 *  ▶ 클래스에 {@code @Transactional(readOnly = true)} 를 걸어 조회를 기본 읽기 전용 트랜잭션으로 묶고,
 *    쓰기 메서드에만 {@code @Transactional} 을 덮어써 쓰기 트랜잭션으로 승격한다(체크리스트 항목).
 */
@Service
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository   teamRepository;
    private final MemberRepository memberRepository;

    public TeamService(TeamRepository teamRepository, MemberRepository memberRepository) {
        this.teamRepository   = teamRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Team create(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    /**
     * 팀별 멤버 조회 — 연관관계(team_id) 기반 조회.
     * 팀이 존재하지 않으면 404, 존재하면 소속 멤버 목록을 반환한다.
     */
    public List<Member> findMembers(Long teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new TeamNotFoundException(teamId);
        }
        return memberRepository.findByTeamId(teamId);
    }

    @Transactional
    public Team rename(Long id, String name) {
        Team team = findById(id);
        team.rename(name); // 영속 상태 → dirty checking 으로 커밋 시 UPDATE SQL 자동
        return team;
    }

    /** 삭제 — cascade/orphanRemoval 로 소속 멤버까지 함께 정리된다. */
    @Transactional
    public void delete(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new TeamNotFoundException(id);
        }
        teamRepository.deleteById(id);
    }
}
