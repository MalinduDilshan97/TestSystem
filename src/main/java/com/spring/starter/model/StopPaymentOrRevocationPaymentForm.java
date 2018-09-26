package com.spring.starter.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stop_payment_or_revocation_payment_form")
public class StopPaymentOrRevocationPaymentForm {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int StopPaymentOrRevocationPaymentFormId;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "EffectOrRevokePaymentId")
	private List<EffectOrRevokePayment> EffectOrRevokePayment;
	
	private boolean effectTheStopPayments;
	
	private boolean revokestopPayment;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	public StopPaymentOrRevocationPaymentForm() {
		super();
	}

	public StopPaymentOrRevocationPaymentForm(int stopPaymentOrRevocationPaymentFormId,
			List<com.spring.starter.model.EffectOrRevokePayment> effectOrRevokePayment, boolean effectTheStopPayments,
			boolean revokestopPayment, CustomerServiceRequest customerServiceRequest) {
		super();
		StopPaymentOrRevocationPaymentFormId = stopPaymentOrRevocationPaymentFormId;
		EffectOrRevokePayment = effectOrRevokePayment;
		this.effectTheStopPayments = effectTheStopPayments;
		this.revokestopPayment = revokestopPayment;
		this.customerServiceRequest = customerServiceRequest;
	}

	public StopPaymentOrRevocationPaymentForm(int stopPaymentOrRevocationPaymentFormId,
			List<com.spring.starter.model.EffectOrRevokePayment> effectOrRevokePayment, boolean effectTheStopPayments,
			boolean revokestopPayment) {
		super();
		StopPaymentOrRevocationPaymentFormId = stopPaymentOrRevocationPaymentFormId;
		EffectOrRevokePayment = effectOrRevokePayment;
		this.effectTheStopPayments = effectTheStopPayments;
		this.revokestopPayment = revokestopPayment;
	}

	public int getStopPaymentOrRevocationPaymentFormId() {
		return StopPaymentOrRevocationPaymentFormId;
	}

	public void setStopPaymentOrRevocationPaymentFormId(int stopPaymentOrRevocationPaymentFormId) {
		StopPaymentOrRevocationPaymentFormId = stopPaymentOrRevocationPaymentFormId;
	}

	public List<EffectOrRevokePayment> getEffectOrRevokePayment() {
		return EffectOrRevokePayment;
	}

	public void setEffectOrRevokePayment(List<EffectOrRevokePayment> effectOrRevokePayment) {
		EffectOrRevokePayment = effectOrRevokePayment;
	}

	public boolean isEffectTheStopPayments() {
		return effectTheStopPayments;
	}

	public void setEffectTheStopPayments(boolean effectTheStopPayments) {
		this.effectTheStopPayments = effectTheStopPayments;
	}

	public boolean isRevokestopPayment() {
		return revokestopPayment;
	}

	public void setRevokestopPayment(boolean revokestopPayment) {
		this.revokestopPayment = revokestopPayment;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}
	
	
}
