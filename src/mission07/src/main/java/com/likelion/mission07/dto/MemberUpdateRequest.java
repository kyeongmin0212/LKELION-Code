package com.likelion.mission07.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * 멤버 수정 요청 DTO ({@code PUT /members/{id}} 의 요청 바디).
 *
 *  ▶ 생성 DTO 와 마찬가지로 {@code id} 를 바디에 두지 않는다 — 수정 대상 식별자는 URL 경로변수({id})에서 온다.
 *    "자원을 가리키는 식별자는 URL, 바뀔 내용은 바디" 라는 REST 규칙을 코드 구조로 강제한다.
 *
 *  ▶ PUT 은 전체 교체(replace) 시맨틱이므로 모든 필드를 필수로 검증한다
 *    (일부만 바꾸는 부분 수정은 PATCH 의 몫 — 이 미션 범위 밖이다).
 */
public class MemberUpdateRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Min(value = 1, message = "기수는 1 이상이어야 합니다.")
    private int generation;

    /** Jackson 역직렬화용 기본 생성자. */
    public MemberUpdateRequest() {
    }

    public MemberUpdateRequest(String name, String email, int generation) {
        this.name       = name;
        this.email      = email;
        this.generation = generation;
    }

    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public int    getGeneration() { return generation; }

    public void setName(String name)          { this.name = name; }
    public void setEmail(String email)        { this.email = email; }
    public void setGeneration(int generation) { this.generation = generation; }
}
