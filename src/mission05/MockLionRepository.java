package mission05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 미리 샘플 데이터를 깔아둔 가짜 저장소 — 데모/테스트용 두 번째 구현체.
 *
 *  ▶ 존재 이유:
 *      - "Service 가 Repository 인터페이스에만 의존한다" 는 사실을 실증하기 위해서다.
 *        AppConfig 에서 구현체 한 줄만 갈아끼우면, Service 코드를 한 글자도 안 바꾸고
 *        완전히 다른 데이터 소스로 동작한다 → DI 의 핵심 가치를 눈으로 보여준다.
 *      - 또한 UI 시연이나 단위 테스트처럼 "DB 없이도 그럴듯한 데이터가 필요할 때"
 *        이 구현체를 골라 쓰면 외부 의존 없이 즉시 화면을 채울 수 있다.
 *
 *  ▶ 동작 사양:
 *      - 생성자에서 샘플 사자 3명을 자동 등록한다 (백엔드 / 프론트엔드 / 디자인 각 1명).
 *      - 그 외 메서드 동작은 {@link MemoryLionRepository} 와 동일하다 (save 도 정상 동작).
 *      - 따라서 호출 측은 이 구현체와 메모리 구현체를 구분할 필요가 전혀 없다.
 */
public class MockLionRepository implements LionRepository {

    private final List<Lion> lions = new ArrayList<>();

    public MockLionRepository() {
        lions.add(new BackendLion ("샘플백", "샘플학과",   13));
        lions.add(new FrontendLion("샘플프", "샘플학과",   13));
        lions.add(new DesignLion  ("샘플디", "샘플학과",   13));
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
