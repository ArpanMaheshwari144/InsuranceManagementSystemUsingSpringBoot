package com.javatpoint.service;

import com.javatpoint.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.javatpoint.repository.UserRepository;

@Service
public class OtpService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserRepository userRepository;
	
	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;

	public void sendOtpEmail(String toEmail, String Otp) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject("You Otp Send");
		message.setText("Your new password " + Otp);
		javaMailSender.send(message);
	}

	public void resetPassword(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			String otp = generateOTP();
			String hashedotp=passwordEncoder.encode(otp);
			user.setPassword(hashedotp); // You may want to hash it for security
			userRepository.save(user);
			sendOtpEmail(email, otp);
		} else {
			throw new RuntimeException("User not found");
		}
	}

	public String generateOTP() {
		int otp = (int) (Math.random() * 9000) + 1000;
		return String.valueOf(otp);
	}

}
