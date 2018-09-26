package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fd_cd_numbers")
public class FdCdNumbers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int FdCdNumbersId;
	
	private String fdCdNumber;

	public FdCdNumbers() {
		super();
	}

	public FdCdNumbers(int fdCdNumbersId, String fdCdNumber) {
		super();
		FdCdNumbersId = fdCdNumbersId;
		this.fdCdNumber = fdCdNumber;
	}

	public int getFdCdNumbersId() {
		return FdCdNumbersId;
	}

	public void setFdCdNumbersId(int fdCdNumbersId) {
		FdCdNumbersId = fdCdNumbersId;
	}

	public String getFdCdNumber() {
		return fdCdNumber;
	}

	public void setFdCdNumber(String fdCdNumber) {
		this.fdCdNumber = fdCdNumber;
	}
	
}
