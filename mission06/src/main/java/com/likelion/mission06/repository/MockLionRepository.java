package com.likelion.mission06.repository;

import com.likelion.mission06.domain.BackendLion;
import com.likelion.mission06.domain.DesignLion;
import com.likelion.mission06.domain.FrontendLion;
import com.likelion.mission06.domain.Lion;
import com.likelion.mission06.domain.Part;
import com.likelion.mission06.exception.DuplicateLionException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 샘플 데이터를 미리 깔아둔 데모/테스트용 저장소 구현체.
 *
 *  ▶ Spring 빈 등록: {@code @Repository("mockLionRepository")} 로 명시적 이름을 부여한다.
 *    - {@code MemoryLionRepository} 가 {@code @Primary} 이므로 이 빈은 기본 주입 대상이 아니다.
 *    - 필요할 때 {@code @Qualifier("mockLionRepository")} 로 명시 선택해 사용한다.
 *  ▶ 두 개 이상의 구현체가 존재해야 IoC 의 효용("주입 대상 한 줄만 바꾸면 동작이 바뀐다")이 의미를 가진다.
 */
@Repository("mockLionRepository")
public class MockLionRepository implements LionRepository {

    private final List<Lion> lions = new ArrayList<>();

    public MockLionRepository() {
        lions.add(new BackendLion ("샘플백", "샘플학과", 13));
        lions.add(new FrontendLion("샘플프", "샘플학과", 13));
        lions.add(new DesignLion  ("샘플디", "샘플학과", 13));
    }

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
