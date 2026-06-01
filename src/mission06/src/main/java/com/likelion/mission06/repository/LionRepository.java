package com.likelion.mission06.repository;

import com.likelion.mission06.domain.Lion;
import com.likelion.mission06.domain.Part;
import com.likelion.mission06.exception.DuplicateLionException;

import java.util.List;

/**
 * 아기사자 저장소의 추상 계약. Mission05 의 LionRepository 인터페이스를 그대로 이전했다.
 *
 *  ▶ Spring 환경에서의 의미:
 *      - 인터페이스 자체에는 어노테이션을 붙이지 않는다 (구현체에 {@code @Repository} 를 붙인다).
 *      - 두 개 이상의 구현체가 존재하므로({@code MemoryLionRepository}, {@code MockLionRepository})
 *        하나를 {@code @Primary} 로 지정해 기본 주입 대상을 명시한다. 다른 구현체는 {@code @Qualifier}
 *        또는 빈 이름으로 선택할 수 있다.
 *
 *  ▶ 계약 요점:
 *      - 모든 조회 메서드는 절대 null 을 반환하지 않는다 (결과가 없으면 빈 List).
 *      - {@code save} 는 동일 정체성(이름 + 기수)이 이미 있으면 {@link DuplicateLionException} 을 던진다.
 *      - 반환되는 List 는 불변 뷰여야 한다.
 */
public interface LionRepository {

    void save(Lion lion);

    List<Lion> findAll();

    int size();

    List<Lion> findByPart(Part part);

    List<Lion> searchByName(String keyword);
}
