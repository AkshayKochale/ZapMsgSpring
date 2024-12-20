package com.zap.main.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private ZapUser user;

    @ManyToOne
    private ZapClient client;

    @Temporal(TemporalType.DATE)
    private Date senddate;

    private String messagetype;
    
    private String message;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZapUser getUser() {
        return user;
    }

    public void setUser(ZapUser user) {
        this.user = user;
    }

    public ZapClient getClient() {
        return client;
    }

    public void setClient(ZapClient client) {
        this.client = client;
    }

    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    public String getMessagetype() {
        return messagetype;
    }

    public void setMessagetype(String messagetype) {
        this.messagetype = messagetype;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "History [id=" + id + ", user=" + user + ", client=" + client + ", senddate=" + senddate
				+ ", messagetype=" + messagetype + ", message=" + message + "]";
	}

    
}
