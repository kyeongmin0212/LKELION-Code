package com.likelion.mission10.domain;

/**
 * 멤버의 파트(역할) 식별자.
 *
 *  ▶ {@link Member} 에서 {@code @Enumerated(EnumType.STRING)} 으로 매핑되어
 *    DB 에 "BACKEND"/"FRONTEND"/"DESIGN" 문자열로 저장된다.
 */
public enum Part {
    BACKEND("백엔드 개발자"),
    FRONTEND("프론트엔드 개발자"),
    DESIGN("디자이너");

    private final String description;

    Part(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
