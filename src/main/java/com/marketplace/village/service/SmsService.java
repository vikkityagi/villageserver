package com.marketplace.village.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendOtp(String mobileNumber, String otp) {

        String url = "http://172.20.7.86:8080/send";

        Map<String, String> request = new HashMap<>();
        request.put("phone", mobileNumber);
        request.put("message", "Your OTP is: " + otp);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, request, String.class);

        System.out.println(response.getBody());
    }
}