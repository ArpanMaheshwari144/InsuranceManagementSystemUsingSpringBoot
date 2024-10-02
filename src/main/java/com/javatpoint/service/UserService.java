package com.javatpoint.service;

import com.javatpoint.DTO.LoginDTO;
import com.javatpoint.DTO.UserDTO;
import com.javatpoint.model.LoginMessage;
import com.javatpoint.model.User;

public interface UserService {
	
	LoginMessage addUser(UserDTO userdto);
	LoginMessage loginUser(LoginDTO logindto);
	boolean updateLoginTime(String email);
	
	
	User findByVerificationToken(String token);
	

}
