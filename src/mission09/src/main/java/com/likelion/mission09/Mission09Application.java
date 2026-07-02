package com.likelion.mission09;

import com.likelion.mission09.domain.Assignment;
import com.likelion.mission09.domain.Member;
import com.likelion.mission09.domain.Part;
import com.likelion.mission09.domain.Team;
import com.likelion.mission09.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mission09 — 연관관계 & 트랜잭션 진입점.
 *
 *  ▶ Mission08(단일 Member Entity + JPA CRUD) 위에 연관관계를 얹은 버전:
 *      - Team(1) : Member(N)   — {@code @OneToMany(mappedBy)} / {@code @ManyToOne @JoinColumn}
 *      - Member(1) : Assignment(N) — 과제 엔티티와 멤버 간 1:N
 *      - {@code @Transactional} 로 Service 계층 트랜잭션 관리
 *
 *  ▶ CommandLineRunner 가 부트 직후 팀·멤버·과제를 연관지어 저장한다 — 콘솔에 FK 를 포함한
 *    INSERT SQL 이 찍히는 것으로 연관관계 매핑이 실제 DB 에 반영됨을 확인할 수 있다.
 */
@SpringBootApplication
public class Mission09Application {

    public static void main(String[] args) {
        SpringApplication.run(Mission09Application.class, args);
    }

    /**
     * 시드 데이터 — 하나의 트랜잭션 안에서 팀에 멤버를, 멤버에 과제를 연관지어 저장한다.
     * {@code CascadeType.ALL} 덕분에 Team 저장 한 번으로 소속 멤버·과제까지 함께 영속된다.
     */
    @Bean
    @Transactional
    CommandLineRunner seedRunner(TeamRepository teamRepository) {
        return args -> {
            Team backendTeam = new Team("백엔드 1팀");

            Member km = new Member("노경민", "km@likelion.org", 13, Part.BACKEND);
            Member fe = new Member("이프론", "frontend@likelion.org", 13, Part.FRONTEND);
            backendTeam.addMember(km); // 양방향 편의 메서드 — Member.team FK 세팅
            backendTeam.addMember(fe);

            // Member(1) : Assignment(N) — 노경민에게 과제 2건 연관
            km.addAssignment(new Assignment("Mission09", "연관관계 & 트랜잭션 구현"));
            km.addAssignment(new Assignment("Mission08", "JPA 기초 & 영속성 컨텍스트"));

            teamRepository.save(backendTeam); // cascade 로 멤버·과제까지 INSERT

            System.out.println("===== [부트 후 시연] 팀/멤버/과제 연관 저장 완료 =====");
            System.out.println("팀: " + backendTeam);
            System.out.println("멤버 수: " + backendTeam.getMembers().size()
                    + ", 노경민 과제 수: " + km.getAssignments().size());
            System.out.println("===== GET http://localhost:8080/teams/1/members / /members/1/assignments 로 확인 =====");
        };
    }
}
