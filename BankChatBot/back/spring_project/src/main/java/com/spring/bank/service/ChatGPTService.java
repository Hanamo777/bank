package com.spring.bank.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.bank.dto.ChatGPT;
import com.spring.bank.dto.ChatMessage;

@Service
public class ChatGPTService {
    @Value("${chatgpt.api-key}")
    private String apiKey;
    
    private final ChatGPT chatGPT;
    
    public ChatGPTService(@Value("${chatgpt.api-key}") String apiKey) {
        this.chatGPT = new ChatGPT(apiKey);
        this.apiKey = apiKey;
    }

    public String sendMessage(String message) {
        ChatMessage response = chatGPT.sendMessage(message);
        return response.getContent();
    }

    public String getApiKey() {
        return this.apiKey;
    }
}