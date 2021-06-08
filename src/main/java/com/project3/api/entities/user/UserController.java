package com.project3.api.entities.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "user")
    public User getUserByIdOrUsername(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String username) {
        return userService.getUserByIdOrUsername(userId, username);
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user,
                                @RequestParam(value = "file", required = false) MultipartFile file) {
        userService.addNewUser(user, file);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        userService.updateUser(userId, username, email, password, file);
    }
}
