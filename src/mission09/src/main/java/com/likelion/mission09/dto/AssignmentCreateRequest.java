package com.likelion.mission09.dto;

import com.likelion.mission09.domain.Assignment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 과제 생성 요청 DTO ({@code POST /assignments} 의 요청 바디).
 *
 *  ▶ {@code memberId} 는 필수 — 과제는 반드시 한 명의 멤버에 속한다(Member 1 : N Assignment).
 *    멤버 연결은 서비스 계층에서 {@code Member.addAssignment} 로 처리한다(양방향 동기화).
 */
public class AssignmentCreateRequest {

    @NotBlank(message = "과제 제목은 필수입니다.")
    private String title;

    private String description;

    @NotNull(message = "담당 멤버 id(memberId)는 필수입니다.")
    private Long memberId;

    public AssignmentCreateRequest() {
    }

    public AssignmentCreateRequest(String title, String description, Long memberId) {
        this.title       = title;
        this.description = description;
        this.memberId    = memberId;
    }

    public Assignment toEntity() {
        return new Assignment(title, description);
    }

    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public Long   getMemberId()    { return memberId; }

    public void setTitle(String title)             { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setMemberId(Long memberId)         { this.memberId = memberId; }
}
