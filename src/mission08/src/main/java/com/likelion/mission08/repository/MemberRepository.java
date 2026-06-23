package com.likelion.mission08.repository;

import com.likelion.mission08.domain.Member;
import com.likelion.mission08.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 멤버 저장소 — 이번 미션의 핵심 변화: 직접 구현체를 짜는 대신 {@link JpaRepository} 를 상속한다.
 *
 *  ▶ Mission07 에서는 {@code MemberRepository}(인터페이스) + {@code MemoryMemberRepository}(직접 구현)가
 *    필요했지만, Spring Data JPA 는 이 인터페이스만 선언하면 런타임에 구현체(프록시)를 자동 생성한다.
 *    {@code save / findById / findAll / deleteById / existsById / count} 등 CRUD 메서드를 그대로 제공한다.
 *
 *  ▶ 메서드 이름 규칙(쿼리 메서드)만으로 SQL 이 파생된다 — 아래 {@code findByPart} 는
 *    {@code SELECT ... FROM members WHERE part = ?} 로 번역된다(직접 SQL 작성 불필요).
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /** 파트별 조회 — 메서드 이름으로 쿼리가 파생된다(WHERE part = ?). */
    List<Member> findByPart(Part part);
}
