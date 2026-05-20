package mission05;

import java.util.List;

/**
 * 아기사자 도메인의 애플리케이션 서비스 — Mission05 의 DI 주체.
 *
 *  ▶ 의존성 두 가지를 모두 "생성자 주입(Constructor Injection)" 으로 받는다.
 *      1) {@link LionRepository}   : 어떻게 저장/조회할지
 *      2) {@link IntroducePolicy}  : 어떻게 자기소개를 출력할지
 *    필드는 모두 final 이며, 한 번 주입된 의존성은 객체 수명 동안 절대 바뀌지 않는다.
 *    → 불변 의존성 + 명시적 생성자 시그니처 = 테스트 시 가짜 객체 주입이 쉬워진다.
 *
 *  ▶ 왜 생성자 주입인가:
 *      - 누락된 의존성이 있으면 객체를 만드는 시점에 즉시 NPE / 컴파일러 에러로 드러난다
 *        (setter 주입이라면 메서드를 처음 호출하는 한참 뒤에야 드러난다).
 *      - 의존성이 final 로 박혀 멀티스레드 환경에서도 안전하다.
 *      - 클래스의 "필요한 협력자가 누구인지" 가 생성자 시그니처 한 줄에 다 보인다.
 *
 *  ▶ Service 는 절대 직접 {@code new MemoryLionRepository()} / {@code new StandardIntroducePolicy()}
 *    같은 구체 클래스 생성을 하지 않는다. 그 결정권은 전부 {@link AppConfig} 가 가진다.
 *    이것이 IoC(Inversion of Control) — "의존 대상을 직접 만들지 않고 외부로부터 받는다" 의 핵심.
 *
 *  ▶ Service 는 또한 Lion 인스턴스 생성의 책임도 흡수한다 ({@link #enroll}).
 *    덕분에 호출 측({@link Mission05} main) 은 BackendLion / FrontendLion 같은
 *    구체 클래스 이름을 알 필요가 없고, "어떤 파트로 등록할지" 만 Part enum 으로 전달한다.
 */
public class LionService {

    private final LionRepository  repository;
    private final IntroducePolicy introducePolicy;

    /**
     * 생성자 주입 — 모든 협력자를 한 번에 받고 final 로 고정한다.
     *
     * @throws IllegalArgumentException  주입된 의존성이 null 인 경우
     */
    public LionService(LionRepository repository, IntroducePolicy introducePolicy) {
        if (repository == null) {
            throw new IllegalArgumentException("LionRepository 는 필수 의존성입니다.");
        }
        if (introducePolicy == null) {
            throw new IllegalArgumentException("IntroducePolicy 는 필수 의존성입니다.");
        }
        this.repository      = repository;
        this.introducePolicy = introducePolicy;
    }

    // ─────────────────────────────────────────────────────────────
    // 등록 — Lion 생성까지 Service 가 캡슐화한다
    // ─────────────────────────────────────────────────────────────

    /**
     * 사자를 등록한다. Part enum 을 받아 적절한 Lion 자식 클래스를 직접 생성한다.
     *
     *  ▶ 이 메서드 덕분에 main 에서 {@code new BackendLion(...)} 같은 코드가 사라진다.
     *    main 은 "이름·전공·기수·파트" 라는 도메인 입력만 넘기면 된다.
     */
    public void enroll(String name, String major, int generation, Part part) {
        if (part == null) {
            throw new IllegalArgumentException("파트는 null 일 수 없습니다.");
        }
        Lion lion = createLion(name, major, generation, part);
        repository.save(lion);
    }

    /** Part 에 따라 어떤 자식 클래스를 만들지 한 곳에서 결정한다 (간이 팩토리). */
    private Lion createLion(String name, String major, int generation, Part part) {
        switch (part) {
            case BACKEND:  return new BackendLion (name, major, generation);
            case FRONTEND: return new FrontendLion(name, major, generation);
            case DESIGN:   return new DesignLion  (name, major, generation);
            default:       throw new IllegalArgumentException("알 수 없는 파트: " + part);
        }
    }

    // ─────────────────────────────────────────────────────────────
    // 조회 — 단순히 Repository 를 그대로 노출하지 않고 의도를 가진 메서드로 감싼다
    // ─────────────────────────────────────────────────────────────

    /** 전체 인원 수. */
    public int memberCount() {
        return repository.size();
    }

    /** 전체 사자의 자기소개를 정책 포맷대로 출력한다. */
    public void printRoster() {
        for (Lion lion : repository.findAll()) {
            introducePolicy.introduce(lion.name(), lion.major(), lion.generation(), lion.role());
        }
    }

    /** 전체 사자가 각자 자기 일을 수행한다 (Lion#work 의 다형성 시연). */
    public void printWorkAssignments() {
        for (Lion lion : repository.findAll()) {
            lion.work();
        }
    }

    /** 특정 파트의 자기소개만 출력한다. */
    public void printByPart(Part part) {
        for (Lion lion : repository.findByPart(part)) {
            introducePolicy.introduce(lion.name(), lion.major(), lion.generation(), lion.role());
        }
    }

    /** 이름으로 검색한 결과의 자기소개를 출력한다. 매칭 건수를 반환한다. */
    public int printSearchByName(String keyword) {
        List<Lion> hits = repository.searchByName(keyword);
        for (Lion lion : hits) {
            introducePolicy.introduce(lion.name(), lion.major(), lion.generation(), lion.role());
        }
        return hits.size();
    }
}
