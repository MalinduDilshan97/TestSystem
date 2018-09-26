package com.spring.starter.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name="deactivate_log")
public class DeactivateLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deactivateId;
	@ManyToOne
	@JoinColumn(name="deactivateStaffId")
	private StaffUser deactivateBy;
	@ManyToOne
	@JoinColumn(name="deactivateWhoID")
	private StaffUser deactivateWho;
	@NonNull
	private Date deactivateDate;
	@NonNull
	private String deactivateIp;
	@NonNull
	private String message;
	
	public DeactivateLog() {
		super();
	}
	
	public DeactivateLog(int deactivateId, StaffUser deactivateBy, StaffUser deactivateWho, Date deactivateDate,
			String deactivateIp, String message) {
		super();
		this.deactivateId = deactivateId;
		this.deactivateBy = deactivateBy;
		this.deactivateWho = deactivateWho;
		this.deactivateDate = deactivateDate;
		this.deactivateIp = deactivateIp;
		this.message = message;
	}

	public int getDeactivateId() {
		return deactivateId;
	}
	public void setDeactivateId(int deactivateId) {
		this.deactivateId = deactivateId;
	}
	public StaffUser getDeactivateBy() {
		return deactivateBy;
	}
	public void setDeactivateBy(StaffUser deactivateBy) {
		this.deactivateBy = deactivateBy;
	}
	public Date getDeactivateDate() {
		return deactivateDate;
	}
	public void setDeactivateDate(Date deactivateDate) {
		this.deactivateDate = deactivateDate;
	}
	public String getDeactivateIp() {
		return deactivateIp;
	}
	public void setDeactivateIp(String deactivateIp) {
		this.deactivateIp = deactivateIp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public StaffUser getDeactivateWho() {
		return deactivateWho;
	}

	public void setDeactivateWho(StaffUser deactivateWho) {
		this.deactivateWho = deactivateWho;
	}
	
	
}
