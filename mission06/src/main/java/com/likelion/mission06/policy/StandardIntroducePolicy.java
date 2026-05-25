package com.likelion.mission06.policy;

/**
 * 표준(공식) 자기소개 포맷 — 한 줄 명단 형태로 출력한다.
 *
 *  ▶ Spring 빈으로 등록되는 위치: {@code com.likelion.mission06.config.AppConfig} 의 {@code @Bean} 메서드.
 *    클래스 자체에는 {@code @Component} 를 붙이지 않는다 — "수동 등록" 의 의도를 분명히 하기 위해서다.
 */
public class StandardIntroducePolicy implements IntroducePolicy {

    @Override
    public void introduce(String name, String major, int generation, String role) {
        System.out.println("- 이름: " + name
                + " / 전공: " + major
                + " / 기수: " + generation + "기"
                + " / 역할: " + role);
    }
}
