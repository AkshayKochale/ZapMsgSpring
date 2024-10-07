package com.zap.authentication.pojo;

public class GlobalInputPojo 
{

	String token;
	String username;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "GlobalInputPojo [token=" + token + ", username=" + username + "]";
	}
	
	
	
}
