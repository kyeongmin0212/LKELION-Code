package com.likelion.mission07.dto;

import com.likelion.mission07.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * 멤버 생성 요청 DTO ({@code POST /members} 의 요청 바디).
 *
 *  ▶ Entity 와 분리하는 이유:
 *      - {@code id} 가 없다 — 식별자는 서버(Repository)가 채번하므로 클라이언트가 보내지 않는다.
 *      - Bean Validation 어노테이션({@code @NotBlank} 등)으로 "입력 검증" 책임을 DTO 가 진다.
 *        도메인 불변식과 별개로, HTTP 입력 단계에서 잘못된 요청을 400 으로 먼저 걸러낸다.
 *
 *  ▶ {@link #toEntity()} 로 검증을 통과한 입력을 도메인 객체로 변환한다(매핑 책임도 DTO 쪽에 둔다).
 */
public class MemberCreateRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Min(value = 1, message = "기수는 1 이상이어야 합니다.")
    private int generation;

    /** Jackson 역직렬화용 기본 생성자. */
    public MemberCreateRequest() {
    }

    public MemberCreateRequest(String name, String email, int generation) {
        this.name       = name;
        this.email      = email;
        this.generation = generation;
    }

    /** 검증을 통과한 요청을 새 도메인 Entity 로 변환한다(아직 id 없음). */
    public Member toEntity() {
        return new Member(name, email, generation);
    }

    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public int    getGeneration() { return generation; }

    public void setName(String name)             { this.name = name; }
    public void setEmail(String email)           { this.email = email; }
    public void setGeneration(int generation)    { this.generation = generation; }
}
