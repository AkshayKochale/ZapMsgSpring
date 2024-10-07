package com.zap.main.pojo;

public class GlobalInputPojo 
{

	String token;
	String username;
	Integer clientid;
	
	
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
	
	
	public Integer getClientid() {
		return clientid;
	}
	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}
	@Override
	public String toString() {
		return "GlobalInputPojo [token=" + token + ", username=" + username + ", clientid=" + clientid + "]";
	}


		
	
	
}
