package com.likelion.mission06.controller;

import com.likelion.mission06.domain.Lion;
import com.likelion.mission06.service.LionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 가장 단순한 인사용 컨트롤러. 체크리스트의 "GET /hello API가 정상 동작하는가" 항목을 담당한다.
 *
 *  ▶ 동시에 Spring DI 가 살아있다는 것을 한 번에 보여주기 위해, 생성자 주입으로 받은
 *    {@link LionService} 의 현재 상태를 함께 노출한다 — {@code /hello/lions} 가 그 역할.
 *  ▶ {@code @RestController} 는 {@code @Controller + @ResponseBody} 합성 어노테이션이며,
 *    이 컨트롤러 자체도 컴포넌트 스캔으로 자동 등록되는 Spring 빈이다.
 */
@RestController
public class HelloController {

    private final LionService lionService;

    public HelloController(LionService lionService) {
        this.lionService = lionService;
    }

    /** 미션06 체크리스트의 필수 엔드포인트. */
    @GetMapping("/hello")
    public String hello() {
        return "Hello, Spring Boot! (Mission06 - LKELION 작성자: 노경민)";
    }

    /** Spring 컨테이너가 LionService 를 정상 주입했는지 확인하는 보너스 엔드포인트. */
    @GetMapping("/hello/lions")
    public List<String> lions() {
        return lionService.findAll().stream()
                .map(Lion::toString)
                .collect(Collectors.toList());
    }
}
