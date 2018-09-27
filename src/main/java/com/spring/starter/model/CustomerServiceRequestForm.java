package com.spring.starter.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer_service_request_form")
public class CustomerServiceRequestForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int CustomerServiceRequestFormId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId")
	private Customer customer;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "service_customer_id")
	private List<CustomerServiceRequest> customerServiceRequest;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staffHandledId")
	private StaffUser staffUser;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerServiceRequestFormId")
	private List<AtmOrDebit> atmOrDebit;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerServiceRequestFormId")
	private List<ChangeMailingMailModel> changeMailingMail;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerServiceRequestFormId")
	private List<ChangePermanentMail> changePermanentMail;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerServiceRequestFormId")
	private List<SMSAlertsForCreditCard> smsAlertsForCreditCard;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerServiceRequestFormId")
	private List<ChangeIdentificationForm> changeIdentification;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerServiceRequestFormId")
	private List<ChangeTelephoneNoOrEmail> changeTelephoneNoOrEmail;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerServiceRequestFormId")
	private List<BankStatementOrPassbookForm> bankStatementOrPassbookForm;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerServiceRequestFormId")
	private List<FdOrCd> fdOrCd;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "CustomerServiceRequestFormId")
	private List<StopPaymentOrRevocationPaymentForm> stopPaymentOrRevocationPaymentForm;

	private String otherRequests;

	private boolean status;

	public CustomerServiceRequestForm() {
		super();
	}

	public CustomerServiceRequestForm(int customerServiceRequestFormId, Customer customer,
			List<CustomerServiceRequest> customerServiceRequest, StaffUser staffUser, List<AtmOrDebit> atmOrDebit,
			List<ChangeMailingMailModel> changeMailingMail, List<ChangePermanentMail> changePermanentMail,
			List<SMSAlertsForCreditCard> smsAlertsForCreditCard, List<ChangeIdentificationForm> changeIdentification,
			List<ChangeTelephoneNoOrEmail> changeTelephoneNoOrEmail,
			List<BankStatementOrPassbookForm> bankStatementOrPassbookForm, List<FdOrCd> fdOrCd,
			List<StopPaymentOrRevocationPaymentForm> stopPaymentOrRevocationPaymentForm, String otherRequests,
			boolean status) {
		super();
		CustomerServiceRequestFormId = customerServiceRequestFormId;
		this.customer = customer;
		this.customerServiceRequest = customerServiceRequest;
		this.staffUser = staffUser;
		this.atmOrDebit = atmOrDebit;
		this.changeMailingMail = changeMailingMail;
		this.changePermanentMail = changePermanentMail;
		this.smsAlertsForCreditCard = smsAlertsForCreditCard;
		this.changeIdentification = changeIdentification;
		this.changeTelephoneNoOrEmail = changeTelephoneNoOrEmail;
		this.bankStatementOrPassbookForm = bankStatementOrPassbookForm;
		this.fdOrCd = fdOrCd;
		this.stopPaymentOrRevocationPaymentForm = stopPaymentOrRevocationPaymentForm;
		this.otherRequests = otherRequests;
		this.status = status;
	}



	public int getCustomerServiceRequestFormId() {
		return CustomerServiceRequestFormId;
	}

	public void setCustomerServiceRequestFormId(int customerServiceRequestFormId) {
		CustomerServiceRequestFormId = customerServiceRequestFormId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CustomerServiceRequest> getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(List<CustomerServiceRequest> customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}

	public StaffUser getStaffUser() {
		return staffUser;
	}

	public void setStaffUser(StaffUser staffUser) {
		this.staffUser = staffUser;
	}

	public List<AtmOrDebit> getAtmOrDebit() {
		return atmOrDebit;
	}

	public void setAtmOrDebit(List<AtmOrDebit> atmOrDebit) {
		this.atmOrDebit = atmOrDebit;
	}

	public List<ChangeMailingMailModel> getChangeMailingMail() {
		return changeMailingMail;
	}

	public void setChangeMailingMail(List<ChangeMailingMailModel> changeMailingMail) {
		this.changeMailingMail = changeMailingMail;
	}

	public List<ChangePermanentMail> getChangePermanentMail() {
		return changePermanentMail;
	}

	public void setChangePermanentMail(List<ChangePermanentMail> changePermanentMail) {
		this.changePermanentMail = changePermanentMail;
	}

	public List<SMSAlertsForCreditCard> getSmsAlertsForCreditCard() {
		return smsAlertsForCreditCard;
	}

	public void setSmsAlertsForCreditCard(List<SMSAlertsForCreditCard> smsAlertsForCreditCard) {
		this.smsAlertsForCreditCard = smsAlertsForCreditCard;
	}

	public List<ChangeIdentificationForm> getChangeIdentification() {
		return changeIdentification;
	}

	public void setChangeIdentification(List<ChangeIdentificationForm> changeIdentification) {
		this.changeIdentification = changeIdentification;
	}

	public List<ChangeTelephoneNoOrEmail> getChangeTelephoneNoOrEmail() {
		return changeTelephoneNoOrEmail;
	}

	public void setChangeTelephoneNoOrEmail(List<ChangeTelephoneNoOrEmail> changeTelephoneNoOrEmail) {
		this.changeTelephoneNoOrEmail = changeTelephoneNoOrEmail;
	}

	public List<BankStatementOrPassbookForm> getBankStatementOrPassbookForm() {
		return bankStatementOrPassbookForm;
	}

	public void setBankStatementOrPassbookForm(List<BankStatementOrPassbookForm> bankStatementOrPassbookForm) {
		this.bankStatementOrPassbookForm = bankStatementOrPassbookForm;
	}

	public List<FdOrCd> getFdOrCd() {
		return fdOrCd;
	}

	public void setFdOrCd(List<FdOrCd> fdOrCd) {
		this.fdOrCd = fdOrCd;
	}

	public List<StopPaymentOrRevocationPaymentForm> getStopPaymentOrRevocationPaymentForm() {
		return stopPaymentOrRevocationPaymentForm;
	}

	public void setStopPaymentOrRevocationPaymentForm(
			List<StopPaymentOrRevocationPaymentForm> stopPaymentOrRevocationPaymentForm) {
		this.stopPaymentOrRevocationPaymentForm = stopPaymentOrRevocationPaymentForm;
	}

	public String getOtherRequests() {
		return otherRequests;
	}

	public void setOtherRequests(String otherRequests) {
		this.otherRequests = otherRequests;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
