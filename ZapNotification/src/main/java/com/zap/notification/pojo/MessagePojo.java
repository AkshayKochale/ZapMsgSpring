package com.zap.notification.pojo;

import java.util.Date;

public class MessagePojo {

	
	String msgtitle;
	String msgcontent;
	String msgType;
	String priority;
	Integer clientid;
	Date senddatetime;
	
	public String getMsgtitle() {
		return msgtitle;
	}
	public void setMsgtitle(String msgtitle) {
		this.msgtitle = msgtitle;
	}
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Integer getClientid() {
		return clientid;
	}
	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}
	public Date getSenddatetime() {
		return senddatetime;
	}
	public void setSenddatetime(Date senddatetime) {
		this.senddatetime = senddatetime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	@Override
	public String toString() {
		return "MessagePojo [msgtitle=" + msgtitle + ", msgcontent=" + msgcontent + ", msgType=" + msgType
				+ ", priority=" + priority + ", clientid=" + clientid + ", senddatetime=" + senddatetime + "]";
	}
	
	
	
	
	
}
