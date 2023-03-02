package com.foxminded.javaspring.universitycms.configuration;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {
	
	@Bean
	public Random random() {
		return new Random();
	}

}
