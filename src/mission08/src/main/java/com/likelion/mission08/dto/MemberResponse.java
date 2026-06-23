package com.likelion.mission08.dto;

import com.likelion.mission08.domain.Member;
import com.likelion.mission08.domain.Part;

/**
 * 멤버 응답 DTO (모든 조회/생성/수정 응답의 바디).
 *
 *  ▶ Entity(@Entity Member)를 직접 반환하지 않는다 — JPA 엔티티를 그대로 직렬화하면
 *    지연 로딩 프록시/내부 매핑이 응답에 새어 나갈 수 있다. 응답 계약은 DTO 로 고정한다.
 */
public class MemberResponse {

    private final Long   id;
    private final String name;
    private final String email;
    private final int    generation;
    private final Part   part;
    private final String partDescription; // enum 의 한글 설명까지 표현 계층에서 함께 노출

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
