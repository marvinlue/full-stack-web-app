package com.project3.api.entities.user;

import com.project3.api.AuthenticationRequest;
import com.project3.api.AuthenticationResponse;
import com.project3.api.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

enum Status {
    SUCCESS,
    USER_ALREADY_EXISTS,
    FAILURE
}

@CrossOrigin
@RestController
@RequestMapping(path = "api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final JwtUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, JwtUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "user")
    public User getUserByIdOrUsername(
            @RequestParam(required = false) String username) {
        return userService.getUserByIdOrUsername(null, username);
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user,
                                @RequestParam(value = "file", required = false) MultipartFile avatar) {
        userService.addNewUser(user, avatar);
    }

    @DeleteMapping
    public void deleteUser(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null, username);
        userService.deleteUser(user.getId());
    }

    @PutMapping
    public ResponseEntity<?> updateUser(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(value = "file", required = false) MultipartFile avatar,
            @RequestHeader("Authorization") String token) {
        String token_username = jwtUtil.extractUsername(token.substring(7));
        User user = userService.getUserByIdOrUsername(null,token_username);
        userService.updateUser(user.getId(), username, email, password, avatar);
        final UserDetails userDetails = userService
                .loadUserByUsername(user.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping(path = "register")
    public Status registerUser(
            @RequestBody User newUser,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        List<User> users = userService.getUsers();
        for(User user : users){
            if (newUser.getUsername().contentEquals(user.getUsername())){
                return Status.USER_ALREADY_EXISTS;
            }
        }
        userService.addNewUser(newUser, file);
        return Status.SUCCESS;

    }

    @PostMapping(path = "login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (AuthenticationException e){

            return ResponseEntity.badRequest().body(Status.FAILURE);
        }

        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt;

        if (authenticationRequest.getRememberMe()) {
            jwt = jwtTokenUtil.generateRememberMeToken(userDetails);
        } else {
            jwt = jwtTokenUtil.generateToken(userDetails);
        }

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
