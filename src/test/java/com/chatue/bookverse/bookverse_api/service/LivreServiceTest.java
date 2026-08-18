package com.chatue.bookverse.bookverse_api.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.chatue.bookverse.bookverse_api.dao.LivreDao;
import com.chatue.bookverse.bookverse_api.service.impl.LivreServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LivreServiceTest {
	@Mock //Mok est un objet fictif il ne sticke rien n'enregistre rien et ne lit rien , c'est nous qui lui donnons un comportement pra défaut
	LivreDao livreDao;
	
	@InjectMocks //C'est l'objet réel sur lequel on pourra faire des modifications
	LivreServiceImpl livreService;
	
}
