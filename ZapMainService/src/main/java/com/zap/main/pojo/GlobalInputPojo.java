package com.zap.main.pojo;

import java.util.List;

public class GlobalInputPojo 
{

	String token;
	String username;
	Integer clientid;
	List<ZapClientPojo> clientpojo;
	String date;
	List<String> clients;
	String notificationtitle;
	String notificationmsg;
	String prompt;
	String emailsubject;
	String emailContent;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
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
	public List<ZapClientPojo> getClientpojo() {
		return clientpojo;
	}
	public void setClientpojo(List<ZapClientPojo> clientpojo) {
		this.clientpojo = clientpojo;
	}
	public List<String> getClients() {
		return clients;
	}
	public void setClients(List<String> clients) {
		this.clients = clients;
	}
	public String getNotificationtitle() {
		return notificationtitle;
	}
	public void setNotificationtitle(String notificationtitle) {
		this.notificationtitle = notificationtitle;
	}
	public String getNotificationmsg() {
		return notificationmsg;
	}
	public void setNotificationmsg(String notificationmsg) {
		this.notificationmsg = notificationmsg;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getEmailsubject() {
		return emailsubject;
	}
	public void setEmailsubject(String emailsubject) {
		this.emailsubject = emailsubject;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	@Override
	public String toString() {
		return "GlobalInputPojo [token=" + token + ", username=" + username + ", clientid=" + clientid + ", clientpojo="
				+ clientpojo + ", date=" + date + ", clients=" + clients + ", notificationtitle=" + notificationtitle
				+ ", notificationmsg=" + notificationmsg + ", prompt=" + prompt + ", emailsubject=" + emailsubject
				+ ", emailContent=" + emailContent + "]";
	}
	
	
	
	
	
}
