package com.project3.api.entities.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getMembers(
            @RequestParam (required = false) Long groupId,
            @RequestParam (required = false) Long userId) {
        return memberService.getMembers(groupId, userId);
    }

    @GetMapping(path = "{groupId}")
    public Member getMemberByGidAndUserId(
            @PathVariable("groupId") Long groupId,
            @RequestParam Long userId) {
        return memberService.getMemberByGidAndUserId(groupId, userId);
    }

    @PostMapping
    public void registerNewMember(@RequestParam Long groupId, @RequestParam Long userId, @RequestBody Member member) {
        memberService.addNewMember(groupId, userId, member);
    }

    @DeleteMapping
    public void leaveGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId) {
        memberService.leaveGroup(userId, groupId);
    }

    @DeleteMapping(path = "{currentUserId}")
    public void deleteMember(
            @PathVariable("currentUserId") Long currentUserId,
            @RequestParam Long userId,
            @RequestParam Long groupId) {
        memberService.deleteMember(currentUserId, userId, groupId);
    }

    @PutMapping(path = "{currentUserId}")
    public void updateMember(
            @PathVariable("currentUserId") Long currentUserId,
            @RequestParam Long userId,
            @RequestParam Long groupId,
            @RequestParam Boolean adminRights) {
        memberService.updateMember(currentUserId, userId, groupId, adminRights);
    }
}
