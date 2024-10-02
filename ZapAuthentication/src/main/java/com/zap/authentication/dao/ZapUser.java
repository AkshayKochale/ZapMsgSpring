package com.zap.authentication.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity
public class ZapUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer Id;
	
	 @Column(nullable = false, unique = true)
	 private String username;

	 @Column(nullable = false)
	 private String password;

	  @Column(nullable = false)
	  private String email;

	  
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ZapUser [Id=" + Id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}

	  
	  
}
