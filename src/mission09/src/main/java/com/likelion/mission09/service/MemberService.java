package com.likelion.mission09.service;

import com.likelion.mission09.domain.Member;
import com.likelion.mission09.domain.Part;
import com.likelion.mission09.domain.Team;
import com.likelion.mission09.exception.MemberNotFoundException;
import com.likelion.mission09.exception.TeamNotFoundException;
import com.likelion.mission09.repository.MemberRepository;
import com.likelion.mission09.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 멤버 애플리케이션 서비스 — CRUD + 팀 배정(연관관계 설정) 로직을 담당한다.
 *
 *  ▶ {@code @Transactional} 규약(이 미션의 체크리스트):
 *      - 클래스 레벨 {@code readOnly = true} → 조회는 읽기 전용 트랜잭션.
 *      - 쓰기 메서드는 {@code @Transactional} 로 승격.
 *      - {@link #create} 에서 팀 배정과 멤버 저장이 <b>하나의 트랜잭션</b>으로 묶여
 *        원자적으로 반영된다(팀 조회 실패 시 전체 롤백).
 */
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository   teamRepository;

    public MemberService(MemberRepository memberRepository, TeamRepository teamRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository   = teamRepository;
    }

    /**
     * 생성 — teamId 가 주어지면 해당 팀에 배정(연관관계 설정)한 뒤 저장한다.
     * team 은 연관관계의 주인(Member.team)이 FK 를 반영하므로, 팀 소속으로 저장된다.
     */
    @Transactional
    public Member create(Member member, Long teamId) {
        if (teamId != null) {
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new TeamNotFoundException(teamId));
            team.addMember(member); // 양방향 동기화(주인 쪽 FK 세팅 포함)
        }
        return memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public List<Member> findByPart(Part part) {
        return memberRepository.findByPart(part);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    /**
     * 수정(전체 교체) — 영속 상태 Entity 의 필드만 바꾸면 커밋 시 dirty checking 으로 UPDATE SQL 자동.
     */
    @Transactional
    public Member update(Long id, String name, String email, int generation, Part part) {
        Member member = findById(id);
        member.update(name, email, generation, part);
        return member;
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new MemberNotFoundException(id);
        }
        memberRepository.deleteById(id);
    }

    public long count() {
        return memberRepository.count();
    }
}
