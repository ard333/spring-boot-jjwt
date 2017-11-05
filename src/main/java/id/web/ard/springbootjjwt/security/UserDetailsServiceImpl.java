/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.security;

import id.web.ard.springbootjjwt.entity.User;
import id.web.ard.springbootjjwt.security.model.Role;
import id.web.ard.springbootjjwt.service.UserService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ardiansyah
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserService userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = userRepository.findByUsername(username);
		if (u != null) {
			return JWTUserFactory.create(u);
		} else {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
	}
	
}
