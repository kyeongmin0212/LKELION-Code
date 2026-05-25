package com.likelion.mission06.service;

import com.likelion.mission06.domain.BackendLion;
import com.likelion.mission06.domain.DesignLion;
import com.likelion.mission06.domain.FrontendLion;
import com.likelion.mission06.domain.Lion;
import com.likelion.mission06.domain.Part;
import com.likelion.mission06.policy.IntroducePolicy;
import com.likelion.mission06.repository.LionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 아기사자 도메인의 애플리케이션 서비스. Mission05 의 LionService 를 Spring 빈으로 전환했다.
 *
 *  ▶ Spring 환경에서의 변화 (한 줄 요약):
 *      - 클래스에 {@code @Service} 어노테이션을 추가했다 (자동 빈 등록).
 *      - 생성자가 한 개뿐인 클래스이므로 Spring 4.3+ 부터는 {@code @Autowired} 를 생략해도
 *        Spring 이 자동으로 생성자 주입을 수행한다 (명시성을 위해 자바독에서 강조한다).
 *      - 그 외 비즈니스 로직 / final 필드 / null 검증은 Mission05 와 완전히 동일하다.
 *
 *  ▶ 왜 생성자 주입인가:
 *      - 필드 주입({@code @Autowired private LionRepository repo;}) 과 달리 final 로 박을 수 있어 불변이 보장된다.
 *      - 누락된 의존성이 있으면 컨테이너 기동 시점에 즉시 BeanCreationException 으로 드러난다.
 *      - 단위 테스트에서 컨테이너 없이 {@code new LionService(mockRepo, mockPolicy)} 로 직접 주입 가능하다.
 */
@Service
public class LionService {

    private final LionRepository  repository;
    private final IntroducePolicy introducePolicy;

    /**
     * 생성자 주입 — Spring 컨테이너가 {@code @Primary} 가 붙은 {@link com.likelion.mission06.repository.MemoryLionRepository}
     * 와 {@code @Bean} 으로 등록된 {@link com.likelion.mission06.policy.StandardIntroducePolicy} 를 자동 주입한다.
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

    public void enroll(String name, String major, int generation, Part part) {
        if (part == null) {
            throw new IllegalArgumentException("파트는 null 일 수 없습니다.");
        }
        Lion lion = createLion(name, major, generation, part);
        repository.save(lion);
    }

    private Lion createLion(String name, String major, int generation, Part part) {
        switch (part) {
            case BACKEND:  return new BackendLion (name, major, generation);
            case FRONTEND: return new FrontendLion(name, major, generation);
            case DESIGN:   return new DesignLion  (name, major, generation);
            default:       throw new IllegalArgumentException("알 수 없는 파트: " + part);
        }
    }

    public int memberCount() {
        return repository.size();
    }

    public List<Lion> findAll() {
        return repository.findAll();
    }

    public void printRoster() {
        for (Lion lion : repository.findAll()) {
            introducePolicy.introduce(lion.name(), lion.major(), lion.generation(), lion.role());
        }
    }

    public void printWorkAssignments() {
        for (Lion lion : repository.findAll()) {
            lion.work();
        }
    }

    public void printByPart(Part part) {
        for (Lion lion : repository.findByPart(part)) {
            introducePolicy.introduce(lion.name(), lion.major(), lion.generation(), lion.role());
        }
    }

    public int printSearchByName(String keyword) {
        List<Lion> hits = repository.searchByName(keyword);
        for (Lion lion : hits) {
            introducePolicy.introduce(lion.name(), lion.major(), lion.generation(), lion.role());
        }
        return hits.size();
    }
}
