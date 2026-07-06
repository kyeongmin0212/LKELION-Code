package com.likelion.mission10.dto;

import com.likelion.mission10.domain.Member;
import com.likelion.mission10.domain.Part;

/**
 * 멤버 응답 DTO (모든 조회/생성/수정/검색 응답의 바디).
 *
 *  ▶ Entity 를 직접 직렬화하지 않고 DTO 로 응답 계약을 고정한다.
 */
public class MemberResponse {

    private final Long   id;
    private final String name;
    private final String email;
    private final int    generation;
    private final Part   part;
    private final String partDescription; // enum 의 한글 설명까지 함께 노출

    private MemberResponse(Long id, String name, String email, int generation, Part part) {
        this.id              = id;
        this.name            = name;
        this.email           = email;
        this.generation      = generation;
        this.part            = part;
        this.partDescription = part == null ? null : part.getDescription();
    }

    /** 도메인 Entity 를 응답 표현으로 변환한다. */
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getGeneration(),
                member.getPart());
    }

    public Long   getId()              { return id; }
    public String getName()            { return name; }
    public String getEmail()           { return email; }
    public int    getGeneration()      { return generation; }
    public Part   getPart()            { return part; }
    public String getPartDescription() { return partDescription; }
}
