/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import id.web.ard.springbootjjwt.security.model.Role;
import java.util.List;

/**
 *
 * @author ardiansyah
 */
public class User {
	
	private String username;
	
	private String password;
	
	private Boolean enabled;
	
	private List<Role> roles;

	public User() {
	}

	public User(String username) {
		this.username = username;
	}
	
	public User(String username, String password, Boolean enabled, List<Role> roles) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User{" + "username=" + username + ", password=" + password + ", enabled=" + enabled + ", roles=" + roles + '}';
	}
	
}