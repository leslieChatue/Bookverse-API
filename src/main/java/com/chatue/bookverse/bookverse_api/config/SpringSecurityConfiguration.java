
package com.chatue.bookverse.bookverse_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.chatue.bookverse.bookverse_api.security.BookVerseUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfiguration {

	//requestMatchers permet de securiser les URL d'api en fonction des rôles ,autorites ou statut d'authentification
	private final BookVerseUserDetailsService bookVerseUserDetailsService;
	
	@Bean
     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/login","/lougout","/").permitAll()
                .anyRequest().authenticated()
            )
           .formLogin(form -> form
        		   .loginPage("/login")
        		   .defaultSuccessUrl("/", true)
        		   .permitAll())
           .logout(logout -> logout
        		   .logoutUrl("/logout")
        		   .logoutSuccessUrl("/logout")
        		   .permitAll())
           .userDetailsService(bookVerseUserDetailsService);
           // .httpBasic();

        return http.build();
    }
    
    
}
