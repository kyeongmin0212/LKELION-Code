package com.likelion.mission09.controller;

import com.likelion.mission09.domain.Member;
import com.likelion.mission09.domain.Team;
import com.likelion.mission09.dto.MemberResponse;
import com.likelion.mission09.dto.TeamCreateRequest;
import com.likelion.mission09.dto.TeamResponse;
import com.likelion.mission09.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * 팀 REST 컨트롤러 — {@code /teams} 자원.
 *
 *  ▶ 이 미션의 대표 API: <b>팀별 멤버 조회</b> {@code GET /teams/{id}/members}
 *    (연관관계 기반 조회 — 체크리스트 "팀별 멤버 조회 API 가 있는가").
 */
@RestController
@RequestMapping("/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /** 팀 생성 — 201 Created + Location. */
    @PostMapping
    public ResponseEntity<TeamResponse> create(@Valid @RequestBody TeamCreateRequest request,
                                               UriComponentsBuilder uriBuilder) {
        Team saved = teamService.create(request.toEntity());
        URI location = uriBuilder.path("/teams/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(TeamResponse.from(saved));
    }

    /** 팀 전체 조회 — 200 OK. */
    @GetMapping
    public ResponseEntity<List<TeamResponse>> findAll() {
        List<TeamResponse> body = teamService.findAll().stream()
                .map(TeamResponse::from)
                .toList();
        return ResponseEntity.ok(body);
    }

    /** 팀 단건 조회 — 200 OK / 없으면 404. */
    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(TeamResponse.from(teamService.findById(id)));
    }

    /**
     * 팀별 멤버 조회 — 200 OK / 팀 없으면 404.
     * Team(1) : Member(N) 연관관계를 이용해 해당 팀의 멤버 목록을 반환한다.
     */
    @GetMapping("/{id}/members")
    public ResponseEntity<List<MemberResponse>> findMembers(@PathVariable Long id) {
        List<MemberResponse> body = teamService.findMembers(id).stream()
                .map(MemberResponse::from)
                .toList();
        return ResponseEntity.ok(body);
    }
}
