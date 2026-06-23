package com.likelion.mission08.service;

import com.likelion.mission08.domain.Member;
import com.likelion.mission08.domain.Part;
import com.likelion.mission08.exception.MemberNotFoundException;
import com.likelion.mission08.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 멤버 애플리케이션 서비스 — CRUD 비즈니스 로직을 담당한다.
 *
 *  ▶ 이번 미션에서 새로 등장하는 개념: {@code @Transactional} 과 영속성 컨텍스트.
 *      - 조회 메서드는 {@code readOnly = true} 로 묶어 불필요한 변경 감지/플러시를 줄인다.
 *      - {@link #update} 는 영속 상태 Entity 의 필드만 바꾸면, 트랜잭션 커밋 시 dirty checking 으로
 *        UPDATE SQL 이 자동 실행된다 — 별도 save 호출이 필요 없다(JPA 영속성 컨텍스트의 핵심).
 *
 *  ▶ 계층 분리(Mission07 유지): 서비스는 DTO 를 모른다. 도메인 값으로 받고 도메인 Entity 를 반환한다.
 */
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /** 생성 — DB 가 채번한 Entity 를 반환한다. */
    @Transactional
    public Member create(Member member) {
        return memberRepository.save(member);
    }

    /** 전체 조회. */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /** 파트별 조회 — 쿼리 메서드(findByPart)로 WHERE 절이 파생된다. */
    public List<Member> findByPart(Part part) {
        return memberRepository.findByPart(part);
    }

    /** 단건 조회 — 없으면 {@link MemberNotFoundException}(→ 404). */
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    /**
     * 수정(전체 교체) — 없으면 404.
     *
     *  ▶ findById 로 가져온 Member 는 영속 상태다. update() 로 필드를 바꾸기만 하면
     *    이 트랜잭션이 커밋될 때 변경 감지가 UPDATE SQL 을 자동 생성한다(save 호출 없음).
     */
    @Transactional
    public Member update(Long id, String name, String email, int generation, Part part) {
        Member member = findById(id);
        member.update(name, email, generation, part);
        return member;
    }

    /** 삭제 — 대상이 없으면 404. */
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
