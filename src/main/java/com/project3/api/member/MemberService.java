package com.project3.api.member;

import com.project3.api.group.Group;
import com.project3.api.group.GroupRepository;
import com.project3.api.user.UserRepository;
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
            return memberRepository.findAllByGroupId(groupId);
        }
        if (userId != null) {
            exists = userRepository.existsById(userId);
            if (!exists) {
                throw new IllegalStateException("User with id " + userId + " does not exist!");
            }
            return memberRepository.findAllByUserId(userId);
        }
        return memberRepository.findAll();
    }

    public Member getMemberByGidAndUserId(Long groupId, Long userId) {
        validateGroupAndUser(groupId, userId);
        Optional<Member> memberByGidAndId = memberRepository.findMemberByGroupIdAndUserId(groupId, userId);
        if (!memberByGidAndId.isPresent()) {
            throw new IllegalStateException("Member with id " + userId + " is not part of group with id " + groupId + "!");
        }
        return memberByGidAndId.get();
    }

    public void addNewMember(Member member) {
        validateGroupAndUser(member.getGroupId(), member.getUserId());
        Optional<Member> memberByGroupIdAndUserId =
                memberRepository.findMemberByGroupIdAndUserId(member.getGroupId(), member.getUserId());
        if (memberByGroupIdAndUserId.isPresent()) {
            throw new IllegalStateException("User with id " + member.getUserId() + " is already a member of group with id " + member.getGroupId() +"!");
        }
        member.setJoinedAt(Timestamp.valueOf(LocalDateTime.now()));
        memberRepository.save(member);
    }

    public void deleteMember(Long groupId, Long userId) {
        validateGroupAndUser(groupId, userId);
        Optional<Member> memberByGroupIdAndUserId =
                memberRepository.findMemberByGroupIdAndUserId(groupId, userId);
        if (!memberByGroupIdAndUserId.isPresent()) {
            throw new IllegalStateException("User with id " + userId + " is not a member of group with id " + groupId +"!");
        }
        Optional<Group> groupByGidAndGroupAdmin = groupRepository.findGroupByGidAndGroupAdmin(groupId, userId);
        if (groupByGidAndGroupAdmin.isPresent()) {
            throw new IllegalStateException("User with id " + userId + " is group admin of group with id " + groupId + ". Please select another group admin first.");
        }
        Member member = memberByGroupIdAndUserId.get();
        memberRepository.delete(member);
    }

    @Transactional
    public void updateMember(Long groupId, Long userId, Boolean adminRights) {
        validateGroupAndUser(groupId, userId);
        Optional<Member> memberByGroupIdAndUserId =
                memberRepository.findMemberByGroupIdAndUserId(groupId, userId);
        if (!memberByGroupIdAndUserId.isPresent()) {
            throw new IllegalStateException("User with id " + userId + " is not a member of group with id " + groupId +"!");
        }
        Member member = memberByGroupIdAndUserId.get();
        if (adminRights != null && !Objects.equals(member.getAdminRights(), adminRights)) {
            Optional<Group> groupByGidAndGroupAdmin = groupRepository.findGroupByGidAndGroupAdmin(groupId, userId);
            if (groupByGidAndGroupAdmin.isPresent()) {
                throw new IllegalStateException("Cannot update adminRights as user with id " + userId + " is group admin of group with id " + groupId + "!");
            }
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
