package com.thanhtrung.loginsecurity.service;

public interface IEmailService {

	void sendRegistrationUserConfirm(String email);

	void sendOtpCodeForResetingPassword(String Otp);

}
