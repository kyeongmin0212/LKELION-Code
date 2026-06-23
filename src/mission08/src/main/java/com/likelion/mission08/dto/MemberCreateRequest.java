package com.likelion.mission08.dto;

import com.likelion.mission08.domain.Member;
import com.likelion.mission08.domain.Part;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 멤버 생성 요청 DTO ({@code POST /members} 의 요청 바디).
 *
 *  ▶ Entity 와 분리하는 이유(Mission07 과 동일):
 *      - {@code id} 가 없다 — 식별자는 DB 가 채번하므로 클라이언트가 보내지 않는다.
 *      - Bean Validation 으로 HTTP 입력 단계에서 잘못된 요청을 400 으로 먼저 걸러낸다.
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

    /** Jackson 역직렬화용 기본 생성자. */
    public MemberCreateRequest() {
    }

    public MemberCreateRequest(String name, String email, int generation, Part part) {
        this.name       = name;
        this.email      = email;
        this.generation = generation;
        this.part       = part;
    }

    /** 검증을 통과한 요청을 새 도메인 Entity 로 변환한다(아직 id 없음 — DB 가 채번). */
    public Member toEntity() {
        return new Member(name, email, generation, part);
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
