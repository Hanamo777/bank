package com.spring.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.spring.bank.dto.AccountDTO;
import com.spring.bank.dto.UserDTO;
import com.spring.bank.service.UserService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public String register(@RequestBody UserDTO user) {
        boolean result = userService.register(user);
        System.out.println("회원가입 요청 ID: " + user.getUserId());  // 디버깅용
        return result ? "success" : "duplicate id";
    }
    
    @PostMapping("/login")
    public UserDTO login(@RequestBody UserDTO user) {
        System.out.println("로그인 요청 ID: " + user.getUserId());  // 디버깅용
        return userService.login(user);
    }
    
    @GetMapping("/{accountNumber}")
    public UserDTO getAccount(@PathVariable int accountNumber) {
        System.out.println("계좌 조회: " + accountNumber);  // 디버깅용
        return userService.getAccount(accountNumber);
    }
}