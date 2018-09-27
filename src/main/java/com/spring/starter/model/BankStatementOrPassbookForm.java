package com.spring.starter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.spring.starter.enums.BankStatementFrequency;
import com.spring.starter.enums.NatureOfStatement;

@Entity
@Table(name = "bank_statement_or_passbook_form")
public class BankStatementOrPassbookForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int BankStatementOrPassbookFormId;
	
	private boolean issueAccountStatementForTheBelowPeriod;
	private Date fromDate;
	private Date toDate;
	
	@Enumerated(EnumType.STRING)
	private NatureOfStatement natureOfStatement;
	
	private boolean passbookOrDuplicatePassbookRequest;

	
	private boolean activeOrCancelEStatementFacility;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerAccountNoId")
	private List<CustomerAccountNo> customerAccountNos;
	
	private String email;
	
	private boolean changeTheFrquency;
	
	@Enumerated(EnumType.STRING)
	private BankStatementFrequency bankStatementFrequency;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	public BankStatementOrPassbookForm() {
		super();
	}

	public BankStatementOrPassbookForm(int bankStatementOrPassbookFormId,
			boolean issueAccountStatementForTheBelowPeriod, Date fromDate, Date toDate,
			NatureOfStatement natureOfStatement, boolean passbookOrDuplicatePassbookRequest,
			boolean activeOrCancelEStatementFacility, List<CustomerAccountNo> customerAccountNos, String email,
			boolean changeTheFrquency, BankStatementFrequency bankStatementFrequency,
			CustomerServiceRequest customerServiceRequest) {
		super();
		BankStatementOrPassbookFormId = bankStatementOrPassbookFormId;
		this.issueAccountStatementForTheBelowPeriod = issueAccountStatementForTheBelowPeriod;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.natureOfStatement = natureOfStatement;
		this.passbookOrDuplicatePassbookRequest = passbookOrDuplicatePassbookRequest;
		this.activeOrCancelEStatementFacility = activeOrCancelEStatementFacility;
		this.customerAccountNos = customerAccountNos;
		this.email = email;
		this.changeTheFrquency = changeTheFrquency;
		this.bankStatementFrequency = bankStatementFrequency;
		this.customerServiceRequest = customerServiceRequest;
	}
	
	public BankStatementOrPassbookForm(int bankStatementOrPassbookFormId,
			boolean issueAccountStatementForTheBelowPeriod, Date fromDate, Date toDate,
			NatureOfStatement natureOfStatement, boolean passbookOrDuplicatePassbookRequest,
			boolean activeOrCancelEStatementFacility, List<CustomerAccountNo> customerAccountNos, String email,
			boolean changeTheFrquency, BankStatementFrequency bankStatementFrequency) {
		super();
		BankStatementOrPassbookFormId = bankStatementOrPassbookFormId;
		this.issueAccountStatementForTheBelowPeriod = issueAccountStatementForTheBelowPeriod;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.natureOfStatement = natureOfStatement;
		this.passbookOrDuplicatePassbookRequest = passbookOrDuplicatePassbookRequest;
		this.activeOrCancelEStatementFacility = activeOrCancelEStatementFacility;
		this.customerAccountNos = customerAccountNos;
		this.email = email;
		this.changeTheFrquency = changeTheFrquency;
		this.bankStatementFrequency = bankStatementFrequency;
	}

	public int getBankStatementOrPassbookFormId() {
		return BankStatementOrPassbookFormId;
	}

	public void setBankStatementOrPassbookFormId(int bankStatementOrPassbookFormId) {
		BankStatementOrPassbookFormId = bankStatementOrPassbookFormId;
	}

	public boolean isIssueAccountStatementForTheBelowPeriod() {
		return issueAccountStatementForTheBelowPeriod;
	}

	public void setIssueAccountStatementForTheBelowPeriod(boolean issueAccountStatementForTheBelowPeriod) {
		this.issueAccountStatementForTheBelowPeriod = issueAccountStatementForTheBelowPeriod;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public NatureOfStatement getNatureOfStatement() {
		return natureOfStatement;
	}

	public void setNatureOfStatement(NatureOfStatement natureOfStatement) {
		this.natureOfStatement = natureOfStatement;
	}

	public boolean isPassbookOrDuplicatePassbookRequest() {
		return passbookOrDuplicatePassbookRequest;
	}

	public void setPassbookOrDuplicatePassbookRequest(boolean passbookOrDuplicatePassbookRequest) {
		this.passbookOrDuplicatePassbookRequest = passbookOrDuplicatePassbookRequest;
	}

	public boolean isActiveOrCancelEStatementFacility() {
		return activeOrCancelEStatementFacility;
	}

	public void setActiveOrCancelEStatementFacility(boolean activeOrCancelEStatementFacility) {
		this.activeOrCancelEStatementFacility = activeOrCancelEStatementFacility;
	}

	public List<CustomerAccountNo> getCustomerAccountNos() {
		return customerAccountNos;
	}

	public void setCustomerAccountNos(List<CustomerAccountNo> customerAccountNos) {
		this.customerAccountNos = customerAccountNos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isChangeTheFrquency() {
		return changeTheFrquency;
	}

	public void setChangeTheFrquency(boolean changeTheFrquency) {
		this.changeTheFrquency = changeTheFrquency;
	}

	public BankStatementFrequency getBankStatementFrequency() {
		return bankStatementFrequency;
	}

	public void setBankStatementFrequency(BankStatementFrequency bankStatementFrequency) {
		this.bankStatementFrequency = bankStatementFrequency;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	} 
	
}
