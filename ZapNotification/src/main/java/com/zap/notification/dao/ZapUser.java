package com.zap.notification.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Component;

@Component
@Entity
public class ZapUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer Id;
	
	 @Column
	 private String firstname;
	 
	 @Column
	 private String lastname;
	
	 @Column(nullable = false, unique = true)
	 private String username;

	 @Column(nullable = false)
	 private String password;

	  @Column(nullable = false)
	  private String email;
	  
	  @Temporal(TemporalType.DATE)
	  private Date accountcreateddate;
	  
	  @Column
	  private String country;
	  
	  @Column
	   private String phoneno;
	  
	  @Column
	   private String accountToken;
	  	
	  @Temporal(TemporalType.TIMESTAMP)
	  private Date tokengenearteddate;
	  
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
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getAccountToken() {
		return accountToken;
	}

	public void setAccountToken(String accountToken) {
		this.accountToken = accountToken;
	}

	public Date getAccountcreateddate() {
		return accountcreateddate;
	}

	public void setAccountcreateddate(Date accountcreateddate) {
		this.accountcreateddate = accountcreateddate;
	}

	public Date getTokengenearteddate() {
		return tokengenearteddate;
	}

	public void setTokengenearteddate(Date tokengenearteddate) {
		this.tokengenearteddate = tokengenearteddate;
	}

	@Override
	public String toString() {
		return "ZapUser [Id=" + Id + ", firstname=" + firstname + ", lastname=" + lastname + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", accountcreateddate=" + accountcreateddate
				+ ", country=" + country + ", phoneno=" + phoneno + ", accountToken=" + accountToken
				+ ", tokengenearteddate=" + tokengenearteddate + "]";
	}

	
	  
}
