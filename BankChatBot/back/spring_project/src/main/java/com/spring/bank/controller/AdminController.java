package com.spring.bank.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.bank.dto.TransactionDTO;
import com.spring.bank.dto.UserDTO;
import com.spring.bank.service.AdminService;
import com.spring.bank.service.UserService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:8080")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @GetMapping("/users")
    public List<UserDTO> getUsers(){
    	System.out.println("사용자 목록 조회");
		return adminService.getUsers();
    }
    
    @PutMapping("/{userId}/status")
    public ResponseEntity<Void> updateUserStatus(@PathVariable String userId,
            @RequestBody Map<String, Object> payload){
    	int isActive = (int) payload.get("isActive"); // 변경 후 값 전달
        adminService.updateUserStatus(userId, isActive);
        return ResponseEntity.ok().build();
    }
}