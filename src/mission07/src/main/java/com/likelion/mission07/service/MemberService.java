package com.likelion.mission07.service;

import com.likelion.mission07.domain.Member;
import com.likelion.mission07.exception.MemberNotFoundException;
import com.likelion.mission07.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 멤버 애플리케이션 서비스 — CRUD 비즈니스 로직을 담당한다.
 *
 *  ▶ 계층 분리 의도:
 *      - 컨트롤러는 HTTP(요청 파싱 / 상태 코드)만, 서비스는 도메인 규칙(존재 검증 / 수정 / 삭제)만 책임진다.
 *      - 서비스는 DTO 를 모른다 — 입력은 이미 변환된 도메인 값(Member / 필드)으로 받고, 도메인 객체를 반환한다.
 *        (DTO ↔ Entity 매핑은 컨트롤러/DTO 가 담당. 서비스는 순수 도메인 계층으로 유지한다.)
 *
 *  ▶ Mission06 과 동일하게 단일 생성자 → 자동 생성자 주입, final 필드로 불변 의존성을 보장한다.
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        if (memberRepository == null) {
            throw new IllegalArgumentException("MemberRepository 는 필수 의존성입니다.");
        }
        this.memberRepository = memberRepository;
    }

    /** 생성 — 채번된 Entity 를 반환한다. */
    public Member create(Member member) {
        return memberRepository.save(member);
    }

    /** 전체 조회. */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /** 단건 조회 — 없으면 {@link MemberNotFoundException}(→ 404). */
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    /** 수정(전체 교체) — 없으면 404. 갱신된 Entity 를 반환한다. */
    public Member update(Long id, String name, String email, int generation) {
        Member member = findById(id);
        member.update(name, email, generation);
        return member;
    }

    /** 삭제 — 대상이 없으면 404. */
    public void delete(Long id) {
        boolean removed = memberRepository.deleteById(id);
        if (!removed) {
            throw new MemberNotFoundException(id);
        }
    }
}
