package com.marketplace.village.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.village.service.SmsService;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/send")
    public String sendOtp(@RequestParam String mobile) {

        String otp = String.valueOf(
                (int)((Math.random() * 900000) + 100000));

        smsService.sendOtp(mobile, otp);

        return "OTP Sent";
    }
}