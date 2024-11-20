package com.spring.bank.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.bank.service.ChatGPTService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatGPTService chatGPTService;
    
    private Map<String, ChatGPTService> userChatServices = new HashMap<>();

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody Map<String, String> request) {
        String userId = request.get("userId"); // Vue에서 userId도 보내도록 수정 필요
        
        ChatGPTService userService = userChatServices.computeIfAbsent(userId, 
            k -> new ChatGPTService(chatGPTService.getApiKey()));
            
        String response = userService.sendMessage(request.get("message"));
        return ResponseEntity.ok(Map.of("message", response));
    }
}