package com.zap.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableEurekaClient
public class ZapAuthenticationApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(ZapAuthenticationApplication.class, args);
		
//		String encode = new BCryptPasswordEncoder().encode("12345");
//		System.out.println(encode);
		
	}
	
	 @Bean 
	  public PasswordEncoder passwordEncoder()
	  {
		  return new  BCryptPasswordEncoder();	 
	  }
}
