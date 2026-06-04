package com.marketplace.village.serviceimpl;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.marketplace.village.entity.ShopUser;
import com.marketplace.village.exception.CustomException;
import com.marketplace.village.repository.ShopUserRepository;
import com.marketplace.village.service.ForgotPasswordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl
        implements ForgotPasswordService {

    private final ShopUserRepository userRepository;
    
    private final EmailService emailService;
    

    @Override
    public void sendOtp(String email) {

        ShopUser user = userRepository.findByEmail(email);

        if(user == null){
            throw new CustomException("Email is not found");
        }
                        

        String otp =
                String.valueOf(
                        100000
                                + new Random()
                                .nextInt(900000));

        

        
        user.setOtp(otp);

        user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(2));

        userRepository.save(user);

        emailService.sendOtp(email, otp);
    }

    @Override
    public boolean verifyOtp(
            String email,
            String otp) {

        ShopUser user = userRepository.findByEmail(email);

        if(user == null){
            throw new CustomException("Email not found");
        }

        

        if (LocalDateTime.now()
                .isAfter(user.getOtpExpiryTime())) {

            throw new CustomException(
                    "OTP Expired");
        }

        if (!user.getOtp()
                .equals(otp)) {

            throw new CustomException(
                    "Invalid OTP");
        }

        user.setOtpVerified(true);

        userRepository.save(user);

        return true;
    }

    @Override
    public void resetPassword(
            String email,
            String password) {

        ShopUser user = userRepository.findByEmail(email);

        if(user == null){
            throw new CustomException("Email not found");
        }

        if (!user.getOtpVerified()) {

            throw new CustomException(
                    "Verify OTP first");
        }

        

        user.setPassword(password);
        user.setConfirmPassword(password);
        user.setUpdatedAt(LocalDateTime.now());
        user.setOtpVerified(false);

        userRepository.save(user);
    }
}