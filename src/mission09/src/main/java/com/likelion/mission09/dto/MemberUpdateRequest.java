package com.likelion.mission09.dto;

import com.likelion.mission09.domain.Part;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 멤버 수정 요청 DTO ({@code PUT /members/{id}} 의 요청 바디).
 * PUT 은 전체 교체 시맨틱이므로 기본 필드를 모두 필수로 검증한다.
 */
public class MemberUpdateRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Min(value = 1, message = "기수는 1 이상이어야 합니다.")
    private int generation;

    @NotNull(message = "파트는 필수입니다. (BACKEND / FRONTEND / DESIGN)")
    private Part part;

    public MemberUpdateRequest() {
    }

    public MemberUpdateRequest(String name, String email, int generation, Part part) {
        this.name       = name;
        this.email      = email;
        this.generation = generation;
        this.part       = part;
    }

    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public int    getGeneration() { return generation; }
    public Part   getPart()       { return part; }

    public void setName(String name)          { this.name = name; }
    public void setEmail(String email)        { this.email = email; }
    public void setGeneration(int generation) { this.generation = generation; }
    public void setPart(Part part)            { this.part = part; }
}
