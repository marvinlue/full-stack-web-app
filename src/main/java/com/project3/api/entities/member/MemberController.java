package com.project3.api.entities.member;

import com.project3.api.JwtUtil;
import com.project3.api.entities.user.User;
import com.project3.api.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/members")
public class MemberController {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService, JwtUtil jwtUtil, UserService userService) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @GetMapping
    public List<Member> getMembers(
            @RequestParam(required = false) Long groupId,
            @RequestHeader("Authorization") String token) {
        if (groupId != null) {
            return memberService.getMembers(groupId, null);
        }
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null, username);
        return memberService.getMembers(null, user.getId());
    }

    @GetMapping(path = "{groupId}")
    public Member getMemberByGidAndUserId(
            @PathVariable("groupId") Long groupId,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null, username);
        return memberService.getMemberByGidAndUserId(groupId, user.getId());
    }

    @PostMapping
    public void registerNewMember(@RequestParam Long groupId, @RequestBody Member member, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null, username);
        memberService.addNewMember(groupId, user.getId(), member);
    }

    @DeleteMapping(path = "{groupId}")
    public void leaveGroup(
            @PathVariable("groupId") Long groupId,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null, username);
        memberService.leaveGroup(user.getId(), groupId);
    }

    @DeleteMapping
    public void deleteMember(
            @RequestParam Long userId,
            @RequestParam Long groupId,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null, username);
        memberService.deleteMember(user.getId(), userId, groupId);
    }

    @PutMapping
    public void updateMember(
            @RequestParam Long userId,
            @RequestParam Long groupId,
            @RequestParam Boolean adminRights,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null, username);
        memberService.updateMember(user.getId(), userId, groupId, adminRights);
    }

    @PutMapping(path = "{groupId}")
    public void updateMember(
            @PathVariable("groupId") Long groupId,
            @RequestParam Boolean adminRights,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null, username);
        memberService.updateMember(user.getId(), user.getId(), groupId, adminRights);
    }
}
