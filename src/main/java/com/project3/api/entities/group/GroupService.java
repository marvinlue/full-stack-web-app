package com.project3.api.entities.group;

import com.project3.api.entities.user.User;
import com.project3.api.entities.user.UserRepository;
import com.project3.api.entities.member.MemberRepository;
import com.project3.api.entities.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public GroupService (GroupRepository groupRepository, UserRepository userRepository, MemberRepository memberRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.memberRepository = memberRepository;
    }

    public List<Group> getGroups() {
        return groupRepository.findAll();
    }

    public Group getGroupByGidOrGroupName(Long groupId, String groupName) {
        if (groupId == null && groupName == null) {
            throw new IllegalStateException("groupId or groupName required for this route!");
        }
        if (groupId != null && groupName != null) {
            Optional<Group> groupByGidAndGroupName = groupRepository.findGroupByGidAndGroupName(groupId, groupName);
            if (groupByGidAndGroupName.isEmpty()) {
                throw new IllegalStateException("Group with id " + groupId + " and name " + groupName + " not found!");
            }
        }
        if (groupId != null) {
            Optional<Group> groupByGid = groupRepository.findGroupByGid(groupId);
            if (groupByGid.isEmpty()) {
                throw new IllegalStateException("Group with id " + groupId + " does not exist!");
            }
            return groupByGid.get();
        }
        Optional<Group> groupByGroupName = groupRepository.findGroupByGroupName(groupName);
        if (groupByGroupName.isEmpty()) {
            throw new IllegalStateException("Group with name " + groupName + " does not exist!");
        }
        return groupByGroupName.get();
    }

    public void addNewGroup(Group group, Long userId) {
        Optional<Group> groupByGroupName = groupRepository.findGroupByGroupName(group.getGroupName());
        if (groupByGroupName.isPresent()) {
            throw new IllegalStateException("Group name already taken!");
        }
        boolean exists = userRepository.existsById(userId);
        if (!exists) {
            throw new IllegalStateException("User with id " + userId + " does not exist!");
        }
        group.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        groupRepository.save(group);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist!"));
        Member member = new Member(
                group,
                user,
                true,
                Timestamp.valueOf(LocalDateTime.now())
        );
        memberRepository.save(member);
    }

    public void deleteGroup(Long userId, Long groupId) {
        Group group = groupRepository.findGroupByGid(groupId)
                .orElseThrow(() -> new IllegalStateException(
                        "Group with id " + groupId + " does not exist!"));
        validateUserInGroup(userId, group);
        groupRepository.deleteById(groupId);
    }

    @Transactional
    public void updateGroup(Long userId, Long groupId, String groupName) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalStateException(
                        "Group with id " + groupId + " does not exist!"));
        if (groupName != null &&
                groupName.length() > 0 &&
                !Objects.equals(group.getGroupName(), groupName)) {
            validateUserInGroup(userId, group);
            Optional<Group> groupByGroup_name = groupRepository.findGroupByGroupName(groupName);
            if (groupByGroup_name.isPresent()) {
                throw new IllegalStateException("Group name already taken!");
            }
            group.setGroupName(groupName);
        }
    }

    private void validateUserInGroup(Long userId, Group group) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " does not exist!"));
        Optional<Member> memberByGroupAndUser = memberRepository.findMemberByGroupAndUser(group, user);
        if (memberByGroupAndUser.isEmpty()) {
            throw new IllegalStateException("User with id " + userId + " is not a member of group with id " + group.getGid() + "!");
        }
        Member member = memberByGroupAndUser.get();
        if (!member.getAdminRights()) {
            throw new IllegalStateException("User with id " + userId + " does not have admin rights in group with id " + group.getGid() + "!");
        }
    }
}
