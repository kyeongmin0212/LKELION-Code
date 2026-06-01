package com.likelion.mission07.dto;

import com.likelion.mission07.domain.Member;

/**
 * 멤버 응답 DTO (모든 조회/생성/수정 응답의 바디).
 *
 *  ▶ Entity 를 직접 반환하지 않는 이유:
 *      - 응답 표현(JSON 필드 구성)을 API 계약으로 고정한다 — 도메인에 내부용 필드가 생겨도 응답은 안 바뀐다.
 *      - 직렬화 형태를 컨트롤러 계층이 통제한다(필드 노출 범위를 명시적으로 선택).
 *
 *  ▶ 모든 필드는 불변(final). 정적 팩토리 {@link #from(Member)} 로 Entity → 응답 변환을 한 곳에 모은다.
 */
public class MemberResponse {

    private final Long   id;
    private final String name;
    private final String email;
    private final int    generation;

    private MemberResponse(Long id, String name, String email, int generation) {
        this.id         = id;
        this.name       = name;
        this.email      = email;
        this.generation = generation;
    }

    /** 도메인 Entity 를 응답 표현으로 변환한다. */
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getGeneration());
    }

    public Long   getId()         { return id; }
    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public int    getGeneration() { return generation; }
}
