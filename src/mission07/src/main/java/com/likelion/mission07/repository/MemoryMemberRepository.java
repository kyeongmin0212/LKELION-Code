package com.likelion.mission07.repository;

import com.likelion.mission07.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ConcurrentHashMap 기반 인메모리 저장소 구현체. (DB 도입 없이도 CRUD 의 본질을 그대로 보여준다.)
 *
 *  ▶ Spring 빈 등록: {@code @Repository} 로 자동 등록된다(빈 이름 "memoryMemberRepository").
 *  ▶ 식별자 채번: {@link AtomicLong} 로 1 부터 증가시키며 동시성 안전하게 부여한다 — DB 의 auto-increment 역할.
 *  ▶ 동시성: 멀티스레드(웹 요청)에서 안전하도록 {@code ConcurrentHashMap} + {@code AtomicLong} 을 쓴다.
 */
@Repository
public class MemoryMemberRepository implements MemberRepository {

    private final Map<Long, Member> store    = new ConcurrentHashMap<>();
    private final AtomicLong         sequence = new AtomicLong(0);

    @Override
    public Member save(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("저장 대상이 null 입니다.");
        }
        Long id = sequence.incrementAndGet();
        member.assignId(id);
        store.put(id, member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(store.values()));
    }

    @Override
    public boolean deleteById(Long id) {
        return store.remove(id) != null;
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    @Override
    public long count() {
        return store.size();
    }
}
