package com.javatpoint.service;

import com.javatpoint.DTO.LoginDTO;
import com.javatpoint.DTO.UserDTO;
import com.javatpoint.model.LoginMessage;

public interface UserService {
	
	LoginMessage addUser(UserDTO userdto);
	LoginMessage loginUser(LoginDTO logindto);
	boolean updateLoginTime(String email);
	

}
