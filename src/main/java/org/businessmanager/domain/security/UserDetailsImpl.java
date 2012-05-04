package org.businessmanager.domain.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Christian Ternes
 *
 */
public class UserDetailsImpl extends org.springframework.security.core.userdetails.User implements UserDetails {

	private List<String> grantedRoles = new ArrayList<String>();
	
	public UserDetailsImpl(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
	}
	
	public void addGrantedRole(String role) {
		this.grantedRoles.add(role);
	}
	
	public List<String> getGrantedRoles() {
		return grantedRoles;
	}
	
	public void setGrantedRoles(List<String> roles) {
		this.grantedRoles = roles;
	}
	

	
}
