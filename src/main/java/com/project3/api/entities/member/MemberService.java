package com.project3.api.entities.member;

import com.project3.api.entities.group.Group;
import com.project3.api.entities.group.GroupRepository;
import com.project3.api.entities.user.User;
import com.project3.api.entities.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, UserRepository userRepository, GroupRepository groupRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public List<Member> getMembers(Long groupId, Long userId) {
        boolean exists;
        if (groupId != null && userId != null) {
            throw new IllegalStateException("This route requires either groupId, userId, or neither, but not both!");
        }
        if (groupId != null) {
            exists = groupRepository.existsById(groupId);
            if (!exists) {
                throw new IllegalStateException("Group with id " + groupId + " does not exist!");
            }
            Group group = groupRepository.getById(groupId);
            return memberRepository.findAllByGroup(group);
        }
        if (userId != null) {
            exists = userRepository.existsById(userId);
            if (!exists) {
                throw new IllegalStateException("User with id " + userId + " does not exist!");
            }
            User user = userRepository.getById(userId);
            return memberRepository.findAllByUser(user);
        }
        return memberRepository.findAll();
    }

    public Member getMemberByGidAndUserId(Long groupId, Long userId) {
        validateGroupAndUser(groupId, userId);
        Group group = groupRepository.getById(groupId);
        User user = userRepository.getById(userId);
        Optional<Member> memberByGroupAndUser = memberRepository.findMemberByGroupAndUser(group, user);
        if (!memberByGroupAndUser.isPresent()) {
            throw new IllegalStateException("Member with id " + userId + " is not part of group with id " + groupId + "!");
        }
        return memberByGroupAndUser.get();
    }

    public void addNewMember(Long groupId, Long userId, Member member) {
        validateGroupAndUser(groupId, userId);
        Group group = groupRepository.getById(groupId);
        User user = userRepository.getById(userId);
        member.setGroup(group);
        member.setUser(user);
        Optional<Member> memberByGroupAndUser =
                memberRepository.findMemberByGroupAndUser(member.getGroup(), member.getUser());
        if (memberByGroupAndUser.isPresent()) {
            throw new IllegalStateException("User with id " + member.getUser().getId() + " is already a member of group with id " + member.getGroup().getGid() +"!");
        }
        member.setJoinedAt(Timestamp.valueOf(LocalDateTime.now()));
        memberRepository.save(member);
    }

    public void deleteMember(Long groupId, Long userId) {
        validateGroupAndUser(groupId, userId);
        Group group = groupRepository.getById(groupId);
        User user = userRepository.getById(userId);
        Optional<Member> memberByGroupAndUser =
                memberRepository.findMemberByGroupAndUser(group, user);
        if (!memberByGroupAndUser.isPresent()) {
            throw new IllegalStateException("User with id " + userId + " is not a member of group with id " + groupId +"!");
        }
        Member member = memberByGroupAndUser.get();
        memberRepository.delete(member);
    }

    @Transactional
    public void updateMember(Long groupId, Long userId, Boolean adminRights) {
        validateGroupAndUser(groupId, userId);
        Group group = groupRepository.getById(groupId);
        User user = userRepository.getById(userId);
        Optional<Member> memberByGroupAndUser =
                memberRepository.findMemberByGroupAndUser(group, user);
        if (!memberByGroupAndUser.isPresent()) {
            throw new IllegalStateException("User with id " + userId + " is not a member of group with id " + groupId +"!");
        }
        Member member = memberByGroupAndUser.get();
        if (adminRights != null && !Objects.equals(member.getAdminRights(), adminRights)) {
            member.setAdminRights(adminRights);
        }
    }

    private void validateGroupAndUser(Long groupId, Long userId) {
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist!");
        }
        exists = groupRepository.existsById(groupId);
        if (!exists) {
            throw new IllegalStateException("Group with id " + groupId + " does not exist!");
        }
    }
}
