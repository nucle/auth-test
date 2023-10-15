package com.example.authtest.auth;

import java.util.Arrays;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DefaultUserDetailsService implements UserDetailsService {

	private final UserRepository myUserRepository;

	public DefaultUserDetailsService(final UserRepository myUserRepository) {
		this.myUserRepository = myUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final var user = myUserRepository.findByUsername(username);
		final var defaultUserDetails = new DefaultUserDetails();

		defaultUserDetails.setUsername(user.getUsername());
		defaultUserDetails.setPassword(user.getPassword());
		defaultUserDetails.setFirstname(user.getFirstName());
		defaultUserDetails.setLastname(user.getLastName());
		defaultUserDetails.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(user.getRole())));

		defaultUserDetails.setEnabled(true);
		defaultUserDetails.setCredentialsNonExpired(true);
		defaultUserDetails.setAccountNonLocked(true);
		defaultUserDetails.setAccountNonExpired(true);
		return defaultUserDetails;
	}
}
