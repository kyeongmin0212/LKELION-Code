package com.likelion.mission07;

import com.likelion.mission07.domain.Member;
import com.likelion.mission07.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Mission07 — REST API(Member CRUD) 진입점.
 *
 *  ▶ Mission06 에서 Spring 컨테이너로 DI 를 옮겼다면, Mission07 은 그 위에 "웹 자원으로서의 Member" 를
 *    RESTful 하게 노출한다:
 *      - {@code POST   /members}      → 생성 (201 Created + Location 헤더)
 *      - {@code GET    /members}      → 전체 조회 (200 OK)
 *      - {@code GET    /members/{id}} → 단건 조회 (200 OK / 404 Not Found)
 *      - {@code PUT    /members/{id}} → 수정 (200 OK / 404 Not Found)
 *      - {@code DELETE /members/{id}} → 삭제 (204 No Content / 404 Not Found)
 *
 *  ▶ 설계 핵심:
 *      - 요청/응답 DTO 를 도메인 {@link Member} 와 분리한다 (입력 검증·표현 책임을 Entity 에서 떼어냄).
 *      - 컨트롤러는 {@code ResponseEntity} 로 HTTP 상태 코드를 명시적으로 통제한다.
 *
 *  ▶ CommandLineRunner 빈은 부트 직후 한 번 실행되어, 빈 저장소에 시연용 멤버 몇 명을 미리 채워 둔다.
 *    덕분에 기동 직후 {@code GET /members} 만 호출해도 비어있지 않은 응답을 바로 확인할 수 있다.
 */
@SpringBootApplication
public class Mission07Application {

    public static void main(String[] args) {
        SpringApplication.run(Mission07Application.class, args);
    }

    @Bean
    CommandLineRunner seedRunner(MemberRepository memberRepository) {
        return args -> {
            memberRepository.save(new Member("김백엔", "backend@likelion.org", 13));
            memberRepository.save(new Member("이프론", "frontend@likelion.org", 13));
            memberRepository.save(new Member("박디자", "design@likelion.org", 13));

            System.out.println("===== [부트 후 시연] 시드 멤버 " + memberRepository.count() + "명 등록 완료 =====");
            System.out.println("===== GET http://localhost:8080/members 를 호출해 보세요 =====");
        };
    }
}
