package com.otp.controller;

import com.otp.dto.OtpDto;
import com.otp.dto.PhoneDto;
import com.otp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/otp")
public class OtpController {
    private Map<String, String> otpStorage = new HashMap<>();
    @Autowired
    private OtpService otpService;

//-----------------SendOtp-----Query parameterized url-------------------------------------------------
    // http://localhost:8080/api/otp/sendOtp?phoneNumber=yourVerifiedPhoneNO
    @PostMapping("/sendOtp")  // Query parameterized url
    public ResponseEntity<String> sendOtp(@RequestParam String phoneNumber) {
        System.out.println("+" + phoneNumber);
        // Generate Random Otp
        String otp = otpService.generateOtp();

        if (otpService.sendOtp("+" + phoneNumber, otp))
            // Store the otp in Hashmap
            otpStorage.put(phoneNumber, otp);
        return ResponseEntity.ok("OTP sent successfully to: " + "+" + phoneNumber);
    }

//-----------------verifyOtp-----Query parameterized url-------------------------------------------------
    // http://localhost:8080/api/otp/verifyOtp?phoneNumber=yourVerifiedPhoneNO&otp=yourOTP
    @PostMapping("/verifyOtp")   // Otp verification for Query parameterized url
    public ResponseEntity<String> verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {

            // Retrieve the stored OTP for the given phone number
            String storedOtp = otpStorage.get(phoneNumber);
            // compare it with the submitted otp If they match, the otp is valid
            if (storedOtp != null && storedOtp.equals(otp)) {
                // The provided OTP matches the stored OTP
                // Remove the OTP from storage to prevent reuse
                otpStorage.remove(phoneNumber);
                return ResponseEntity.ok("OTP verified successfully");
            }

        return ResponseEntity.ok("Invalid Otp");  // Otp verified successfully
    }


//------------------------SendOtp----- JSON Object to Dto-------------------------------------------------------
    // http://localhost:8080/api/otp/sendJsonOtp
    @PostMapping("/sendJsonOtp")   // Send Otp with JSON Object
    public String sendOtpJson(@RequestBody PhoneDto phoneDto) {
        System.out.println(phoneDto.getPhoneNo());
        // Generate Random Otp
        String otp = otpService.generateOtp();

        if (otpService.sendOtp( phoneDto.getPhoneNo(), otp)){
            // Store the otp temporary in Hashmap
            otpStorage.put(phoneDto.getPhoneNo(), otp);
            return "OTP sent successfully to: " + phoneDto.getPhoneNo();
        } else {
            return "Failed to sent otp";
        }
    }

//------------------------- Verify Otp with JSON Object-------------------------------------------------------------------------------
     // http://localhost:8080/api/otp/verifyJsonOtp
    @PostMapping("/verifyJsonOtp")   // Otp verification for Query parameterized url
    public String verifyOtp(@RequestBody OtpDto otpDto) {

        // Retrieve the stored OTP for the given phone number
        String storedOtp = otpStorage.get(otpDto.getPhoneNumber());
        System.out.println(storedOtp);

        // Retrieve the stored OTP for the user from the db and compare it with the submitted otp
        // If they match, then otp is valid
        if (storedOtp != null && storedOtp.equals(otpDto.getOtpDto())) {
            // The provided OTP matches the stored OTP
            // Remove the OTP from storage to prevent reuse
            otpStorage.remove(otpDto.getPhoneNumber());
            return "OTP is valid";  // Otp verified successfully
        }else {
            return "Invalid Otp";  // Otp verified successfully
        }
    }

}
