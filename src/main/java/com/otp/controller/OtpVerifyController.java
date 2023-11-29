package com.otp.controller;

import com.otp.service.OtpVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OtpVerifyController {
    // used to Stores phone numbers and OTPs
    private Map<String, String> otpStorage = new HashMap<>();

    @Autowired
    private OtpVerifyService otpVerifyService;

    @PostMapping("/send-Otp")
    public ResponseEntity<String> sendOtp(@RequestParam String phoneNumber) {
        System.out.println(phoneNumber);
        // Generate Otp
        String otp = otpVerifyService.generateOtp();
        boolean sent = otpVerifyService.sendOtp("+"+phoneNumber, otp);
        System.out.print(sent);
        if (sent) {
            otpStorage.put(phoneNumber, otp);
            // Store the otp and associate it with the user in ur db
            return ResponseEntity.ok("OTP sent successfully to: "+phoneNumber);
        } else {
            return ResponseEntity.badRequest().body("Failed to send OTP");
        }
    }

    @PostMapping("/verify-Otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String phoneNumber, @RequestParam String otp) {

        // Retrieve the stored OTP for the phoneNumber from the db/HashMap
        String storedOtp = otpStorage.get(phoneNumber);
        // compare it with the submitted otp If they match, the otp is valid
        if (storedOtp != null && storedOtp.equals(otp)){
              otpStorage.remove(phoneNumber);
            return ResponseEntity.ok("OTP is valid");  // Otp verified successfully
        } else{
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }

    //    @PostMapping("/verify_otp") //This is with DTO Object
//    public String verifyOtpjson(@RequestBody PhoneDto dto){
//        String storedOtp= otpStorage.get(dto.getPhoneDto());
//        System.out.println(storedOtp);
//        System.out.println(dto.getOtpDto());
//        if(storedOtp !=null && storedOtp.equals(dto.getOtpDto())){
//            otpStorage.remove(dto.getPhoneDto());
//            return "OTP verified successfully";
//        }else {
//            return "Invalid OTP";
//        }
//    }

//    @PostMapping("/send_otp")//This is with DTO Object
//    public String sendOtpJson1(@RequestBody PhoneDto dto){
//        System.out.println("+"+dto.getPhoneDto());
//        // Generate a random OTP (you can use libraries like java.util.Random)
//        String otp = otpService.generateOtp();
//
//        if(otpService.sendOtp("+"+dto.getPhoneDto(), otp)){
//            otpStorage.put(dto.getPhoneDto(), otp);
//            return "OTP sent successfully";
//        }else {
//            return "failed to send OTP";
//        }
//    }
//
//
//    @PostMapping("/verify_otp") //This is with DTO Object
//    public String verifyOtpjson1(@RequestBody OtpDto dto){
//        String storedOtp= otpStorage.get(dto.getPhoneNumber());
//        System.out.println(storedOtp);
//        System.out.println(dto.getOtpDto());
//        if(storedOtp !=null && storedOtp.equals(dto.getOtpDto())){
//            otpStorage.remove(dto.getPhoneNumber());
//            return "OTP verified successfully";
//        }else {
//            return "Invalid OTP";
//        }
//    }
}
