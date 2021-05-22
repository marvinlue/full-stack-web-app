package com.example.project3.member;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository
        extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByGroupIdAndUserId (Long groupId, Long userId);
}
