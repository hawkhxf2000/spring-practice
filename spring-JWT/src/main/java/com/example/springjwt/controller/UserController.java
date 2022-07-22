package com.example.springjwt.controller;

import com.example.springjwt.entity.UserDemo;
import com.example.springjwt.util.JWTUtil;
import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final String USERNAME="zs";
    private String PASSWORD = "123456";

    @GetMapping("/login")
    public UserDemo login(@RequestParam String username, String password){
        if(USERNAME.equals(username) && PASSWORD.equals(password)){
            UserDemo user = new UserDemo();
            user.setUsername(username);
            user.setPassword(password);
            user.setToken(JWTUtil.createToken());
            return user;
        }
        return null;
    }
}
