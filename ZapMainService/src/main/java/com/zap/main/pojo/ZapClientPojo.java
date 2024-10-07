package com.zap.main.pojo;

import java.util.Date;

import com.zap.main.dao.ZapUser;

public class ZapClientPojo {

	private Integer clientid;

	private ZapUser user;

	private Date created;
	
	private String clientname;
	
	private String clientemail;
	
	private String clientphone;
	
	private String platformtoken;
	 
	private boolean isactive;
	
	private String token;
	
	private String clientType;
	

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public ZapUser getUser() {
		return user;
	}

	public void setUser(ZapUser user) {
		this.user = user;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public String getClientemail() {
		return clientemail;
	}

	public void setClientemail(String clientemail) {
		this.clientemail = clientemail;
	}

	public String getClientphone() {
		return clientphone;
	}

	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}

	public String getPlatformtoken() {
		return platformtoken;
	}

	public void setPlatformtoken(String platformtoken) {
		this.platformtoken = platformtoken;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "ZapClientPojo [clientid=" + clientid + ", user=" + user + ", created=" + created + ", clientname="
				+ clientname + ", clientemail=" + clientemail + ", clientphone=" + clientphone + ", platformtoken="
				+ platformtoken + ", isactive=" + isactive + ", token=" + token + ", clientType=" + clientType + "]";
	}

	
	
	
	
	
	
}


