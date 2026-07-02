package com.likelion.mission09.dto;

import com.likelion.mission09.domain.Member;
import com.likelion.mission09.domain.Part;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 멤버 생성 요청 DTO ({@code POST /members} 의 요청 바디).
 *
 *  ▶ {@code teamId} 는 선택값 — 지정하면 생성과 동시에 해당 팀에 배정한다(연관관계 설정).
 *    팀 배정은 서비스 계층에서 {@code Team.addMember} 로 처리한다(양방향 동기화).
 */
public class MemberCreateRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Min(value = 1, message = "기수는 1 이상이어야 합니다.")
    private int generation;

    @NotNull(message = "파트는 필수입니다. (BACKEND / FRONTEND / DESIGN)")
    private Part part;

    /** 선택값 — 지정 시 해당 팀에 배정. null 이면 팀 없이 생성. */
    private Long teamId;

    public MemberCreateRequest() {
    }

    public MemberCreateRequest(String name, String email, int generation, Part part, Long teamId) {
        this.name       = name;
        this.email      = email;
        this.generation = generation;
        this.part       = part;
        this.teamId     = teamId;
    }

    /** 검증을 통과한 요청을 새 도메인 Entity 로 변환한다(팀 연결은 서비스에서 수행). */
    public Member toEntity() {
        return new Member(name, email, generation, part);
    }

    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public int    getGeneration() { return generation; }
    public Part   getPart()       { return part; }
    public Long   getTeamId()     { return teamId; }

    public void setName(String name)          { this.name = name; }
    public void setEmail(String email)        { this.email = email; }
    public void setGeneration(int generation) { this.generation = generation; }
    public void setPart(Part part)            { this.part = part; }
    public void setTeamId(Long teamId)        { this.teamId = teamId; }
}
