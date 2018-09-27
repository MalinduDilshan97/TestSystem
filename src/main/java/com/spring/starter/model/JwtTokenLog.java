package com.spring.starter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.lang.NonNull;

@Entity
@Table(name="jwt_token_log")
public class JwtTokenLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tokenLogId;
	
	@NonNull
	@NotEmpty
	private String token;
	
	@NotNull
	private int userId;
	
	@NotNull
	private Date EnteredDate;

	@NotNull
	private String ip;

	public JwtTokenLog() {
		super();
	}

	public JwtTokenLog(int tokenLogId, @NotEmpty String token, @NotNull int userId, @NotNull Date enteredDate,
			@NotNull String ip) {
		super();
		this.tokenLogId = tokenLogId;
		this.token = token;
		this.userId = userId;
		EnteredDate = enteredDate;
		this.ip = ip;
	}

	public int getTokenLogId() {
		return tokenLogId;
	}

	public void setTokenLogId(int tokenLogId) {
		this.tokenLogId = tokenLogId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getEnteredDate() {
		return EnteredDate;
	}

	public void setEnteredDate(Date enteredDate) {
		EnteredDate = enteredDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
