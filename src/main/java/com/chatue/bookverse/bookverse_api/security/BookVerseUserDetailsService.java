package com.chatue.bookverse.bookverse_api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chatue.bookverse.bookverse_api.dao.UserDao;
import com.chatue.bookverse.bookverse_api.entity.User;
import com.chatue.bookverse.bookverse_api.exception.RessourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookVerseUserDetailsService implements UserDetailsService {

	private final UserDao userDao;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user= userDao.findByEmail(email).orElseThrow(() -> new RessourceNotFoundException("Aucun utilisaeur trouvé avec cet email"+email));
		return new BookVerseUserDetails(user);
	}

}
