package com.likelion.mission07.controller;

import com.likelion.mission07.domain.Member;
import com.likelion.mission07.dto.MemberCreateRequest;
import com.likelion.mission07.dto.MemberResponse;
import com.likelion.mission07.dto.MemberUpdateRequest;
import com.likelion.mission07.service.MemberService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * 멤버 REST 컨트롤러 — {@code /members} 자원의 CRUD 를 RESTful 하게 노출한다.
 *
 *  ▶ RESTful URL 설계 규칙(이 미션의 채점 포인트):
 *      - 자원은 복수형 명사 컬렉션 {@code /members} 로 표현한다(동사를 URL 에 넣지 않는다).
 *      - 컬렉션 = {@code /members}, 개별 자원 = {@code /members/{id}} 로 계층을 나눈다.
 *      - "무엇을 할지" 는 URL 이 아니라 HTTP 메서드(POST/GET/PUT/DELETE)로 표현한다.
 *
 *  ▶ ResponseEntity 로 상태 코드를 명시적으로 통제한다:
 *      - POST   → 201 Created  (+ Location 헤더에 새 자원 URI)
 *      - GET    → 200 OK
 *      - PUT    → 200 OK
 *      - DELETE → 204 No Content (본문 없음)
 *      - 없는 id → 404 (GlobalExceptionHandler), 잘못된 입력 → 400 (@Valid + 핸들러)
 *
 *  ▶ DTO ↔ Entity 경계: 컨트롤러는 요청 DTO 를 도메인으로 변환해 서비스에 넘기고,
 *    서비스가 돌려준 도메인 Entity 를 응답 DTO 로 감싸 반환한다. Entity 는 외부로 새지 않는다.
 */
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /** 생성 — 201 Created + Location: /members/{id}. */
    @PostMapping
    public ResponseEntity<MemberResponse> create(@Valid @RequestBody MemberCreateRequest request,
                                                 UriComponentsBuilder uriBuilder) {
        Member saved = memberService.create(request.toEntity());

        URI location = uriBuilder.path("/members/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(MemberResponse.from(saved));
    }

    /** 전체 조회 — 200 OK. 비어 있어도 빈 배열 + 200 (404 아님). */
    @GetMapping
    public ResponseEntity<List<MemberResponse>> findAll() {
        List<MemberResponse> body = memberService.findAll().stream()
                .map(MemberResponse::from)
                .toList();
        return ResponseEntity.ok(body);
    }

    /** 단건 조회 — 200 OK / 없으면 404. */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> findOne(@PathVariable Long id) {
        Member member = memberService.findById(id);
        return ResponseEntity.ok(MemberResponse.from(member));
    }

    /** 수정(전체 교체) — 200 OK / 없으면 404. */
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody MemberUpdateRequest request) {
        Member updated = memberService.update(
                id, request.getName(), request.getEmail(), request.getGeneration());
        return ResponseEntity.ok(MemberResponse.from(updated));
    }

    /** 삭제 — 204 No Content / 없으면 404. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
