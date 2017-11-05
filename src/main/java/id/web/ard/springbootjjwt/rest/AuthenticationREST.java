/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.rest;

import id.web.ard.springbootjjwt.entity.User;
import id.web.ard.springbootjjwt.security.JWTUtil;
import id.web.ard.springbootjjwt.security.model.AuthRequest;
import id.web.ard.springbootjjwt.security.model.AuthResponse;
import id.web.ard.springbootjjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author ardiansyah
 */
@RestController
public class AuthenticationREST {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtTokenUtil;
	
	@Autowired
	private UserService userRepository;
	
	@RequestMapping(value = "auth", method = RequestMethod.POST)
	public ResponseEntity<?> auth(@RequestBody AuthRequest ar) {
		
		final Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(ar.getUsername(), ar.getPassword())
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		User u = userRepository.findByUsername(ar.getUsername());
		if (u != null) {
			String token = jwtTokenUtil.generateToken(u);
			return ResponseEntity.ok(new AuthResponse(token));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
}
