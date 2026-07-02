package com.likelion.mission09.controller;

import com.likelion.mission09.domain.Assignment;
import com.likelion.mission09.dto.AssignmentCreateRequest;
import com.likelion.mission09.dto.AssignmentResponse;
import com.likelion.mission09.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * 과제 REST 컨트롤러 — {@code /assignments} 자원.
 *
 *  ▶ 생성 시 {@code memberId} 로 담당 멤버를 지정한다(Member 1 : N Assignment).
 */
@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    /** 생성 — 201 Created + Location. memberId 필수. */
    @PostMapping
    public ResponseEntity<AssignmentResponse> create(@Valid @RequestBody AssignmentCreateRequest request,
                                                     UriComponentsBuilder uriBuilder) {
        Assignment saved = assignmentService.create(request.toEntity(), request.getMemberId());
        URI location = uriBuilder.path("/assignments/{id}").buildAndExpand(saved.getId()).toUri();
        return ResponseEntity.created(location).body(AssignmentResponse.from(saved));
    }

    /** 전체 조회 — 200 OK. */
    @GetMapping
    public ResponseEntity<List<AssignmentResponse>> findAll() {
        List<AssignmentResponse> body = assignmentService.findAll().stream()
                .map(AssignmentResponse::from)
                .toList();
        return ResponseEntity.ok(body);
    }

    /** 단건 조회 — 200 OK / 없으면 404. */
    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(AssignmentResponse.from(assignmentService.findById(id)));
    }

    /** 삭제 — 204 No Content / 없으면 404. */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        assignmentService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
