package com.spring.starter.DTO;

import java.io.Serializable;

public class LoginDTO implements Serializable {

	public LoginDTO() {
	}

	private String username;
	private String password;
	
	public LoginDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	
}