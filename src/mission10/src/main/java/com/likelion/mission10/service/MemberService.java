package com.likelion.mission10.service;

import com.likelion.mission10.domain.Member;
import com.likelion.mission10.domain.Part;
import com.likelion.mission10.exception.MemberNotFoundException;
import com.likelion.mission10.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 멤버 애플리케이션 서비스 — CRUD + 검색 비즈니스 로직.
 *
 *  ▶ 이 미션의 예외 처리 규약: 서비스는 실패 상황에서 <b>예외를 던지는 방식</b>으로 리팩토링되어 있다.
 *    (존재하지 않으면 {@link MemberNotFoundException}). 상태 코드 변환은 컨트롤러가 아니라
 *    {@code GlobalExceptionHandler} 가 담당한다 — 서비스는 도메인 규칙에만 집중한다.
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

    /**
     * 검색 — 이름 또는 이메일에 키워드가 포함된 멤버를 조회한다(대소문자 무시).
     *
     *  ▶ 이 미션에서 추가된 검색 기능. 키워드가 비어 있으면 전체를 반환한다.
     */
    public List<Member> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return memberRepository.findAll();
        }
        String trimmed = keyword.trim();
        return memberRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(trimmed, trimmed);
    }

    /** 단건 조회 — 없으면 {@link MemberNotFoundException}(→ 404). */
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException(id));
    }

    /** 수정(전체 교체) — 없으면 404. 영속 Entity 필드 변경 → 커밋 시 dirty checking 으로 UPDATE. */
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
