package com.spring.bank.dto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import com.spring.bank.LoggingInterceptor;
import java.util.*;

public class ChatGPT {
   private String apiKey;
   private static final String API_URL = "https://api.openai.com/v1/chat/completions";
   private final RestTemplate restTemplate;
   private List<Map<String, String>> conversationHistory = new ArrayList<>();

   public ChatGPT(String apiKey) {
       this.apiKey = apiKey;
       this.restTemplate = new RestTemplate();
       this.restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
       this.restTemplate.setInterceptors(Collections.singletonList(new LoggingInterceptor()));
   }

   public ChatMessage sendMessage(String message) {
       Map<String, String> userMessage = Map.of("role", "user", "content", message);
       conversationHistory.add(userMessage);

       HttpHeaders headers = new HttpHeaders();
       headers.set("Authorization", "Bearer " + apiKey);
       headers.setContentType(MediaType.APPLICATION_JSON);

       Map<String, Object> requestBody = new HashMap<>();
       requestBody.put("model", "chatgpt-4o-latest");
       requestBody.put("messages", conversationHistory);

       HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
       ResponseEntity<Map> response = restTemplate.postForEntity(API_URL, request, Map.class);
       
       String content = ((Map)((Map)((List)response.getBody().get("choices")).get(0)).get("message")).get("content").toString();
       
       Map<String, String> assistantMessage = Map.of("role", "assistant", "content", content);
       conversationHistory.add(assistantMessage);
       
       return new ChatMessage(content);
   }
}