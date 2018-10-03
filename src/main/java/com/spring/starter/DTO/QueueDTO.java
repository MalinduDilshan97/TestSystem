package com.spring.starter.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

public class QueueDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4007916497960366103L;

	private String name;

	private String mobileNo;
	
	private int serviceRequestId;

	public QueueDTO() {
		super();
	}

	public QueueDTO(String name, String mobileNo, int serviceRequestId) {
		super();
		this.name = name;
		this.mobileNo = mobileNo;
		this.serviceRequestId = serviceRequestId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public int getServiceRequestId() {
		return serviceRequestId;
	}

	public void setServiceRequestId(int serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
