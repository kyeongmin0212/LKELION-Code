package mission05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 실제 메모리(ArrayList) 기반의 {@link LionRepository} 구현체 — 운영용 기본 저장소.
 *
 *  ▶ Mission04 의 LionRepository 클래스가 이 자리로 옮겨왔다.
 *    "ArrayList 로 저장하고, contains 로 중복을 막는다" 는 구현 디테일은 동일하다.
 *  ▶ 달라진 점은 단 하나, 더 이상 main 이 이 클래스 이름을 직접 알 필요가 없다는 것이다.
 *    {@link AppConfig} 한 곳에서만 {@code new MemoryLionRepository()} 가 등장하며,
 *    그 결과 {@link LionService} 와 {@link Mission05} 어디에도 이 클래스 이름이 나오지 않는다.
 *  ▶ 운영용 저장소이므로 시작 상태는 비어 있다. 데이터는 외부에서 save() 로 채운다.
 */
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
