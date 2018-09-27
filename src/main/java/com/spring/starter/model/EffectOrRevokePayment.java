package com.spring.starter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "effect_or_revoke_payment")
public class EffectOrRevokePayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int EffectOrRevokePaymentId;
	
	private String chequeNumber;
	
	private double value;
	
	private String payeeName;
	
	private Date dateOfCheque;
	
	private String reasonToStopPayment;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerAccountNoId")
	private List<CustomerAccountNo> customerAccountNos;

	public EffectOrRevokePayment() {
		super();
	}

	public EffectOrRevokePayment(int effectOrRevokePaymentId, String chequeNumber, double value, String payeeName,
			Date dateOfCheque, String reasonToStopPayment, List<CustomerAccountNo> customerAccountNos) {
		super();
		EffectOrRevokePaymentId = effectOrRevokePaymentId;
		this.chequeNumber = chequeNumber;
		this.value = value;
		this.payeeName = payeeName;
		this.dateOfCheque = dateOfCheque;
		this.reasonToStopPayment = reasonToStopPayment;
		this.customerAccountNos = customerAccountNos;
	}

	public int getEffectOrRevokePaymentId() {
		return EffectOrRevokePaymentId;
	}

	public void setEffectOrRevokePaymentId(int effectOrRevokePaymentId) {
		EffectOrRevokePaymentId = effectOrRevokePaymentId;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public Date getDateOfCheque() {
		return dateOfCheque;
	}

	public void setDateOfCheque(Date dateOfCheque) {
		this.dateOfCheque = dateOfCheque;
	}

	public String getReasonToStopPayment() {
		return reasonToStopPayment;
	}

	public void setReasonToStopPayment(String reasonToStopPayment) {
		this.reasonToStopPayment = reasonToStopPayment;
	}

	public List<CustomerAccountNo> getCustomerAccountNos() {
		return customerAccountNos;
	}

	public void setCustomerAccountNos(List<CustomerAccountNo> customerAccountNos) {
		this.customerAccountNos = customerAccountNos;
	}

}
