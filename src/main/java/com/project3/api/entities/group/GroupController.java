package com.project3.api.entities.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) { this.groupService = groupService; }

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
    public void createNewGroup(@RequestParam Long userId, @RequestBody Group group) {
        groupService.addNewGroup(group, userId);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteGroup(
            @PathVariable("userId") Long userId,
            @RequestParam Long groupId) {
        groupService.deleteGroup(userId, groupId);
    }

    @PutMapping(path = "{userId}")
    public void updateGroup(
            @PathVariable("userId") Long userId,
            @RequestParam Long groupId,
            @RequestParam String groupName) {
        groupService.updateGroup(userId, groupId, groupName);
    }
}
