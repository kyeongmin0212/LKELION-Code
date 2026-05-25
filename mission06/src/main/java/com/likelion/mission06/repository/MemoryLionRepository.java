package com.likelion.mission06.repository;

import com.likelion.mission06.domain.Lion;
import com.likelion.mission06.domain.Part;
import com.likelion.mission06.exception.DuplicateLionException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ArrayList 기반의 운영용 저장소 구현체.
 *
 *  ▶ Spring 빈 등록: {@code @Repository} 어노테이션으로 자동 등록된다.
 *    - 빈 이름 기본값: "memoryLionRepository" (클래스명 + 첫 글자 소문자).
 *    - {@code @Primary} 를 함께 지정해, 같은 타입의 구현체가 여러 개일 때 기본 주입 대상이 된다.
 *  ▶ Mission05 의 MemoryLionRepository 와 비교한 단 하나의 변화는 어노테이션 두 줄뿐 — 그 외 동작은 동일하다.
 */
@Repository
@Primary
public class MemoryLionRepository implements LionRepository {

    private final List<Lion> lions = new ArrayList<>();

    @Override
    public void save(Lion lion) {
        if (lion == null) {
            throw new IllegalArgumentException("등록 대상이 null 입니다.");
        }
        if (lions.contains(lion)) {
            throw new DuplicateLionException(
                    String.format("이미 등록된 사자입니다: %s (%d기)", lion.name(), lion.generation()));
        }
        lions.add(lion);
    }

    @Override
    public List<Lion> findAll() {
        return Collections.unmodifiableList(lions);
    }

    @Override
    public int size() {
        return lions.size();
    }

    @Override
    public List<Lion> findByPart(Part part) {
        if (part == null) {
            throw new IllegalArgumentException("파트는 null 일 수 없습니다.");
        }
        List<Lion> hits = new ArrayList<>();
        for (Lion lion : lions) {
            if (lion.part() == part) {
                hits.add(lion);
            }
        }
        return Collections.unmodifiableList(hits);
    }

    @Override
    public List<Lion> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어는 빈 값일 수 없습니다.");
        }
        String needle = keyword.trim().toLowerCase();

        List<Lion> hits = new ArrayList<>();
        for (Lion lion : lions) {
            if (lion.name().toLowerCase().contains(needle)) {
                hits.add(lion);
            }
        }
        return Collections.unmodifiableList(hits);
    }
}
