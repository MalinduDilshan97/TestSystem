package com.spring.starter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "fd_or_cd")
public class FdOrCd {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int fdOrCdId; 
	
	private boolean withholdingTaxDeductionCertificate;
	
/*	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "fdCdNumbersId")
	private List<FdCdNumbers> fdCdNumbers;
	
	private Date maturityDate;
	
	private boolean otherFdOrCdDepositRelatedRequests;
	
	private String fdOrCdrequest;
	
	private boolean fdOrCallDepositCertificate;

	private String duplicatefdCdNumber;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;
	
	public FdOrCd() {
		super();
	}

	public FdOrCd(int fdOrCdId, boolean withholdingTacDeductionCertificate, List<FdCdNumbers> fdCdNumbers,
			Date maturityDate, boolean otherFdOrCdDepositRelatedRequests, String fdOrCdrequest,
			boolean fdOrCallDepositCertificate, CustomerServiceRequest customerServiceRequest) {
		super();
		this.fdOrCdId = fdOrCdId;
		this.withholdingTaxDeductionCertificate = withholdingTacDeductionCertificate;
		this.fdCdNumbers = fdCdNumbers;
		this.maturityDate = maturityDate;
		this.otherFdOrCdDepositRelatedRequests = otherFdOrCdDepositRelatedRequests;
		this.fdOrCdrequest = fdOrCdrequest;
		this.fdOrCallDepositCertificate = fdOrCallDepositCertificate;
		this.customerServiceRequest = customerServiceRequest;
	}

	public FdOrCd(int fdOrCdId, boolean withholdingTacDeductionCertificate, List<FdCdNumbers> fdCdNumbers,
			Date maturityDate, boolean otherFdOrCdDepositRelatedRequests, String fdOrCdrequest,
			boolean fdOrCallDepositCertificate) {
		super();
		this.fdOrCdId = fdOrCdId;
		this.withholdingTaxDeductionCertificate = withholdingTacDeductionCertificate;
		this.fdCdNumbers = fdCdNumbers;
		this.maturityDate = maturityDate;
		this.otherFdOrCdDepositRelatedRequests = otherFdOrCdDepositRelatedRequests;
		this.fdOrCdrequest = fdOrCdrequest;
		this.fdOrCallDepositCertificate = fdOrCallDepositCertificate;
	}

	public int getFdOrCdId() {
		return fdOrCdId;
	}

	public void setFdOrCdId(int fdOrCdId) {
		this.fdOrCdId = fdOrCdId;
	}

	public boolean isWithholdingTaxDeductionCertificate() {
		return withholdingTaxDeductionCertificate;
	}

	public void setWithholdingTaxDeductionCertificate(boolean withholdingTacDeductionCertificate) {
		this.withholdingTaxDeductionCertificate = withholdingTacDeductionCertificate;
	}

	public List<FdCdNumbers> getFdCdNumbers() {
		return fdCdNumbers;
	}

	public void setFdCdNumbers(List<FdCdNumbers> fdCdNumbers) {
		this.fdCdNumbers = fdCdNumbers;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public boolean isOtherFdOrCdDepositRelatedRequests() {
		return otherFdOrCdDepositRelatedRequests;
	}

	public void setOtherFdOrCdDepositRelatedRequests(boolean otherFdOrCdDepositRelatedRequests) {
		this.otherFdOrCdDepositRelatedRequests = otherFdOrCdDepositRelatedRequests;
	}

	public String getFdOrCdrequest() {
		return fdOrCdrequest;
	}

	public void setFdOrCdrequest(String fdOrCdrequest) {
		this.fdOrCdrequest = fdOrCdrequest;
	}

	public boolean isFdOrCallDepositCertificate() {
		return fdOrCallDepositCertificate;
	}

	public void setFdOrCallDepositCertificate(boolean fdOrCallDepositCertificate) {
		this.fdOrCallDepositCertificate = fdOrCallDepositCertificate;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}

	public String getDuplicatefdCdNumber() {
		return duplicatefdCdNumber;
	}

	public void setDuplicatefdCdNumber(String duplicatefdCdNumber) {
		this.duplicatefdCdNumber = duplicatefdCdNumber;
	}*/
}
