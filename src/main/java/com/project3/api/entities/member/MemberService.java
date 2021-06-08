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
        User user = getUser(userId);
        Group group = getGroup(groupId);
        Optional<Member> memberByGroupAndUser = memberRepository.findMemberByGroupAndUser(group, user);
        if (memberByGroupAndUser.isEmpty()) {
            throw new IllegalStateException("User with id " + userId + " is not a member of group with id " + groupId + "!");
        }
        return memberByGroupAndUser.get();
    }

    public void addNewMember(Long groupId, Long userId, Member member) {
        User user = getUser(userId);
        Group group = getGroup(groupId);
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

    public void leaveGroup(Long userId, Long groupId) {
        User user = getUser(userId);
        Group group = getGroup(groupId);
        Optional<Member> memberByGroupAndUser = memberRepository.findMemberByGroupAndUser(group, user);
        if (memberByGroupAndUser.isEmpty()) {
            throw new IllegalStateException("User with id " + userId + " is not a member of group with id " + groupId + "!");
        }
        Member member = memberByGroupAndUser.get();
        memberRepository.delete(member);
    }

    public void deleteMember(Long currentUserId, Long userId, Long groupId) {
        Member member = memberRepository.getById(validateUsersInGroup(currentUserId, userId, groupId));
        if (member.getAdminRights()) {
            throw new IllegalStateException("Cannot delete user with id " + userId + " from group with id " + groupId + ". User is a group admin!");
        }
        memberRepository.delete(member);
    }

    @Transactional
    public void updateMember(Long currentUserId, Long userId, Long groupId, Boolean adminRights) {
        Member member = memberRepository.getById(validateUsersInGroup(currentUserId, userId, groupId));
        if (adminRights != null && !Objects.equals(member.getAdminRights(), adminRights)) {
            if (!currentUserId.equals(userId)) {
                if (member.getAdminRights()) {
                    throw new IllegalStateException("Cannot update admin rights of user with id " + userId + " in group with id " + groupId + ". User is a group admin!");
                }
            }
            member.setAdminRights(adminRights);
        }
    }

    private Long validateUsersInGroup(Long currentUserId, Long userId, Long groupId) {
        User current = getUser(currentUserId);
        User user = getUser(userId);
        Group group = getGroup(groupId);
        Optional<Member> memberByGroupAndUser = memberRepository.findMemberByGroupAndUser(group, user);
        if (memberByGroupAndUser.isEmpty()) {
            throw new IllegalStateException("User with id " + user.getId() + " is not a member of group with id " + group.getGid() + "!");
        }
        Optional<Member> adminByGroupAndUser = memberRepository.findMemberByGroupAndUser(group, current);
        if (adminByGroupAndUser.isEmpty()) {
            throw new IllegalStateException("User with id " + current.getId() + " is not a member of group with id " + group.getGid() + "!");
        }
        Member admin = adminByGroupAndUser.get();
        if (!admin.getAdminRights()) {
            throw new IllegalStateException("User with id " + current.getId() + " does not have admin rights in group with id " + group.getGid() + "!");
        }
        Member member = memberByGroupAndUser.get();
        return member.getMemberId();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist!"));
    }

    private Group getGroup(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalStateException(
                        "Group with id " + groupId + " does not exist!"));
    }

}
