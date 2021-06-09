package com.project3.api.entities.user;

import com.project3.api.AuthenticationRequest;
import com.project3.api.AuthenticationResponse;
import com.project3.api.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

enum Status {
    SUCCESS,
    USER_ALREADY_EXISTS,
    FAILURE
}

@RestController
@RequestMapping(path = "api/users")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

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
    public void deleteUser(@PathVariable("userId") Long userId, @RequestHeader("Authorization") String token) {

        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(userId,null);

        if (username.equals(user.getUsername())) {
            userService.deleteUser(userId);
        }
    }

    @PutMapping(path = "{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(value = "file", required = false) MultipartFile file),
            @RequestHeader("Authorization") String token) {

        String token_username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(userId,null);

        if (token_username.equals(user.getUsername())) {
            userService.updateUser(userId, username, email, password, file);
        }
    }

    @PostMapping(path = "register")
    public Status registerUser(@RequestBody User newUser){
        List<User> users = userService.getUsers();
        for(User user : users){
            if (newUser.getUsername().contentEquals(user.getUsername())){
                return Status.USER_ALREADY_EXISTS;
            }
        }
        userService.addNewUser(newUser);
        return Status.SUCCESS;

    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping(path = "login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
