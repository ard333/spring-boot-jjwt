/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *
 * @author ardiansyah
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UnauthorizedHandler unauthorizedHandler;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new PBKDF2Encoder();
    }
	
	@Bean
    public AuthenticationFilter authenticationFilterBean() throws Exception {
        return new AuthenticationFilter();
    }
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			// we don't need CSRF because our token is invulnerable
			.csrf().disable()
			
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			
			// don't create session
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			
			.authorizeRequests()
			
			// allow auth url
			.antMatchers("/auth").permitAll()
			
			.anyRequest().authenticated();
		
		// custom JWT based security filter
		httpSecurity.addFilterBefore(authenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);

		// disable page caching
		httpSecurity.headers().cacheControl();
	}
}
