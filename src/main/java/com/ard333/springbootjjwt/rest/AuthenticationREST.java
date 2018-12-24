package com.ard333.springbootjjwt.rest;

import com.ard333.springbootjjwt.entity.User;
import com.ard333.springbootjjwt.security.JWTUtil;
import com.ard333.springbootjjwt.security.PBKDF2Encoder;
import com.ard333.springbootjjwt.security.model.AuthRequest;
import com.ard333.springbootjjwt.security.model.AuthResponse;
import com.ard333.springbootjjwt.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

/**
 *
 * @author ard333
 */
@RestController @Log4j2
public class AuthenticationREST {
	
	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private PBKDF2Encoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
		try {
			User u = userService.findByUsername(authRequest.getUsername());
			if (u != null) {
				CharSequence password = u.getPassword();
				String passwordInput = passwordEncoder.encode(authRequest.getPassword());
				if (password.equals(passwordInput)) {
					return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(u)));
				} else {
					log.error("password not match");
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
			} else {
				log.error("user null");
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			log.error("error", e);
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
}
