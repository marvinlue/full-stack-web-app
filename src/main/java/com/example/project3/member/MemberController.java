package com.example.project3.member;

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
    public List<Member> getMembers() {
        return memberService.getMembers();
    }

    @PostMapping
    public void registerNewMember(@RequestBody Member member) {
        memberService.addNewMember(member);
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