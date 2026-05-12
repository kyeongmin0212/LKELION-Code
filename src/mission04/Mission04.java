package mission04;

import java.util.List;
import java.util.Map;

/**
 * Mission04 — Java Collections & 설계 확장
 *
 *  ▶ Mission03 까지의 코드는 "Lion[] 고정 길이 배열" 을 main 안에서 직접 다뤘다.
 *    Mission04 는 그 책임을 통째로 {@link LionRepository} 로 옮기고,
 *    main 은 "저장소 API 를 어떻게 쓰는가" 만 보여준다.
 *
 *  ▶ 이 데모 한 번으로 체크리스트 6개를 모두 확인할 수 있다:
 *      1) ArrayList 로 멤버 관리          — LionRepository.lions
 *      2) Map 으로 파트별 그룹핑          — repository.groupByPart()
 *      3) 제네릭 사용                     — List<Lion>, Map<Part, List<Lion>>
 *      4) 등록 시 중복 확인              — repository.register() 두 번째 호출 시 예외
 *      5) 이름으로 검색                   — repository.findByName("김")
 *      6) 파트별 필터링                  — repository.filterByPart(Part.BACKEND)
 */
public class Mission04 {

    public static void main(String[] args) {

        IntroducePolicy policy = new StandardIntroducePolicy();
        LionRepository  repo   = new LionRepository();

        // ─────────────────────────────────────────────────────────
        // 1) 등록 — ArrayList 기반 저장소에 다양한 파트의 사자를 넣는다.
        // ─────────────────────────────────────────────────────────
        repo.register(new BackendLion ("김백엔", "컴퓨터공학", 13, policy));
        repo.register(new BackendLion ("최서버", "정보통신",  13, policy));
        repo.register(new FrontendLion("이프론", "소프트웨어", 13, policy));
        repo.register(new FrontendLion("정뷰어", "디지털미디어", 13, policy));
        repo.register(new DesignLion  ("박디자", "시각디자인", 13, policy));

        System.out.println("===== [1] 전체 멤버 (등록 순서 유지) — 총 " + repo.size() + "명 =====");
        for (Lion lion : repo.findAll()) {
            lion.introduce();
        }
        System.out.println();

        // ─────────────────────────────────────────────────────────
        // 2) 중복 등록 시도 — 저장소가 예외로 막아준다.
        //    "김백엔 / 13기" 는 이미 위에서 등록했으므로 다시 넣을 수 없다.
        // ─────────────────────────────────────────────────────────
        System.out.println("===== [2] 중복 등록 시도 =====");
        try {
            repo.register(new BackendLion("김백엔", "컴퓨터공학", 13, policy));
        } catch (DuplicateLionException e) {
            System.out.println("[차단됨] " + e.getMessage());
        }

        // 동명이인이지만 기수가 다르면 같은 사자가 아니다 — 정상 등록된다.
        repo.register(new BackendLion("김백엔", "컴퓨터공학", 12, policy));
        System.out.println("[OK] 동명이인(12기)은 정상 등록 → 현재 인원: " + repo.size() + "명");
        System.out.println();

        // ─────────────────────────────────────────────────────────
        // 3) 이름으로 검색 — 부분 일치
        // ─────────────────────────────────────────────────────────
        System.out.println("===== [3] 이름 검색: \"김\" 포함 =====");
        List<Lion> kims = repo.findByName("김");
        for (Lion lion : kims) {
            lion.introduce();
        }
        System.out.println("→ " + kims.size() + "건 검색됨");
        System.out.println();

        // ─────────────────────────────────────────────────────────
        // 4) 파트별 필터링 — 단일 파트만 뽑기
        // ─────────────────────────────────────────────────────────
        System.out.println("===== [4] 파트 필터: BACKEND =====");
        for (Lion lion : repo.filterByPart(Part.BACKEND)) {
            lion.introduce();
        }
        System.out.println();

        // ─────────────────────────────────────────────────────────
        // 5) 파트별 그룹핑 — Map<Part, List<Lion>> 한 번에 보기
        // ─────────────────────────────────────────────────────────
        System.out.println("===== [5] 파트별 그룹핑 (Map) =====");
        Map<Part, List<Lion>> grouped = repo.groupByPart();
        for (Map.Entry<Part, List<Lion>> entry : grouped.entrySet()) {
            Part       part    = entry.getKey();
            List<Lion> members = entry.getValue();

            System.out.println("[" + part.label() + " 파트] " + members.size() + "명");
            for (Lion lion : members) {
                System.out.println("  · " + lion.name() + " (" + lion.major() + ", " + lion.generation() + "기)");
            }
        }
        System.out.println();

        // ─────────────────────────────────────────────────────────
        // 6) 다형성 보너스 — 같은 List<Lion> 안에서 각자의 work() 가 자식별로 실행된다.
        //    Mission03 에서 보여준 동적 디스패치가 컬렉션 위에서도 그대로 동작한다.
        // ─────────────────────────────────────────────────────────
        System.out.println("===== [6] 각자 일하기 (다형성 + 컬렉션) =====");
        for (Lion lion : repo.findAll()) {
            lion.work();
        }
    }
}
