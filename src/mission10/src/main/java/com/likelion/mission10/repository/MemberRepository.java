package com.likelion.mission10.repository;

import com.likelion.mission10.domain.Member;
import com.likelion.mission10.domain.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 멤버 저장소 — {@link JpaRepository} 상속으로 기본 CRUD 를 자동 제공받는다.
 *
 *  ▶ 이 미션에서 추가된 검색 쿼리 메서드:
 *      - {@link #findByNameContainingIgnoreCase} : 이름 부분 일치(대소문자 무시) 검색.
 *      - {@link #findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase} : 이름 또는 이메일 부분 일치 검색.
 *    메서드 이름 규칙만으로 {@code WHERE name LIKE %?% } 형태의 SQL 이 파생된다(직접 SQL 작성 불필요).
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    /** 파트별 조회 — WHERE part = ?. */
    List<Member> findByPart(Part part);

    /** 이름 부분 일치(대소문자 무시) — WHERE lower(name) LIKE lower('%키워드%'). */
    List<Member> findByNameContainingIgnoreCase(String keyword);

    /** 이름 또는 이메일에 키워드가 포함되는 멤버 검색(대소문자 무시). */
    List<Member> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String nameKeyword, String emailKeyword);
}
