package com.spring.bank.dto;

public class ChatMessage  {
	   private String role;
	   private String content;

	   public ChatMessage (String content) {
	       this.content = content;
	   }

	   public String getContent() {
	       return content;
	   }
	}