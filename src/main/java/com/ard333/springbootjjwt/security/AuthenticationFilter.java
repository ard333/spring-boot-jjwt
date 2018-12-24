package com.ard333.springbootjjwt.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ard333.springbootjjwt.entity.User;
import com.ard333.springbootjjwt.security.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author ard333
 */
@Log4j2
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtil jwtUtil;

	private final String authHeader = "Authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		
		//CORS
		response.addHeader("Access-Control-Allow-Origin", "*");
		if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.addHeader("Access-Control-Allow-Headers", "Authorization");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type");
			response.addHeader("Access-Control-Max-Age", "1");
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		}
		
		final String authHeader = request.getHeader(this.authHeader);
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			if (jwtUtil.validateToken(token)) {
				try {
					Claims claims = jwtUtil.getAllClaimsFromToken(token);
					List<String> rolesString = claims.get("role", List.class);
					Boolean enabled = claims.get("enabled", Boolean.class);
					
					List<Role> roles = new ArrayList<>();
					for (String r : rolesString) {
						roles.add(Role.valueOf(r));
					}

					User u = new User(claims.getSubject(), null, enabled, roles);
					
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} catch (Exception e) {
					log.error("ERROR ", e);
				}
			}
		}
		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(request, response);
		}
	}
}