package mission05;

import java.util.List;

/**
 * 아기사자 저장소의 추상 계약 (Mission05 의 핵심 인터페이스).
 *
 *  ▶ Mission04 에서는 {@code LionRepository} 가 ArrayList 구현체 자체였다.
 *    Mission05 에서는 이 클래스를 인터페이스로 승격하고 구현체를 두 개로 분리한다:
 *      - {@link MemoryLionRepository} : 실제 메모리(ArrayList) 기반 저장소
 *      - {@link MockLionRepository}   : 미리 샘플 데이터를 깔아둔 테스트/데모용 가짜 저장소
 *
 *  ▶ 이 인터페이스 덕분에 {@link LionService} 는 "저장소가 어떤 구현체인지" 를 알 필요가 없다.
 *    Service 는 LionRepository 타입에만 의존하고, 실제 구현체 선택은 {@link AppConfig} 가 결정한다.
 *    → 이것이 IoC(Inversion of Control): 의존 대상의 결정권을 호출 측(Service)이 아니라
 *      외부 조립자(AppConfig)가 가져가는 구조이다.
 *
 *  ▶ 계약 요점:
 *      - 모든 조회 메서드는 절대 null 을 반환하지 않는다. 결과가 없으면 빈 List 를 돌려준다.
 *      - {@link #save(Lion)} 은 동일 정체성(이름 + 기수)이 이미 있으면
 *        {@link DuplicateLionException} 을 던진다. 호출 측은 한 가지 예외 타입만 알면 된다.
 *      - 반환되는 List 는 불변 뷰여야 한다 (구현체가 unmodifiableList 등으로 보장).
 */
public interface LionRepository {

    /**
     * 사자를 저장한다.
     *
     * @throws IllegalArgumentException  lion 이 null 인 경우
     * @throws DuplicateLionException    같은 (이름, 기수) 가 이미 등록된 경우
     */
    void save(Lion lion);

    /** 등록된 모든 사자 (불변 뷰, 등록 순서 유지). */
    List<Lion> findAll();

    /** 등록된 사자 수. */
    int size();

    /** 특정 파트의 사자만 (불변 뷰). 없으면 빈 List. */
    List<Lion> findByPart(Part part);

    /** 이름에 keyword 가 포함된 사자만 (부분 일치, 대소문자 무시). 없으면 빈 List. */
    List<Lion> searchByName(String keyword);
}
