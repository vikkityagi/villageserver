package com.marketplace.village.service;

public interface ForgotPasswordService {

    void sendOtp(String email);

    boolean verifyOtp(String email,
                      String otp);

    void resetPassword(String email,
                       String password);
}