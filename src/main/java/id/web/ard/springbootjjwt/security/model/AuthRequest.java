/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.security.model;

/**
 *
 * @author ardiansyah
 */
public class  AuthRequest {
	
	private String username;
	
	private String password;

	public AuthRequest() {
	}

	public AuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "AuthRequest{" + "username=" + username + ", password=" + password + '}';
	}
	
}
