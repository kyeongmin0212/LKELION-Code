package com.likelion.mission08.domain;

/**
 * 멤버의 파트(역할) 식별자 — Mission03~06 에서 써 온 도메인 개념을 그대로 가져온다.
 *
 *  ▶ 이 enum 은 {@link Member} 에서 {@code @Enumerated(EnumType.STRING)} 으로 매핑된다.
 *    DB 에는 0/1/2 같은 순서값(ORDINAL)이 아니라 "BACKEND"/"FRONTEND"/"DESIGN" 문자열로 저장된다 →
 *    enum 상수의 선언 순서가 바뀌어도 기존 데이터가 깨지지 않는다(이 미션의 체크포인트).
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
