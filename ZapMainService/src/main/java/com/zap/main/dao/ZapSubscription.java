package com.zap.main.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ZapSubscription 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer subscriptionid;
	
	@OneToOne
	private ZapUser user;
	
	private Integer credits_used;
	
	private Integer credits_avaliable;
	
	@Temporal(value = TemporalType.DATE)
	private Date subscription_start_Date;
	
	@Temporal(value = TemporalType.DATE)
	private Date subscription_end_Date;

	public Integer getSubscriptionid() {
		return subscriptionid;
	}

	public void setSubscriptionid(Integer subscriptionid) {
		this.subscriptionid = subscriptionid;
	}

	public ZapUser getUser() {
		return user;
	}

	public void setUser(ZapUser user) {
		this.user = user;
	}

	public Integer getCredits_used() {
		return credits_used;
	}

	public void setCredits_used(Integer credits_used) {
		this.credits_used = credits_used;
	}

	public Integer getCredits_avaliable() {
		return credits_avaliable;
	}

	public void setCredits_avaliable(Integer credits_avaliable) {
		this.credits_avaliable = credits_avaliable;
	}

	public Date getSubscription_start_Date() {
		return subscription_start_Date;
	}

	public void setSubscription_start_Date(Date subscription_start_Date) {
		this.subscription_start_Date = subscription_start_Date;
	}

	public Date getSubscription_end_Date() {
		return subscription_end_Date;
	}

	public void setSubscription_end_Date(Date subscription_end_Date) {
		this.subscription_end_Date = subscription_end_Date;
	}

	@Override
	public String toString() {
		return "ZapSubscription [subscriptionid=" + subscriptionid + ", user=" + user + ", credits_used=" + credits_used
				+ ", credits_avaliable=" + credits_avaliable + ", subscription_start_Date=" + subscription_start_Date
				+ ", subscription_end_Date=" + subscription_end_Date + "]";
	}
	
	
	
}
