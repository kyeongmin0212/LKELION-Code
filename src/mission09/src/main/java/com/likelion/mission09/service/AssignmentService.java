package com.likelion.mission09.service;

import com.likelion.mission09.domain.Assignment;
import com.likelion.mission09.domain.Member;
import com.likelion.mission09.exception.AssignmentNotFoundException;
import com.likelion.mission09.exception.MemberNotFoundException;
import com.likelion.mission09.repository.AssignmentRepository;
import com.likelion.mission09.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 과제 애플리케이션 서비스 — 과제 생성/조회와 멤버 연결(Member 1 : N Assignment)을 담당한다.
 */
@Service
@Transactional(readOnly = true)
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final MemberRepository     memberRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, MemberRepository memberRepository) {
        this.assignmentRepository = assignmentRepository;
        this.memberRepository     = memberRepository;
    }

    /**
     * 생성 — memberId 로 담당 멤버를 찾아 과제를 연결한 뒤 저장한다.
     * 멤버가 없으면 404 로 전체 롤백된다(하나의 트랜잭션).
     */
    @Transactional
    public Assignment create(Assignment assignment, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
        member.addAssignment(assignment); // 양방향 동기화(주인 쪽 FK member_id 세팅)
        return assignmentRepository.save(assignment);
    }

    public List<Assignment> findAll() {
        return assignmentRepository.findAll();
    }

    public Assignment findById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new AssignmentNotFoundException(id));
    }

    /** 멤버별 과제 조회 — 연관관계(member_id) 기반. 멤버가 없으면 404. */
    public List<Assignment> findByMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException(memberId);
        }
        return assignmentRepository.findByMemberId(memberId);
    }

    @Transactional
    public void delete(Long id) {
        if (!assignmentRepository.existsById(id)) {
            throw new AssignmentNotFoundException(id);
        }
        assignmentRepository.deleteById(id);
    }
}
