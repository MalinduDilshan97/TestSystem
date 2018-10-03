package com.spring.starter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="login_logs")
public class Loginlogs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loginLogsId;
	
	private Date loginTime;
	private String status;
	private String loginIp;
	private String logUsername;
	@OneToOne
	@JoinColumn(name="token_id")
	private JwtTokens jwtTokens;
	
	public Loginlogs() {
		super();
	}

	public Loginlogs(Date loginTime, String status, String loginIp, String logUsername, JwtTokens jwtTokens) {
		super();
		this.loginTime = loginTime;
		this.status = status;
		this.loginIp = loginIp;
		this.logUsername = logUsername;
		this.jwtTokens = jwtTokens;
	}

	public Loginlogs(int loginLogsId, Date loginTime, String status, String loginIp, String logUsername,
			JwtTokens jwtTokens) {
		super();
		this.loginLogsId = loginLogsId;
		this.loginTime = loginTime;
		this.status = status;
		this.loginIp = loginIp;
		this.logUsername = logUsername;
		this.jwtTokens = jwtTokens;
	}

	public int getLoginLogsId() {
		return loginLogsId;
	}

	public void setLoginLogsId(int loginLogsId) {
		this.loginLogsId = loginLogsId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLogUsername() {
		return logUsername;
	}

	public void setLogUsername(String logUsername) {
		this.logUsername = logUsername;
	}

	public JwtTokens getJwtTokens() {
		return jwtTokens;
	}

	public void setJwtTokens(JwtTokens jwtTokens) {
		this.jwtTokens = jwtTokens;
	}
	
}
