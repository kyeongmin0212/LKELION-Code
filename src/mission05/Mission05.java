package mission05;

/**
 * Mission05 — 자바로 배우는 IoC/DI
 *
 *  ▶ 이 main 의 가장 중요한 특징: 단 한 줄도 {@code new} 가 등장하지 않는다.
 *    Lion / Repository / Service / Policy — 그 어떤 객체도 main 이 직접 만들지 않고,
 *    전부 {@link AppConfig} 가 조립해서 돌려준 인스턴스를 사용한다.
 *
 *  ▶ 데모 구성:
 *      [A] AppConfig.createLionService()       — 운영용 MemoryLionRepository 주입
 *      [B] AppConfig.createDemoLionService()   — 데모용 MockLionRepository 주입
 *    두 시나리오에서 호출하는 Service 메서드(printRoster, printByPart 등)는 완전히 동일하다.
 *    바뀐 건 오직 AppConfig 안의 주입 대상 한 줄뿐 — 이것이 IoC/DI 의 핵심 가치이다.
 *
 *  ▶ 체크리스트 ↔ 코드 매핑:
 *      1) Repository 인터페이스 정의            → {@link LionRepository}
 *      2) 두 개 이상의 구현체                   → {@link MemoryLionRepository}, {@link MockLionRepository}
 *      3) Service 가 Repository 에 의존         → {@link LionService} 의 final 필드
 *      4) 생성자를 통한 의존성 주입             → {@link LionService#LionService(LionRepository, IntroducePolicy)}
 *      5) AppConfig 등 설정 클래스에서 객체 조립 → {@link AppConfig}
 *      6) main 에서 직접 new 하지 않음           → 이 파일 전체에 {@code new} 키워드 없음
 *      7) README 에 본인 이름 포함              → 저장소 루트 README.md "작성자: 노경민"
 */
public class Mission05 {

    public static void main(String[] args) {

        // ─────────────────────────────────────────────────────────
        // [A] 운영 시나리오 — 비어 있는 MemoryLionRepository 가 주입된 LionService
        //     데이터는 main 이 service.enroll(...) 으로 직접 채운다.
        // ─────────────────────────────────────────────────────────
        System.out.println("===== [A] 운영 시나리오: MemoryLionRepository 주입 =====");
        LionService production = AppConfig.createLionService();

        production.enroll("김백엔", "컴퓨터공학",   13, Part.BACKEND);
        production.enroll("최서버", "정보통신",     13, Part.BACKEND);
        production.enroll("이프론", "소프트웨어",   13, Part.FRONTEND);
        production.enroll("정뷰어", "디지털미디어", 13, Part.FRONTEND);
        production.enroll("박디자", "시각디자인",   13, Part.DESIGN);

        System.out.println("[전체 명단] 총 " + production.memberCount() + "명");
        production.printRoster();
        System.out.println();

        // 중복 등록 — Repository 계약이 보장하는 DuplicateLionException 이 그대로 올라온다.
        System.out.println("===== [A-1] 중복 등록 시도 (Repository 계약 검증) =====");
        try {
            production.enroll("김백엔", "컴퓨터공학", 13, Part.BACKEND);
        } catch (DuplicateLionException e) {
            System.out.println("[차단됨] " + e.getMessage());
        }
        System.out.println();

        // 파트별 출력 — Service 가 Repository.findByPart 를 위임받아 정책 포맷으로 출력한다.
        System.out.println("===== [A-2] 파트별 출력: BACKEND =====");
        production.printByPart(Part.BACKEND);
        System.out.println();

        // 이름 검색.
        System.out.println("===== [A-3] 이름 검색: \"김\" 포함 =====");
        int hits = production.printSearchByName("김");
        System.out.println("→ " + hits + "건 검색됨");
        System.out.println();

        // 다형성 — Service 가 Lion#work 를 그대로 호출, 자식 클래스별로 다르게 실행된다.
        System.out.println("===== [A-4] 각자 일하기 (다형성) =====");
        production.printWorkAssignments();
        System.out.println();

        // ─────────────────────────────────────────────────────────
        // [B] 데모/테스트 시나리오 — MockLionRepository (샘플 3건 미리 적재) 주입
        //     Service 사용 코드는 위와 완전히 동일하다. 바뀐 건 AppConfig 의 한 줄뿐.
        // ─────────────────────────────────────────────────────────
        System.out.println("===== [B] 데모 시나리오: MockLionRepository 주입 (Service 코드 변경 없음) =====");
        LionService demo = AppConfig.createDemoLionService();

        System.out.println("[샘플 명단] 총 " + demo.memberCount() + "명 (Mock 저장소가 생성자에서 미리 깔아둠)");
        demo.printRoster();
        System.out.println();

        // Mock 저장소에도 추가 등록 가능 — 인터페이스 계약이 동일하므로 호출 코드도 동일.
        demo.enroll("추가테스터", "테스트학과", 13, Part.BACKEND);
        System.out.println("[추가 등록 후] 총 " + demo.memberCount() + "명");
        demo.printByPart(Part.BACKEND);
    }
}
