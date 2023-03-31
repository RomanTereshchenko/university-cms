package com.foxminded.javaspring.universitycms.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.foxminded.javaspring.universitycms.model.Person;
import com.foxminded.javaspring.universitycms.service.PersonService;

@EnableWebMvc
@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

	private PersonService personService;

	@Autowired
	public SecSecurityConfig(PersonService personService) {
		this.personService = personService;
	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		List<Person> allpersons = personService.findAllPersons();
		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("000")).roles("ADMIN").build();
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
		for (Person person : allpersons) {
			UserDetails userPerson = User.withUsername(person.getLogin())
					.password(passwordEncoder().encode(person.getPassword())).roles(person.getRole().toString())
					.build();
			inMemoryUserDetailsManager.createUser(userPerson);
		}
		inMemoryUserDetailsManager.createUser(admin);
		return inMemoryUserDetailsManager;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/anonymous")
				.anonymous().antMatchers("/login*").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").defaultSuccessUrl("/index", true).failureUrl("/login-error");
		return http.build();
	}

}
