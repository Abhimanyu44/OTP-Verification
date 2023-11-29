package com.otp.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class OtpService {
    @Value("${twilio.accountSID}")
    public String accountSID;
    @Value("${twilio.authToken}")
    public String authToken;
    @Value("${twilio.twilioPhoneNo}")
    public String twilioPhoneNo;  // +16562130877-----Twilio phone number

    //-------------------generateOtp------------------------------------------------
    public String generateOtp() {
        //Generate a random otp, eg. a 6-digit no. using Random Utility class object
        int otp = new Random().nextInt(900000) + 100000;
        // Send the otp via Twilio Sms
        return String.valueOf(otp);
    }

    //-------------------sendOtp------------------------------------------------
    public boolean sendOtp(String phoneNumber, String otp) {
        Twilio.init(accountSID, authToken);   // it will check whether user is authenticated or not
        try {
            Message message = Message.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioPhoneNo),
                    "Your OTP is: " + otp
            ).create();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

