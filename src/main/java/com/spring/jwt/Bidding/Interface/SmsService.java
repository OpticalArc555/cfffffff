package com.spring.jwt.Bidding.Interface;


import com.spring.jwt.Bidding.DTO.SmsDto;
import com.spring.jwt.entity.SmsEntity;

public interface SmsService {
    void sendSms(String message , String number , String apiKey);

    void saveOtp(SmsEntity smsEntity);

    boolean verifyOtp(SmsDto smsDto);


    public boolean canResendOtp(long mobileNo);

    public void removePreviousOtp(long mobileNo);
}
