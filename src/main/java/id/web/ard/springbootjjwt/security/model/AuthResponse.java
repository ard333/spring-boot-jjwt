/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.security.model;

/**
 *
 * @author ardiansyah
 */
public class AuthResponse {
	
	private final String token;

	public AuthResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	@Override
	public String toString() {
		return "AuthResponse{" + "token=" + token + '}';
	}
	
}
