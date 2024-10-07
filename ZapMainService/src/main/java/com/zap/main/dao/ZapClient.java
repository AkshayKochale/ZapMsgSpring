package com.zap.main.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ZapClient {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer zapclientid;
	
	@OneToOne(fetch = FetchType.LAZY)
	private ZapUser user;
	
	@Temporal(TemporalType.DATE)
	private Date created;
	
	private String clientname;
	
	private String clientemail;
	
	private String clientphone;
	
	private String platformtoken;
	 
	private boolean isactive;

	private String clienttype;	
	
	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public Integer getZapclientid() {
		return zapclientid;
	}

	public void setZapclientid(Integer zapclientid) {
		this.zapclientid = zapclientid;
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

	public String getClienttype() {
		return clienttype;
	}

	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}

	@Override
	public String toString() {
		return "ZapClient [zapclientid=" + zapclientid + ", user=" + user + ", created=" + created + ", clientname="
				+ clientname + ", clientemail=" + clientemail + ", clientphone=" + clientphone + ", platformtoken="
				+ platformtoken + ", isactive=" + isactive + ", clienttype=" + clienttype + "]";
	}
	
	

	
	
	
	
}
