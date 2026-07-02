package com.likelion.mission09.dto;

import com.likelion.mission09.domain.Member;
import com.likelion.mission09.domain.Part;
import com.likelion.mission09.domain.Team;

/**
 * 멤버 응답 DTO (모든 조회/생성/수정 응답의 바디).
 *
 *  ▶ Entity 를 직접 직렬화하지 않는다 — 지연 로딩 프록시/양방향 연관관계가 그대로 노출되면
 *    무한 순환(Team ↔ Member)이나 의도치 않은 쿼리가 발생한다. 응답 계약은 DTO 로 고정한다.
 *  ▶ 소속 팀은 순환을 피해 {@code teamId}/{@code teamName} 만 평탄화하여 노출한다.
 */
public class MemberResponse {

    private final Long   id;
    private final String name;
    private final String email;
    private final int    generation;
    private final Part   part;
    private final String partDescription;
    private final Long   teamId;
    private final String teamName;

    private MemberResponse(Long id, String name, String email, int generation,
                           Part part, Long teamId, String teamName) {
        this.id              = id;
        this.name            = name;
        this.email           = email;
        this.generation      = generation;
        this.part            = part;
        this.partDescription = part == null ? null : part.getDescription();
        this.teamId          = teamId;
        this.teamName        = teamName;
    }

    /** 도메인 Entity 를 응답 표현으로 변환한다. team 은 id/name 만 평탄화한다. */
    public static MemberResponse from(Member member) {
        Team team = member.getTeam();
        return new MemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getGeneration(),
                member.getPart(),
                team == null ? null : team.getId(),
                team == null ? null : team.getName());
    }

    public Long   getId()              { return id; }
    public String getName()            { return name; }
    public String getEmail()           { return email; }
    public int    getGeneration()      { return generation; }
    public Part   getPart()            { return part; }
    public String getPartDescription() { return partDescription; }
    public Long   getTeamId()          { return teamId; }
    public String getTeamName()        { return teamName; }
}
