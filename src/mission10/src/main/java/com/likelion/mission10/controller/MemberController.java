package com.likelion.mission10.controller;

import com.likelion.mission10.domain.Member;
import com.likelion.mission10.domain.Part;
import com.likelion.mission10.dto.MemberCreateRequest;
import com.likelion.mission10.dto.MemberResponse;
import com.likelion.mission10.dto.MemberUpdateRequest;
import com.likelion.mission10.service.MemberService;
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
 * 멤버 REST 컨트롤러 — {@code /members} 자원의 CRUD + 검색.
 *
 *  ▶ 컨트롤러는 정상 흐름만 다룬다. 없는 멤버 조회/검증 실패 등의 예외 처리는 전혀 하지 않고,
 *    서비스가 던진 예외를 {@code GlobalExceptionHandler} 가 통일된 에러 응답으로 변환한다.
 *  ▶ 프론트엔드({@code static/index.html})가 이 엔드포인트들을 fetch() 로 호출한다.
 */
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /** 생성 — 201 Created + Location. */
    @PostMapping
    public ResponseEntity<MemberResponse> create(@Valid @RequestBody MemberCreateRequest request,
                                                 UriComponentsBuilder uriBuilder) {
        Member saved = memberService.create(request.toEntity());
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

    /**
     * 검색 — 200 OK. 이름 또는 이메일에 키워드가 포함된 멤버를 조회한다.
     * 예) {@code GET /members/search?keyword=백엔}
     *
     *  ▶ 이 미션에서 추가된 검색 기능 API. 프론트엔드의 검색창이 이 엔드포인트를 호출한다.
     */
    @GetMapping("/search")
    public ResponseEntity<List<MemberResponse>> search(@RequestParam(required = false, defaultValue = "") String keyword) {
        List<MemberResponse> body = memberService.search(keyword).stream()
                .map(MemberResponse::from)
                .toList();
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
}
