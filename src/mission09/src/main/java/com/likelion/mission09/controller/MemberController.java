package com.likelion.mission09.controller;

import com.likelion.mission09.domain.Member;
import com.likelion.mission09.domain.Part;
import com.likelion.mission09.dto.AssignmentResponse;
import com.likelion.mission09.dto.MemberCreateRequest;
import com.likelion.mission09.dto.MemberResponse;
import com.likelion.mission09.dto.MemberUpdateRequest;
import com.likelion.mission09.service.AssignmentService;
import com.likelion.mission09.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * 멤버 REST 컨트롤러 — {@code /members} 자원의 CRUD + 연관관계 조회.
 *
 *  ▶ Mission08 의 CRUD 를 유지하되, 생성 시 {@code teamId} 로 팀 배정이 가능하고,
 *    {@code GET /members/{id}/assignments} 로 멤버별 과제(1:N)를 조회한다.
 */
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService     memberService;
    private final AssignmentService assignmentService;

    public MemberController(MemberService memberService, AssignmentService assignmentService) {
        this.memberService     = memberService;
        this.assignmentService = assignmentService;
    }

    /** 생성 — 201 Created + Location. teamId 지정 시 해당 팀에 배정. */
    @PostMapping
    public ResponseEntity<MemberResponse> create(@Valid @RequestBody MemberCreateRequest request,
                                                 UriComponentsBuilder uriBuilder) {
        Member saved = memberService.create(request.toEntity(), request.getTeamId());
        URI location = uriBuilder.path("/members/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(MemberResponse.from(saved));
    }

    /** 전체 조회 — 200 OK. {@code ?part=BACKEND} 로 파트별 조회. */
    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll(@RequestParam(required = false) Part part) {
        List<Member> members = (part == null)
                ? memberService.findAll()
                : memberService.findByPart(part);
        List<MemberResponse> body = members.stream().map(MemberResponse::from).toList();
        return ResponseEntity.ok(body);
    }

    /** 단건 조회 — 200 OK / 없으면 404. */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(MemberResponse.from(memberService.findById(id)));
    }

    /** 수정(전체 교체) — 200 OK / 없으면 404. */
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody MemberUpdateRequest request) {
        Member updated = memberService.update(
                id, request.getName(), request.getEmail(), request.getGeneration(), request.getPart());
        return ResponseEntity.ok(MemberResponse.from(updated));
    }

    /** 삭제 — 204 No Content / 없으면 404. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 멤버별 과제 조회 — 200 OK / 멤버 없으면 404.
     * Member(1) : Assignment(N) 연관관계 기반 조회.
     */
    @GetMapping("/{id}/assignments")
    public ResponseEntity<List<AssignmentResponse>> findAssignments(@PathVariable Long id) {
        List<AssignmentResponse> body = assignmentService.findByMember(id).stream()
                .map(AssignmentResponse::from)
                .toList();
        return ResponseEntity.ok(body);
    }
}
