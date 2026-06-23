package com.likelion.mission08;

import com.likelion.mission08.domain.Member;
import com.likelion.mission08.domain.Part;
import com.likelion.mission08.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Mission08 — JPA 기초 & 영속성 컨텍스트 진입점.
 *
 *  ▶ Mission07(REST API + 인메모리 저장소) 위에 영속 계층을 JPA 로 교체한 버전:
 *      - {@code @Entity Member} 가 DB 테이블 {@code members} 와 매핑된다.
 *      - {@code JpaRepository} 상속만으로 CRUD 가 실제 DB(H2)에 반영된다.
 *      - 콘솔에 Hibernate 가 실행하는 실제 SQL 이 출력된다(show-sql + format_sql).
 *
 *  ▶ CommandLineRunner 가 부트 직후 시드 멤버 3명을 저장한다 — 이때 콘솔에 INSERT SQL 이 찍히는 것으로
 *    "CRUD 가 DB 에 반영됨" 을 즉시 확인할 수 있다.
 */
@SpringBootApplication
public class Mission08Application {

    public static void main(String[] args) {
        SpringApplication.run(Mission08Application.class, args);
    }

    @Bean
    CommandLineRunner seedRunner(MemberRepository memberRepository) {
        return args -> {
            memberRepository.save(new Member("김백엔", "backend@likelion.org", 13, Part.BACKEND));
            memberRepository.save(new Member("이프론", "frontend@likelion.org", 13, Part.FRONTEND));
            memberRepository.save(new Member("박디자", "design@likelion.org", 13, Part.DESIGN));

            System.out.println("===== [부트 후 시연] 시드 멤버 " + memberRepository.count() + "명 DB 저장 완료 =====");
            System.out.println("===== GET http://localhost:8080/members 또는 /h2-console 로 확인하세요 =====");
        };
    }
}
