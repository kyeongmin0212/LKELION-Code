package com.likelion.mission06.policy;

/**
 * 자기소개 출력 형식을 결정하는 정책 인터페이스. Mission05 와 동일한 계약이다.
 *
 *  ▶ Spring 환경에서는 이 인터페이스의 구현체를 {@code @Configuration + @Bean} 으로 수동 등록한다
 *    (AppConfig 참조). 컴포넌트 스캔(@Component) 으로 자동 등록해도 무방하지만,
 *    체크리스트 요구사항 "@Configuration + @Bean 수동 등록 또는 자동 등록 방식 사용" 을
 *    명시적으로 보여주기 위해 의도적으로 수동 등록 경로를 택했다.
 */
public interface IntroducePolicy {

    void introduce(String name, String major, int generation, String role);
}
