/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.service;

import id.web.ard.springbootjjwt.entity.User;
import id.web.ard.springbootjjwt.security.model.Role;
import java.util.Arrays;
import org.springframework.stereotype.Service;

/**
 *
 * @author ardiansyah
 */
@Service
public class UserService {
	
	// this is just an example, you can load the user from the database from the repository
	public User findByUsername(String username) {
		String userUsername = "user";
		String adminUsername = "admin";
		
		if (username.equals(userUsername)) {
			
			return new User(userUsername, "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Arrays.asList(Role.ROLE_USER));
			
		} else if (username.equals(adminUsername)) {
			
			return new User(adminUsername, "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Arrays.asList(Role.ROLE_ADMIN));
			
		} else {
			return null;
		}
	}
	
}
