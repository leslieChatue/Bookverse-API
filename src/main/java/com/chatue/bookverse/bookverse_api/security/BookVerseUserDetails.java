package com.chatue.bookverse.bookverse_api.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.chatue.bookverse.bookverse_api.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookVerseUserDetails implements UserDetails{

	
	private static final long serialVersionUID = 1L;
	private final User user;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		 return List.of(
		            new SimpleGrantedAuthority(
		                    user.getRoles().getLibelleRole().name()
		            )
		    );
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}

	@Override
	public boolean isEnabled() {
	    return true;
	}
}
