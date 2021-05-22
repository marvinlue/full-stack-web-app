package com.example.project3.group;

import com.example.project3.member.Member;
import com.example.project3.member.MemberRepository;
import com.example.project3.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public void addNewGroup(Group group) {
        Optional<Group> groupByGroupName = groupRepository.findGroupByGroupName(group.getGroupName());
        if (groupByGroupName.isPresent()) {
            throw new IllegalStateException("Group name already taken!");
        }
        boolean exists = userRepository.existsById(group.getGroupAdmin());
        if (!exists) {
            throw new IllegalStateException("User with id " + group.getGroupAdmin() + " does not exist!");
        }
        group.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        groupRepository.save(group);
        groupByGroupName = groupRepository.findGroupByGroupName(group.getGroupName());
        group = groupByGroupName.get();
        Member member = new Member(
                group.getGid(),
                group.getGroupAdmin(),
                true,
                Timestamp.valueOf(LocalDateTime.now())
        );
        memberRepository.save(member);
    }

    public void deleteGroup(Long groupId) {
        boolean exists = groupRepository.existsById(groupId);
        if (!exists) {
            throw new IllegalStateException("Group with id " + groupId + " does not exist!");
        }
        groupRepository.deleteById(groupId);
    }

    @Transactional
    public void updateGroup(Long groupId, String groupName, Long groupAdmin) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalStateException(
                        "Group with id " + groupId + " does not exist!"));

        if (groupName != null &&
                groupName.length() > 0 &&
                !Objects.equals(group.getGroupName(), groupName)) {
            Optional<Group> groupByGroup_name = groupRepository.findGroupByGroupName(groupName);
            if (groupByGroup_name.isPresent()) {
                throw new IllegalStateException("Group name already taken!");
            }
            group.setGroupName(groupName);
        }

        if (groupAdmin != null &&
                !Objects.equals(group.getGroupAdmin(), groupAdmin)) {
            boolean exists = userRepository.existsById(groupAdmin);
            if (!exists) {
                throw new IllegalStateException("User with id " + groupAdmin + " does not exist!");
            }
            Optional<Member> memberByGroupIdAndUserId =
                    memberRepository.findMemberByGroupIdAndUserId(groupId, groupAdmin);
            if (!memberByGroupIdAndUserId.isPresent()) {
                throw new IllegalStateException("User with id " + groupAdmin + " is not a member of group with id " + groupId +"!");
            }
            group.setGroupAdmin(groupAdmin);
            Member member = memberByGroupIdAndUserId.get();
            member.setAdminRights(true);
        }
    }
}