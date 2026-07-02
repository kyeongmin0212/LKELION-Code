package com.likelion.mission09.dto;

import com.likelion.mission09.domain.Team;

/**
 * 팀 응답 DTO — 팀 기본 정보 + 소속 멤버 수만 노출한다.
 *
 *  ▶ 멤버 목록 자체는 순환/과다 조회를 피해 여기 담지 않는다.
 *    팀별 멤버 상세는 별도 엔드포인트({@code GET /teams/{id}/members})로 조회한다.
 */
public class TeamResponse {

    private final Long   id;
    private final String name;
    private final int    memberCount;

    private TeamResponse(Long id, String name, int memberCount) {
        this.id          = id;
        this.name        = name;
        this.memberCount = memberCount;
    }

    public static TeamResponse from(Team team) {
        return new TeamResponse(team.getId(), team.getName(), team.getMembers().size());
    }

    public Long   getId()          { return id; }
    public String getName()        { return name; }
    public int    getMemberCount() { return memberCount; }
}
