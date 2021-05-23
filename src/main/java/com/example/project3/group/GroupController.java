package com.example.project3.group;

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
    public void createNewGroup(@RequestBody Group group) {
        groupService.addNewGroup(group);
    }

    @DeleteMapping(path = "{groupId}")
    public void deleteGroup(@PathVariable("groupId") Long groupId) {
        groupService.deleteGroup(groupId);
    }

    @PutMapping(path = "{groupId}")
    public void updateGroup(
            @PathVariable("groupId") Long groupId,
            @RequestParam(required = false) String groupName,
            @RequestParam(required = false) Long groupAdmin) {
        groupService.updateGroup(groupId, groupName, groupAdmin);
    }
}