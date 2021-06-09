package com.project3.api.entities.member;

import com.project3.api.entities.group.Group;
import com.project3.api.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository
        extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByGroupAndUser (Group group, User user);
    List<Member> findAllByGroup(Group group);
    List<Member> findAllByUser (User user);

    @Query("SELECT m.group FROM members_info m WHERE m.user = ?1")
    List<Group> findAllGroupsByUser(User user);
}
