package com.otp.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpVerifyService {
    // used to Stores phone numbers and OTPs

    @Value("${twilio.accountSID}")
    public String accountSID;

    @Value("${twilio.authToken}")
    public String authToken;
    @Value("${twilio.twilioPhoneNo}")
    public String twilioPhoneNo;  // +16562130877-----Twilio phone number

    public String generateOtp() {
        //Generate a random otp, eg. a 6-digit no.
        int otp = new Random().nextInt(900000) + 100000;
        return String.valueOf(otp);
    }

    public boolean sendOtp(String phoneNumber, String otp) {
        Twilio.init(accountSID, authToken);   // it will check whether user is authenticated or not
        try {
            Message message = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioPhoneNo),
                    "Your OTP is: " + otp
            ).create();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
