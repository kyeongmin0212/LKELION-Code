package com.likelion.mission07.repository;

import com.likelion.mission07.domain.Member;

import java.util.List;
import java.util.Optional;

/**
 * 멤버 저장소의 추상 계약. Mission06 의 LionRepository 와 같은 철학(구현체에만 @Repository)을 따른다.
 *
 *  ▶ 계약 요점:
 *      - {@code save} 는 id 가 없는 신규 Entity 를 받아 채번 후 저장하고, 같은 인스턴스를 돌려준다.
 *      - 조회 메서드는 절대 null 을 반환하지 않는다 (단건은 {@link Optional}, 다건은 빈 List).
 *      - {@code findAll} 이 반환하는 List 는 불변 뷰여야 한다.
 *      - {@code deleteById} 는 실제로 지웠으면 true, 대상이 없었으면 false 를 반환한다.
 */
public interface MemberRepository {

    /** 신규 멤버를 저장하며 식별자를 채번한다. 반환값은 id 가 부여된 동일 인스턴스. */
    Member save(Member member);

    Optional<Member> findById(Long id);

    List<Member> findAll();

    /** 실제 삭제됐으면 true, 대상이 없었으면 false. */
    boolean deleteById(Long id);

    boolean existsById(Long id);

    long count();
}
