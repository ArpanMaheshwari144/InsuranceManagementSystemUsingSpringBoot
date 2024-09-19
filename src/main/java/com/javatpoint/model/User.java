package com.javatpoint.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid")
	private int userid;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "email_sent")
	private boolean email_sent;
	
	@Column(name = "last_login")
	private Timestamp last_login;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int userid, String username, String email,String password) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getUserId() {
		return userid;
	}

	public void setUserId(int userid) {
		this.userid = userid;
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

	public boolean getEmail_sent() {
		return email_sent;
	}

	public void setEmail_sent(boolean email_sent) {
		this.email_sent = email_sent;
	}

	public Timestamp  getLast_login() {
		return last_login;
	}

	public void setLast_login(Timestamp timestamp) {
		this.last_login = timestamp;
	}
	
	

}
