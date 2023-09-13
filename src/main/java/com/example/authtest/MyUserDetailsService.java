package com.example.authtest;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private MyUserRepository myUserRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		MyUser myUser = myUserRepository.findByUsername(username);
		MyUserDetails userDetails = new MyUserDetails();

		userDetails.setUsername(myUser.getUsername());
		userDetails.setPassword(myUser.getPassword());
		userDetails.setFirstname(myUser.getFirstName());
		userDetails.setLastname(myUser.getLastName());
		userDetails.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(myUser.getRole())));
		return userDetails;
	}
}
