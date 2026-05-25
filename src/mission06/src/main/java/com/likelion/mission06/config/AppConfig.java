package com.likelion.mission06.config;

import com.likelion.mission06.policy.IntroducePolicy;
import com.likelion.mission06.policy.StandardIntroducePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 수동 빈 등록 설정 클래스 — Mission05 의 AppConfig 가 Spring 의 {@code @Configuration} 으로 진화한 형태.
 *
 *  ▶ 체크리스트 매핑:
 *      - "@Configuration + @Bean 수동 등록 또는 자동 등록 방식을 사용했는가" 의 "수동 등록" 측 증거.
 *      - 자동 등록 측은 {@code @Service / @Repository} (LionService, MemoryLionRepository, MockLionRepository) 가 담당.
 *    한 프로젝트 안에서 두 방식이 공존할 수 있다는 점도 함께 보여준다.
 *
 *  ▶ 왜 IntroducePolicy 만 수동 등록인가:
 *      - 정책(StandardIntroducePolicy) 은 도메인이 아닌 "출력 형식" 이라는 가로지르는 관심사다.
 *      - 향후 다른 포맷 정책으로 갈아끼울 가능성이 높아, 클래스 자체에 어노테이션을 박아두기보다
 *        설정 클래스 한 곳에서 명시적으로 조립하는 편이 유지보수에 유리하다.
 */
@Configuration
public class AppConfig {

    /**
     * 표준 자기소개 정책 빈 — {@link com.likelion.mission06.service.LionService} 의 두 번째 생성자 파라미터로 주입된다.
     */
    @Bean
    public IntroducePolicy introducePolicy() {
        return new StandardIntroducePolicy();
    }
}
