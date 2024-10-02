package com.zap.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.zap.authentication.service.ZapUserDetailsService;

@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter
{
	
	  @Autowired 
	  ZapUserDetailsService ZapUserDetailsService;
	  
	  @Autowired
	  OauthSuccessHandler filter;
	  
	  @Autowired
	    private JwtFilter jwtFilter;
	  
	  @Autowired
	  PasswordEncoder passwordEncoder;
	  
	  @Override 
	  protected void configure(HttpSecurity http) throws Exception
	  {
	  
		  http
		  .csrf().disable()
		  .cors().and()
		  .authorizeRequests().antMatchers("/public/**","/login/**","/oauth2/**").permitAll()
		  .anyRequest().authenticated()
		  .and().httpBasic()
		  .and().oauth2Login().loginPage("/") .successHandler(filter).failureUrl("/login?error=true") 
		  .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		  .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	  }
	  
	  @Override 
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	  {
		  auth.userDetailsService(ZapUserDetailsService).passwordEncoder(
				  passwordEncoder); 
	  }
	  
	 
	 
	  @Bean
	   public CorsFilter corsFilter()
	  {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowCredentials(true);
	        config.addAllowedOriginPattern("*"); 
	        config.addAllowedHeader("*");
	        config.addAllowedMethod("*");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }
	  
	  @Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
	  
	  @Bean
	  public RestTemplate restTemplate() 
	  {
		  return new RestTemplate();
	  } 
}
