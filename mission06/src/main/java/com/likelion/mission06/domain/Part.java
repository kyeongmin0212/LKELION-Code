package com.likelion.mission06.domain;

/**
 * 아기사자가 속한 파트 식별자. Mission05 의 Part 와 동일하다.
 *
 *  ▶ Spring 환경에서도 enum 은 그대로 도메인 타입으로 사용된다.
 *    LionService 의 enroll() 이 어떤 자식 클래스를 만들지 결정할 때 이 enum 을 분기 키로 쓴다.
 */
public enum Part {

    BACKEND ("백엔드",   "백엔드 개발자"),
    FRONTEND("프론트엔드", "프론트엔드 개발자"),
    DESIGN  ("디자인",   "디자이너");

    private final String label;
    private final String roleName;

    Part(String label, String roleName) {
        this.label    = label;
        this.roleName = roleName;
    }

    public String label() {
        return label;
    }

    public String roleName() {
        return roleName;
    }
}
