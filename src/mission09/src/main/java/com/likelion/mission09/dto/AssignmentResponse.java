package com.likelion.mission09.dto;

import com.likelion.mission09.domain.Assignment;
import com.likelion.mission09.domain.Member;

/**
 * 과제 응답 DTO — 과제 정보 + 담당 멤버를 id/이름으로 평탄화하여 노출한다(순환 방지).
 */
public class AssignmentResponse {

    private final Long   id;
    private final String title;
    private final String description;
    private final Long   memberId;
    private final String memberName;

    private AssignmentResponse(Long id, String title, String description, Long memberId, String memberName) {
        this.id          = id;
        this.title       = title;
        this.description = description;
        this.memberId    = memberId;
        this.memberName  = memberName;
    }

    public static AssignmentResponse from(Assignment assignment) {
        Member member = assignment.getMember();
        return new AssignmentResponse(
                assignment.getId(),
                assignment.getTitle(),
                assignment.getDescription(),
                member == null ? null : member.getId(),
                member == null ? null : member.getName());
    }

    public Long   getId()          { return id; }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public Long   getMemberId()    { return memberId; }
    public String getMemberName()  { return memberName; }
}
