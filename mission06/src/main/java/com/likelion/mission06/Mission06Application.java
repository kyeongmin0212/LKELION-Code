package com.likelion.mission06;

import com.likelion.mission06.domain.Part;
import com.likelion.mission06.service.LionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Mission06 — Spring Boot 전환 진입점.
 *
 *  ▶ Mission05 에서는 {@code AppConfig} 의 정적 팩토리가 직접 의존성 그래프를 조립했다.
 *    Mission06 에서는 그 책임을 Spring 컨테이너가 가져간다:
 *      - {@code @SpringBootApplication} → 같은 패키지 이하 컴포넌트 스캔 활성화
 *      - {@code @Service} / {@code @Repository} / {@code @Component} 가 자동 등록되고
 *      - {@code @Configuration + @Bean} 으로 수동 등록한 빈도 함께 조립된다.
 *
 *  ▶ CommandLineRunner 빈은 부트 기동 후 한 번 실행된다 — Mission05 의 main 시연이
 *    Spring 컨텍스트 안에서도 동일하게 동작함을 보여주기 위한 데모이다.
 */
@SpringBootApplication
public class Mission06Application {

    public static void main(String[] args) {
        SpringApplication.run(Mission06Application.class, args);
    }

    @Bean
    CommandLineRunner demoRunner(LionService lionService) {
        return args -> {
            System.out.println("===== [Spring Boot 부트 후 시연] LionService 주입 검증 =====");
            lionService.enroll("김백엔", "컴퓨터공학",   13, Part.BACKEND);
            lionService.enroll("최서버", "정보통신",     13, Part.BACKEND);
            lionService.enroll("이프론", "소프트웨어",   13, Part.FRONTEND);
            lionService.enroll("박디자", "시각디자인",   13, Part.DESIGN);

            System.out.println("[전체 명단] 총 " + lionService.memberCount() + "명");
            lionService.printRoster();
            System.out.println("===== 시연 끝. GET http://localhost:8080/hello 호출해 보세요 =====");
        };
    }
}
