package com.spring.bank;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import java.io.IOException;

public class LoggingInterceptor implements ClientHttpRequestInterceptor {
	   @Override
	   public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
	       System.out.println("Request Headers: " + request.getHeaders());
	       System.out.println("Request Body: " + new String(body));
	       ClientHttpResponse response = execution.execute(request, body);
	       System.out.println("Response Status: " + response.getStatusCode());
	       return response;
	   }
	}