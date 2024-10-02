package com.javatpoint.serviceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.javatpoint.DTO.LoginDTO;
import com.javatpoint.DTO.UserDTO;
import com.javatpoint.model.LoginMessage;
import com.javatpoint.model.User;
import com.javatpoint.repository.UserRepository;
import com.javatpoint.service.EmailService;
import com.javatpoint.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	EmailService emailService;
	
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

	
	

	@Override
	public LoginMessage addUser(UserDTO userdto) {
		User user =  new User(
				userdto.getUserid(),
				userdto.getUsername(),
				userdto.getEmail(),
				this.passwordEncoder.encode(userdto.getPassword()) 
				
				);
		User existuser =userRepository.findByEmail(userdto.getEmail());
//		find by username kal bvanan hai
		if(existuser!=null){
			if(existuser.getEmail().equals(user.getEmail())) {
				return new LoginMessage("email already  exist",false); 
			}
			
		}
//		if (existuser!=null) {
//			if(existuser.getUsername().equals(user.getUsername())) {
//				return new LoginMessage("username already  exist",false); 
//			}
//			
//		}
		if(!isValidEmail(user.getEmail())) {
			return new LoginMessage("envalid email",false);
		}
		String token=UUID.randomUUID().toString();
		user.setVerification_token(token);
		user.setVerification_token_expired_time(LocalDateTime.now().plusHours(24));	
		user.setIs_enabled(false);
		emailService.sendVerificationEmail(user);
		userRepository.save(user);
		return new LoginMessage("Login succes", true);
	}
	
	

	@Override
	public LoginMessage loginUser(LoginDTO logindto) {
		String message="";
		User user1=userRepository.findByEmail(logindto.getEmail());
		if(user1!=null) {
			String password=logindto.getPassword();
			String encodedPassword=user1.getPassword();
			Boolean isPasswordRight = passwordEncoder.matches(password, encodedPassword);
			if (isPasswordRight) {
				Optional<User> user = userRepository.findOneByEmailAndPassword(logindto.getEmail(), encodedPassword);
				if(user.isPresent()) {
					return new LoginMessage("Login succes", true);
				}
				else {
					return new LoginMessage("Login Failed",false);
				}
				
			}
			else {
				return new LoginMessage("Password did not Match",false);
			}
		}
		else {
			return new LoginMessage("email does not exist",false);
			
		}
	}
	
	public boolean updateLoginTime(String email) {
		Optional<User> user1=Optional.ofNullable(userRepository.findByEmail(email));
		if(user1.isPresent() && user1.get().isIs_enabled()==true) {
			User user = user1.get();
			user.setLast_login(Timestamp.valueOf(LocalDateTime.now()));
			user.setEmail_sent(false);
			userRepository.save(user);
			return true;
			
		}
		return false;
		
	}



	@Override
	public User findByVerificationToken(String token) {
		return userRepository.findByVerificationToken(token);
		
	}
	
	public boolean isValidEmail(String email) {
		Pattern pattern=Pattern.compile(EMAIL_REGEX);
		return pattern.matcher(email).matches();
	}
	

}
