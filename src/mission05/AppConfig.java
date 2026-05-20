package mission05;

/**
 * 의존성 그래프 조립 책임 클래스 — Mission05 의 DI 컨테이너 역할.
 *
 *  ▶ Spring 의 {@code @Configuration} 클래스를 손으로 구현했다고 보면 된다.
 *    "어느 구현체를 쓸지" / "그것들을 어떻게 엮을지" 의 결정권을 이 한 클래스가 전부 가진다.
 *    덕분에 {@link LionService} 와 {@link Mission05} 는 구체 클래스 이름을 전혀 모른다.
 *
 *  ▶ 정적 메서드로 제공하는 이유:
 *    "main 에서 단 한 줄도 new 가 등장하지 않도록" 하기 위해서다.
 *      - 만약 인스턴스 메서드였다면 main 이 {@code new AppConfig()} 를 호출해야 한다.
 *      - 정적 팩토리로 두면 {@code AppConfig.createLionService()} 한 줄로 끝난다.
 *    실제 Spring 환경에서는 컨테이너가 인스턴스를 관리해 주지만,
 *    프레임워크 없이 IoC 의 정수를 보여주는 가장 단순한 방법이 정적 팩토리이다.
 *
 *  ▶ 구현체 교체 시연:
 *    {@link #createLionService()} 는 운영용 메모리 저장소를 주입하고,
 *    {@link #createDemoLionService()} 는 샘플 데이터가 깔린 Mock 저장소를 주입한다.
 *    Service 코드는 단 한 글자도 다르지 않다 — 바뀌는 건 오직 이 클래스의 한 줄뿐이다.
 *    이것이 IoC/DI 가 약속하는 "구성과 사용의 분리(separation of construction & use)" 다.
 *
 *  ▶ 인스턴스화 금지 (유틸리티 클래스). 정적 메서드만 사용된다.
 */
public final class AppConfig {

    /** 인스턴스화 금지 — 모든 메서드가 static 이므로 객체를 만들 필요가 없다. */
    private AppConfig() {
        throw new UnsupportedOperationException("AppConfig 는 정적 팩토리 전용 클래스입니다.");
    }

    // ─────────────────────────────────────────────────────────────
    // 외부 공개 팩토리 — main 은 이 메서드만 호출한다
    // ─────────────────────────────────────────────────────────────

    /**
     * 운영 환경용 LionService 를 조립해서 돌려준다.
     *
     *  ▶ 저장소: {@link MemoryLionRepository} (비어 있는 상태로 시작)
     *  ▶ 정책 : {@link StandardIntroducePolicy}
     */
    public static LionService createLionService() {
        return new LionService(
                memoryRepository(),
                standardIntroducePolicy()
        );
    }

    /**
     * 데모/테스트용 LionService 를 조립해서 돌려준다.
     *
     *  ▶ 저장소: {@link MockLionRepository} (샘플 데이터 3건이 미리 들어 있음)
     *  ▶ 정책 : {@link StandardIntroducePolicy}
     *
     *  ▶ 위의 {@link #createLionService()} 와 비교하면 단 한 줄만 다르다 (Repository 구현체).
     *    Service 클래스 자체는 변경되지 않았다는 점이 DI 의 핵심 효용을 보여준다.
     */
    public static LionService createDemoLionService() {
        return new LionService(
                mockRepository(),
                standardIntroducePolicy()
        );
    }

    // ─────────────────────────────────────────────────────────────
    // 내부 빈(bean) 팩토리 — 각 의존성 한 종류당 하나씩
    // ─────────────────────────────────────────────────────────────

    /**
     * 운영용 메모리 저장소.
     *
     *  ▶ 매 호출마다 새 인스턴스를 돌려준다 (Service 마다 독립된 데이터 공간 보장).
     *    싱글톤이 필요하다면 정적 필드 캐시로 바꾸기만 하면 된다.
     */
    private static LionRepository memoryRepository() {
        return new MemoryLionRepository();
    }

    /** 데모용 Mock 저장소 (생성자에서 샘플 데이터 자동 등록). */
    private static LionRepository mockRepository() {
        return new MockLionRepository();
    }

    /** 표준 자기소개 정책. 상태가 없으므로 매번 새로 만들어도 무방하다. */
    private static IntroducePolicy standardIntroducePolicy() {
        return new StandardIntroducePolicy();
    }
}
