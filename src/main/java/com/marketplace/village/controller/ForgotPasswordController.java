package com.marketplace.village.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.village.dto.ResetPasswordRequest;
import com.marketplace.village.dto.SendOtpRequest;
import com.marketplace.village.dto.VerifyOtpRequest;
import com.marketplace.village.service.ForgotPasswordService;
import org.springframework.web.bind.annotation.RequestBody;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ForgotPasswordController {

    private final ForgotPasswordService service;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(
            @RequestBody SendOtpRequest request) {

        service.sendOtp(request.getEmail());

        return ResponseEntity.ok(
                "OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        service.verifyOtp(
                request.getEmail(),
                request.getOtp());

        return ResponseEntity.ok(
                "OTP verified successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        service.resetPassword(
                request.getEmail(),
                request.getNewPassword());

        return ResponseEntity.ok(
                "Password reset successful");
    }
}