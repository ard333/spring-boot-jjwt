/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.security;

import id.web.ard.springbootjjwt.security.model.JWTUser;
import id.web.ard.springbootjjwt.security.model.Role;
import io.jsonwebtoken.Claims;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author ardiansyah
 */
public class AuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JWTUtil jwtTokenUtil;

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
		
		String username;
		String authToken;
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			authToken = authHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
			} catch (Exception e) {
				username = null;
				authToken = null;
			}
		} else {
			username = null;
			authToken = null;
		}
		
		if (username != null && authToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			Claims claims = jwtTokenUtil.getAllClaimsFromToken(authToken);
			if (jwtTokenUtil.validateToken(authToken)) {
				List<String> rolesMap = claims.get("role", List.class);
				List<Role> roles = new ArrayList<>();
				for (String rolemap : rolesMap) {
					roles.add(Role.valueOf(rolemap));
				}
				UserDetails userDetails = new JWTUser(
					username, null, JWTUserFactory.mapToGrantedAuthorities(roles), claims.get("enable", Boolean.class)
				);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
			chain.doFilter(request, response);
		}
	}
}