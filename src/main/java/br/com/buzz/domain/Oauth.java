package br.com.buzz.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Oauth implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 1300)
	private String access_token;
	private Date expires_in;
	
	public String getAccess_token() {
		return access_token;
	}
	
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	public Date getExpires_in() {
		return expires_in;
	}
	
	public void setExpires_in(Date expires_in) {
		this.expires_in = expires_in;
	}

	public Oauth(Integer id, String access_token, Date expires_in) {
		super();
		this.id = id;
		this.access_token = access_token;
		this.expires_in = expires_in;
	}

	public Oauth() {
		super();
	}
	
}
