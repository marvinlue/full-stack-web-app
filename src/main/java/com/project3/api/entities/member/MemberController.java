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

    @DeleteMapping(path = "{groupId}")
    public void deleteGroup(
            @PathVariable("groupId") Long groupId,
            @RequestParam Long userId) {
        memberService.deleteMember(groupId, userId);
    }

    @PutMapping(path = "{groupId}")
    public void updateMember(
            @PathVariable("groupId") Long groupId,
            @RequestParam Long userId,
            @RequestParam Boolean adminRights) {
        memberService.updateMember(groupId, userId, adminRights);
    }
}
