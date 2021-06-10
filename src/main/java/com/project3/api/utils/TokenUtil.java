package com.project3.api.utils;

import com.project3.api.JwtUtil;
import com.project3.api.entities.user.User;
import com.project3.api.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class TokenUtil {
    private JwtUtil jwtUtil;
    private UserService userService;

    @Autowired
    public TokenUtil(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    public User getUserFromToken(String token){
        String userName = jwtUtil.extractUsername(token.substring(7));
        return userService.getUserByIdOrUsername(null, userName);
    }
}
