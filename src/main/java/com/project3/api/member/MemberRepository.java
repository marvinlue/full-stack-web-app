package com.project3.api.member;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MemberRepository
        extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByGroupIdAndUserId (Long groupId, Long userId);
    List<Member> findAllByGroupId (Long groupId);
    List<Member> findAllByUserId (Long userId);
}
