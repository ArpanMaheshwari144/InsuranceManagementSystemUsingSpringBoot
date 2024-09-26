package com.javatpoint.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.javatpoint.model.User;

import com.javatpoint.repository.UserRepository;



@Service
public class EmailScheduler {

	@Autowired	
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
//	@Scheduled(cron = "*/1 * * * * *") 
	public void sendEmailToEligibleUsers(){
		System.out.println("ehjfguew");
		LocalDateTime time24HoursAgo = LocalDateTime.now().minusMinutes(1); 
		List<User> users =userRepository.getUserLoggedIn24HoursAgo(time24HoursAgo);
		for (User obj: users) {
			if(!obj.getEmail_sent()) {
				emailService.sendPolicyClaimedEmail(obj.getEmail());
				obj.setEmail_sent(true);
				userRepository.save(obj);
			}
			
			
		}
		
		
	}


}
