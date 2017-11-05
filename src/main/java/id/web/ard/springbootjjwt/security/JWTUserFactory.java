/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.security;

import id.web.ard.springbootjjwt.entity.User;
import id.web.ard.springbootjjwt.security.model.JWTUser;
import id.web.ard.springbootjjwt.security.model.Role;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author ardiansyah
 */
public final class JWTUserFactory {

	private JWTUserFactory() {
	}

	public static JWTUser create(User user) {
		return new JWTUser(
			user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.getRoles()), user.getEnabled()
		);
	}

	public static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> authorities) {
		return authorities.stream()
				.map(authority -> new SimpleGrantedAuthority(authority.name()))
				.collect(Collectors.toList());
	}
}
