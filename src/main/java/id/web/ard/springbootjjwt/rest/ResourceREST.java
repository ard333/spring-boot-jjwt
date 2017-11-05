/*
 * Ardiansyah | http://ard.web.id
 * 
 */
package id.web.ard.springbootjjwt.rest;

import id.web.ard.springbootjjwt.entity.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ardiansyah
 */
@RestController
public class ResourceREST {
	
	@RequestMapping(value = "resource/user", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> user() {
		return ResponseEntity.ok(new Message("Content for user"));
	}
	
	@RequestMapping(value = "resource/admin", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> admin() {
		return ResponseEntity.ok(new Message("Content for admin"));
	}
	
	@RequestMapping(value = "resource/user-or-admin", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> userOrAdmin() {
		return ResponseEntity.ok(new Message("Content for user or admin"));
	}
}
