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

/**
 *
 * @author ard333
 */
@RestController
public class AuthenticationREST {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private PBKDF2Encoder passwordEncoder;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
		User u = userService.findByUsername(authRequest.getUsername());
		if (u != null) {
			if (passwordEncoder.encode(authRequest.getPassword()).equals(u.getPassword())) {
				return ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(u)));
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

}
