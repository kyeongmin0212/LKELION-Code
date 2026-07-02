package com.likelion.mission09.dto;

import com.likelion.mission09.domain.Team;
import jakarta.validation.constraints.NotBlank;

/**
 * 팀 생성 요청 DTO ({@code POST /teams} 의 요청 바디).
 */
public class TeamCreateRequest {

    @NotBlank(message = "팀 이름은 필수입니다.")
    private String name;

    public TeamCreateRequest() {
    }

    public TeamCreateRequest(String name) {
        this.name = name;
    }

    public Team toEntity() {
        return new Team(name);
    }

    public String getName()          { return name; }
    public void   setName(String name) { this.name = name; }
}
