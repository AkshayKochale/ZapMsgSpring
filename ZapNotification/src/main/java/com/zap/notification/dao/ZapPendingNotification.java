package com.zap.notification.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ZapPendingNotification 
{
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@ManyToOne
	ZapClient client;
	
	@Column
	String message;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date senddatetime;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date lastsenddatetime;
	
	@Column
	String priority;
	
	@Column
	Long maxLife;
	
	@Column
	Integer currentRetries;
	
	@Column
	Integer maxRetries;
	
	@Column 
	boolean  ack;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ZapClient getClient() {
		return client;
	}

	public void setClient(ZapClient client) {
		this.client = client;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSenddatetime() {
		return senddatetime;
	}

	public void setSenddatetime(Date senddatetime) {
		this.senddatetime = senddatetime;
	}

	public Date getLastsenddatetime() {
		return lastsenddatetime;
	}

	public void setLastsenddatetime(Date lastsenddatetime) {
		this.lastsenddatetime = lastsenddatetime;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Long getMaxLife() {
		return maxLife;
	}

	public void setMaxLife(Long maxLife) {
		this.maxLife = maxLife;
	}

	public Integer getCurrentRetries() {
		return currentRetries;
	}

	public void setCurrentRetries(Integer currentRetries) {
		this.currentRetries = currentRetries;
	}

	public Integer getMaxRetries() {
		return maxRetries;
	}

	public void setMaxRetries(Integer maxRetries) {
		this.maxRetries = maxRetries;
	}

	public boolean isAck() {
		return ack;
	}

	public void setAck(boolean ack) {
		this.ack = ack;
	}

	@Override
	public String toString() {
		return "ZapPendingNotification [id=" + id + ", client=" + client + ", message=" + message + ", senddatetime="
				+ senddatetime + ", lastsenddatetime=" + lastsenddatetime + ", priority=" + priority + ", maxLife="
				+ maxLife + ", currentRetries=" + currentRetries + ", maxRetries=" + maxRetries + ", ack=" + ack + "]";
	}
	
	
	
	
}
