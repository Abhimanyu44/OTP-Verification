package com.otp.dto;

public class OtpDto {

        private String phoneDto;
        private String otpDto;

        public String getOtpDto() {
            return otpDto;
        }
        public void setOtpDto(String otpDto) {
            this.otpDto = otpDto;
        }
        public String getPhoneNumber() {
            return phoneDto;
        }
        public void getPhoneNumber(String phoneDto) {
            this.phoneDto = phoneDto;
        }
    }

