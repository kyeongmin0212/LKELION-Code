package mission04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 아기사자 명단을 관리하는 컬렉션 저장소 — Mission04 의 핵심 클래스.
 *
 *  ▶ Mission01 에서는 {@code Lion[] lions = new Lion[5]} 같은 고정 길이 배열을 직접 다뤘고,
 *    "5명 미만" 같은 인덱스 관리·배열 복사 같은 잡일을 main 이 직접 짊어졌다.
 *  ▶ Mission04 에서는 그 책임을 전부 컬렉션 프레임워크와 이 저장소 클래스에 위임한다.
 *      - 저장:    {@link ArrayList} (크기 관리 자동, 임의 추가 O(1) amortized)
 *      - 그룹핑:  {@link EnumMap}<Part, List<Lion>> (Part enum 을 키로 쓰는 가장 빠른 Map 구현)
 *      - 제네릭: 모든 컬렉션이 {@code <Lion>} 으로 매개변수화 — 꺼낼 때 캐스팅 한 줄 없다.
 *
 *  ▶ 저장소는 단순히 add/get 하는 곳이 아니라
 *    "데이터 무결성(중복 차단)" + "검색/필터 질의" 의 책임도 함께 가진다.
 *    이 책임을 한 곳에 모아둬야 main 이 매번 같은 검증 코드를 재작성하지 않는다.
 *
 *  ▶ 외부로 내보내는 List 는 항상 {@link Collections#unmodifiableList(List)} 로 감싼다.
 *    호출자가 받은 리스트에 add/remove 하면 저장소 내부 상태가 깨지므로,
 *    "꺼내서 본다" 는 보장되지만 "꺼내서 고친다" 는 막는다.
 */
public class LionRepository {

    /** 원본 저장소 — 등록 순서를 유지하기 위해 ArrayList 사용. */
    private final List<Lion> lions = new ArrayList<>();

    // ─────────────────────────────────────────────────────────────
    // 등록 — 중복 차단 포함
    // ─────────────────────────────────────────────────────────────

    /**
     * 새 사자를 등록한다.
     *
     *  ▶ 중복 판정은 Lion#equals (이름 + 기수) 가 책임진다.
     *    저장소는 "이미 들어있는가?" 만 묻고, "같다는 게 무슨 뜻인가" 는 도메인 객체에 맡긴다.
     *  ▶ List#contains 는 내부적으로 모든 원소에 equals 를 호출하므로 O(n) 이지만,
     *    명단 규모가 수십~수백 수준인 PBL 도메인에선 충분히 빠르고 가독성이 좋다.
     *
     * @throws IllegalArgumentException  lion 이 null 인 경우
     * @throws DuplicateLionException    같은 (이름, 기수) 의 사자가 이미 등록된 경우
     */
    public void register(Lion lion) {
        if (lion == null) {
            throw new IllegalArgumentException("등록 대상이 null 입니다.");
        }
        if (lions.contains(lion)) {
            throw new DuplicateLionException(
                    String.format("이미 등록된 사자입니다: %s (%d기)", lion.name(), lion.generation()));
        }
        lions.add(lion);
    }

    // ─────────────────────────────────────────────────────────────
    // 조회 — 전체
    // ─────────────────────────────────────────────────────────────

    /** 등록된 모든 사자를 반환한다 (수정 불가 뷰). */
    public List<Lion> findAll() {
        return Collections.unmodifiableList(lions);
    }

    /** 등록된 사자 수. */
    public int size() {
        return lions.size();
    }

    // ─────────────────────────────────────────────────────────────
    // 조회 — 이름 검색
    // ─────────────────────────────────────────────────────────────

    /**
     * 이름에 주어진 키워드가 포함된 사자들을 돌려준다 (부분 일치, 대소문자 무시).
     *
     *  ▶ "완전 일치" 가 아니라 "포함" 으로 잡은 이유:
     *    명단 검색의 실사용 패턴은 보통 "성만 입력해서 김씨를 찾는다" 식이기 때문.
     *  ▶ 결과가 0건이어도 빈 리스트를 반환한다 (null 대신).
     *    호출자 입장에서 if(result != null) 같은 방어 코드를 안 써도 되도록.
     */
    public List<Lion> findByName(String keyword) {
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

    // ─────────────────────────────────────────────────────────────
    // 조회 — 파트별 필터링 / 그룹핑
    // ─────────────────────────────────────────────────────────────

    /** 특정 파트의 사자만 골라서 돌려준다. */
    public List<Lion> filterByPart(Part part) {
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

    /**
     * 모든 사자를 파트 기준으로 한 번에 그룹핑한 Map 을 돌려준다.
     *
     *  ▶ 사자가 0명인 파트도 빈 리스트로 채워서 반환한다.
     *    → 호출자가 모든 파트를 일관된 형태로 순회할 수 있다 ("디자인 파트: 0명" 도 자연스레 출력됨).
     *
     *  ▶ Map 구현체로 {@link EnumMap} 을 쓴 이유:
     *    enum 키 전용 최적화 구현이라 HashMap 보다 빠르고 메모리도 작다.
     *    또, 출력 순서가 enum 선언 순서로 고정되기 때문에 "파트별 명단" 표시 순서가 안정적이다.
     */
    public Map<Part, List<Lion>> groupByPart() {
        Map<Part, List<Lion>> grouped = new EnumMap<>(Part.class);
        for (Part part : Part.values()) {
            grouped.put(part, new ArrayList<>());
        }
        for (Lion lion : lions) {
            grouped.get(lion.part()).add(lion);
        }
        return grouped;
    }
}
