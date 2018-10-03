package com.spring.starter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

@Entity
@Table(name="jwt_tokens")
public class JwtTokens {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int token_id;
	private Date issuedTime;
	private Date rejectTime;
	private int isValied;
	@NonNull
	@NotEmpty
	private String token;
	
	@OneToOne
	@JoinColumn(name="staffId")
	private StaffUser staffUser;
	
	@OneToOne 
	@JoinColumn(name = "loginLogsId")
	private Loginlogs loginlogs;
	
	public JwtTokens() {
		super();
	}
	
	public JwtTokens(int token_id, Date issuedTime, Date rejectTime, int isValied, @NotEmpty String token,
			StaffUser staffUser, Loginlogs loginlogs) {
		super();
		this.token_id = token_id;
		this.issuedTime = issuedTime;
		this.rejectTime = rejectTime;
		this.isValied = isValied;
		this.token = token;
		this.staffUser = staffUser;
		this.loginlogs = loginlogs;
	}

	public JwtTokens(int token_id, Date issuedTime, Date rejectTime, int isValied, String token, Loginlogs loginlogs) {
		super();
		this.token_id = token_id;
		this.issuedTime = issuedTime;
		this.rejectTime = rejectTime;
		this.isValied = isValied;
		this.token = token;
		this.loginlogs = loginlogs;
	}
	public JwtTokens(Date issuedTime, Date rejectTime, int isValied, String token) {
		super();
		this.issuedTime = issuedTime;
		this.rejectTime = rejectTime;
		this.isValied = isValied;
		this.token = token;
	}
	public JwtTokens(int token_id, Date issuedTime, Date rejectTime, int isValied, String token) {
		super();
		this.token_id = token_id;
		this.issuedTime = issuedTime;
		this.rejectTime = rejectTime;
		this.isValied = isValied;
		this.token = token;
	}
	public int getToken_id() {
		return token_id;
	}
	public void setToken_id(int token_id) {
		this.token_id = token_id;
	}
	public Date getIssuedTime() {
		return issuedTime;
	}
	public void setIssuedTime(Date issuedTime) {
		this.issuedTime = issuedTime;
	}
	public Date getRejectTime() {
		return rejectTime;
	}
	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}
	public int getIsValied() {
		return isValied;
	}
	public void setIsValied(int isValied) {
		this.isValied = isValied;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Loginlogs getLoginlogs() {
		return loginlogs;
	}
	public void setLoginlogs(Loginlogs loginlogs) {
		this.loginlogs = loginlogs;
	}
	public StaffUser getStaffUser() {
		return staffUser;
	}
	public void setStaffUser(StaffUser staffUser) {
		this.staffUser = staffUser;
	}
	
	
}
