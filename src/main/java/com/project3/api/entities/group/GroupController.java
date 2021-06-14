package com.project3.api.entities.group;

import com.project3.api.JwtUtil;
import com.project3.api.entities.user.User;
import com.project3.api.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/groups")
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public GroupController(GroupService groupService, UserService userService, JwtUtil jwtUtil) {
        this.groupService = groupService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Group> getGroups() {
        return groupService.getGroups();
    }

    @GetMapping(path = "group")
    public Group getGroupByGidOrGroupName(
            @RequestParam (required = false) Long groupId,
            @RequestParam (required = false) String groupName) {
        return groupService.getGroupByGidOrGroupName(groupId, groupName);
    }

    @PostMapping
    public void createNewGroup(
            @RequestBody Group group,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null,username);
        groupService.addNewGroup(group, user.getId());
    }

    @DeleteMapping
    public void deleteGroup(
            @RequestParam Long groupId,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null,username);
        groupService.deleteGroup(user.getId(), groupId);
    }

    @PutMapping
    public void updateGroup(
            @RequestParam Long groupId,
            @RequestParam String groupName,
            @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null,username);
        groupService.updateGroup(user.getId(), groupId, groupName);
    }
}
