package com.zap.authentication.service;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zap.authentication.dao.ZapUser;
import com.zap.authentication.repo.ZapUserRepo;

@Service
public class ZapUserDetailsService implements UserDetailsService
{

	
	  @Autowired ZapUserRepo repo;
	  
	  @Override public UserDetails loadUserByUsername(String username) throws
	  UsernameNotFoundException {
	  
	  ZapUser user = repo.findByUsername(username);
	  
	  return new User(user.getUsername(),user.getPassword(),getAuthorities(user));
	  }
	  
	  private Collection<? extends GrantedAuthority> getAuthorities(ZapUser user) {
	  
	  SimpleGrantedAuthority authority=new SimpleGrantedAuthority("Admin");
	  
	  return Arrays.asList(authority); }
	 

}
