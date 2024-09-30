package com.javatpoint.service;

import com.javatpoint.model.User;

import java.time.LocalDateTime;

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

	public void generateAndSendOtp(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			String otp = generateOTP();
			user.setOtp(otp);
			user.setOtp_expired_time(LocalDateTime.now().plusMinutes(20));
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
	
	public boolean verifyOtp(String email,String otp) {
		User user = userRepository.findByEmail(email);
		if (user!=null && user.getOtp().equals(otp) &&
				user.getOtp_expired_time().isAfter(LocalDateTime.now())) {
			return true;
		}
		return false;
	}
	
	public void resetPassword(String email, String newPassword) {
		User user = userRepository.findByEmail(email);
		if(user!=null) {
			String hashedPassword=passwordEncoder.encode(newPassword);
			user.setPassword(hashedPassword);
			user.setOtp(null);
			user.setOtp_expired_time(null);
			userRepository.save(user);
		}
		
	}

}
