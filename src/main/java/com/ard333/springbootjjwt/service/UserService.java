package com.ard333.springbootjjwt.service;

import com.ard333.springbootjjwt.entity.User;
import com.ard333.springbootjjwt.security.model.Role;
import java.util.Arrays;
import org.springframework.stereotype.Service;

/**
 *
 * @author ard333
 */
@Service
public class UserService {
	
	// this is just an example, you can load the user from the database from the repository
	public User findByUsername(String username) {
		String userUsername = "user";

		//generated from password encoder
		String userPassword = "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=";

		String adminUsername = "admin";

		//generated from password encoder
		String adminPassword = "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=";
		
		if (username.equals(userUsername)) {
			return new User(userUsername, userPassword, true, Arrays.asList(Role.ROLE_USER));
		} else if (username.equals(adminUsername)) {
			return new User(adminUsername, adminPassword, true, Arrays.asList(Role.ROLE_ADMIN));
		} else {
			return null;
		}
	}
	
}
