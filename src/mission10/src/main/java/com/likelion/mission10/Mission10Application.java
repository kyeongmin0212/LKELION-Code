package com.likelion.mission10;

import com.likelion.mission10.domain.Member;
import com.likelion.mission10.domain.Part;
import com.likelion.mission10.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Mission10 — 예외 처리 & FE 연동 진입점.
 *
 *  ▶ Mission08 의 REST + JPA CRUD 위에:
 *      - {@code @RestControllerAdvice} 전역 예외 처리 + 통일된 {@code ErrorResponse}
 *      - 검색 API ({@code GET /members/search?keyword=})
 *      - fetch() 로 CRUD 를 호출하는 프론트엔드({@code static/index.html})
 *    를 얹었다.
 *
 *  ▶ 부팅 직후 CommandLineRunner 가 시드 멤버 3명을 저장한다 →
 *    브라우저에서 {@code http://localhost:8080/} 접속 시 즉시 목록이 보인다.
 */
@SpringBootApplication
public class Mission10Application {

    public static void main(String[] args) {
        SpringApplication.run(Mission10Application.class, args);
    }

    @Bean
    CommandLineRunner seedRunner(MemberRepository memberRepository) {
        return args -> {
            memberRepository.save(new Member("김백엔", "backend@likelion.org", 13, Part.BACKEND));
            memberRepository.save(new Member("이프론", "frontend@likelion.org", 13, Part.FRONTEND));
            memberRepository.save(new Member("박디자", "design@likelion.org", 13, Part.DESIGN));

            System.out.println("===== [부트 후 시연] 시드 멤버 " + memberRepository.count() + "명 DB 저장 완료 =====");
            System.out.println("===== 브라우저에서 http://localhost:8080/ 접속 → CRUD & 검색 화면 =====");
        };
    }
}
