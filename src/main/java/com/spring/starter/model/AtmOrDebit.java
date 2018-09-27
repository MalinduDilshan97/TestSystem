package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="atm_or_debit")
public class AtmOrDebit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int atmOrDebitId;

	private boolean suspendDebitCard=false;
	
	private boolean reactivateDebitCard=false;
	
	private boolean canselAtmDebit=false;
	
	private boolean reissueAPin=false;
	
	private boolean pinCollectionBranch=false;
	
	private boolean pinPostToAddress=false;
	
	private boolean subscribeToSmsAlerts=false;
	
	private boolean incresePointOfSale=false;
	
	private boolean linkNewAccountToCard=false; 
	
	private boolean atBranch;
	
	private boolean postToAddress;
	
	private double incresingAmmount;
	
	private String mobileNumberForSmsAlert;
	
	private String primaryAccNo;
	
	private String secondaryAccNo;
	
	private String newPrimaryAccountNo;
	
	private String cardNumber;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="branchId")
	private NDBBranch branch;
	
	private String postAddress;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;
	
	public AtmOrDebit() {
		super();
	}

	public AtmOrDebit(int atmOrDebitId, boolean suspendDebitCard, boolean reactivateDebitCard, boolean canselAtmDebit,
			boolean reissueAPin, boolean pinCollectionBranch, boolean pinPostToAddress, boolean subscribeToSmsAlerts,
			boolean incresePointOfSale, boolean linkNewAccountToCard, boolean atBranch, boolean postToAddress,
			double incresingAmmount, String mobileNumberForSmsAlert, String primaryAccNo, String secondaryAccNo,
			String newPrimaryAccountNo, String cardNumber, NDBBranch branch, String postAddress,
			CustomerServiceRequest customerServiceRequest) {
		super();
		this.atmOrDebitId = atmOrDebitId;
		this.suspendDebitCard = suspendDebitCard;
		this.reactivateDebitCard = reactivateDebitCard;
		this.canselAtmDebit = canselAtmDebit;
		this.reissueAPin = reissueAPin;
		this.pinCollectionBranch = pinCollectionBranch;
		this.pinPostToAddress = pinPostToAddress;
		this.subscribeToSmsAlerts = subscribeToSmsAlerts;
		this.incresePointOfSale = incresePointOfSale;
		this.linkNewAccountToCard = linkNewAccountToCard;
		this.atBranch = atBranch;
		this.postToAddress = postToAddress;
		this.incresingAmmount = incresingAmmount;
		this.mobileNumberForSmsAlert = mobileNumberForSmsAlert;
		this.primaryAccNo = primaryAccNo;
		this.secondaryAccNo = secondaryAccNo;
		this.newPrimaryAccountNo = newPrimaryAccountNo;
		this.cardNumber = cardNumber;
		this.branch = branch;
		this.postAddress = postAddress;
		this.customerServiceRequest = customerServiceRequest;
	}


	public AtmOrDebit(int atmOrDebitId, boolean suspendDebitCard, boolean reactivateDebitCard, boolean canselAtmDebit,
			boolean reissueAPin, boolean pinCollectionBranch, boolean pinPostToAddress, boolean subscribeToSmsAlerts,
			boolean incresePointOfSale, boolean linkNewAccountToCard, double incresingAmmount,
			String mobileNumberForSmsAlert, String primaryAccNo, String secondaryAccNo, String cardNumber,
			NDBBranch branch, String postAddress, CustomerServiceRequest customerServiceRequest) {
		super();
		this.atmOrDebitId = atmOrDebitId;
		this.suspendDebitCard = suspendDebitCard;
		this.reactivateDebitCard = reactivateDebitCard;
		this.canselAtmDebit = canselAtmDebit;
		this.reissueAPin = reissueAPin;
		this.pinCollectionBranch = pinCollectionBranch;
		this.pinPostToAddress = pinPostToAddress;
		this.subscribeToSmsAlerts = subscribeToSmsAlerts;
		this.incresePointOfSale = incresePointOfSale;
		this.linkNewAccountToCard = linkNewAccountToCard;
		this.incresingAmmount = incresingAmmount;
		this.mobileNumberForSmsAlert = mobileNumberForSmsAlert;
		this.primaryAccNo = primaryAccNo;
		this.secondaryAccNo = secondaryAccNo;
		this.cardNumber = cardNumber;
		this.branch = branch;
		this.postAddress = postAddress;
		this.customerServiceRequest = customerServiceRequest;
	}

	public AtmOrDebit(boolean suspendDebitCard, boolean reactivateDebitCard, boolean canselAtmDebit,
			boolean reissueAPin, boolean pinCollectionBranch, boolean pinPostToAddress, boolean subscribeToSmsAlerts,
			boolean incresePointOfSale, boolean linkNewAccountToCard, double incresingAmmount,
			String mobileNumberForSmsAlert, String primaryAccNo, String secondaryAccNo, String cardNumber,
			NDBBranch branch, String postAddress) {
		super();
		this.suspendDebitCard = suspendDebitCard;
		this.reactivateDebitCard = reactivateDebitCard;
		this.canselAtmDebit = canselAtmDebit;
		this.reissueAPin = reissueAPin;
		this.pinCollectionBranch = pinCollectionBranch;
		this.pinPostToAddress = pinPostToAddress;
		this.subscribeToSmsAlerts = subscribeToSmsAlerts;
		this.incresePointOfSale = incresePointOfSale;
		this.linkNewAccountToCard = linkNewAccountToCard;
		this.incresingAmmount = incresingAmmount;
		this.mobileNumberForSmsAlert = mobileNumberForSmsAlert;
		this.primaryAccNo = primaryAccNo;
		this.secondaryAccNo = secondaryAccNo;
		this.cardNumber = cardNumber;
		this.branch = branch;
		this.postAddress = postAddress;
	}
	
	public AtmOrDebit(int atmOrDebitId, boolean suspendDebitCard, boolean reactivateDebitCard, boolean canselAtmDebit,
			boolean reissueAPin, boolean pinCollectionBranch, boolean pinPostToAddress, boolean subscribeToSmsAlerts,
			boolean incresePointOfSale, boolean linkNewAccountToCard, double incresingAmmount,
			String mobileNumberForSmsAlert, String primaryAccNo, String secondaryAccNo, String cardNumber,
			NDBBranch branch, String postAddress) {
		super();
		this.atmOrDebitId = atmOrDebitId;
		this.suspendDebitCard = suspendDebitCard;
		this.reactivateDebitCard = reactivateDebitCard;
		this.canselAtmDebit = canselAtmDebit;
		this.reissueAPin = reissueAPin;
		this.pinCollectionBranch = pinCollectionBranch;
		this.pinPostToAddress = pinPostToAddress;
		this.subscribeToSmsAlerts = subscribeToSmsAlerts;
		this.incresePointOfSale = incresePointOfSale;
		this.linkNewAccountToCard = linkNewAccountToCard;
		this.incresingAmmount = incresingAmmount;
		this.mobileNumberForSmsAlert = mobileNumberForSmsAlert;
		this.primaryAccNo = primaryAccNo;
		this.secondaryAccNo = secondaryAccNo;
		this.cardNumber = cardNumber;
		this.branch = branch;
		this.postAddress = postAddress;
	}
	
	

	public String getNewPrimaryAccountNo() {
		return newPrimaryAccountNo;
	}

	public void setNewPrimaryAccountNo(String newPrimaryAccountNo) {
		this.newPrimaryAccountNo = newPrimaryAccountNo;
	}

	public int getAtmOrDebitId() {
		return atmOrDebitId;
	}

	public void setAtmOrDebitId(int atmOrDebitId) {
		this.atmOrDebitId = atmOrDebitId;
	}

	public boolean isSuspendDebitCard() {
		return suspendDebitCard;
	}

	public void setSuspendDebitCard(boolean suspendDebitCard) {
		this.suspendDebitCard = suspendDebitCard;
	}

	public boolean isReactivateDebitCard() {
		return reactivateDebitCard;
	}

	public void setReactivateDebitCard(boolean reactivateDebitCard) {
		this.reactivateDebitCard = reactivateDebitCard;
	}

	public boolean isCanselAtmDebit() {
		return canselAtmDebit;
	}

	public void setCanselAtmDebit(boolean canselAtmDebit) {
		this.canselAtmDebit = canselAtmDebit;
	}

	public boolean isReissueAPin() {
		return reissueAPin;
	}

	public void setReissueAPin(boolean reissueAPin) {
		this.reissueAPin = reissueAPin;
	}

	public boolean isPinCollectionBranch() {
		return pinCollectionBranch;
	}

	public void setPinCollectionBranch(boolean pinCollectionBranch) {
		this.pinCollectionBranch = pinCollectionBranch;
	}

	public boolean isPinPostToAddress() {
		return pinPostToAddress;
	}

	public void setPinPostToAddress(boolean pinPostToAddress) {
		this.pinPostToAddress = pinPostToAddress;
	}

	public boolean isSubscribeToSmsAlerts() {
		return subscribeToSmsAlerts;
	}

	public void setSubscribeToSmsAlerts(boolean subscribeToSmsAlerts) {
		this.subscribeToSmsAlerts = subscribeToSmsAlerts;
	}

	public boolean isIncresePointOfSale() {
		return incresePointOfSale;
	}

	public void setIncresePointOfSale(boolean incresePointOfSale) {
		this.incresePointOfSale = incresePointOfSale;
	}

	public boolean isLinkNewAccountToCard() {
		return linkNewAccountToCard;
	}

	public void setLinkNewAccountToCard(boolean linkNewAccountToCard) {
		this.linkNewAccountToCard = linkNewAccountToCard;
	}

	public double getIncresingAmmount() {
		return incresingAmmount;
	}

	public void setIncresingAmmount(double incresingAmmount) {
		this.incresingAmmount = incresingAmmount;
	}

	public String getMobileNumberForSmsAlert() {
		return mobileNumberForSmsAlert;
	}

	public void setMobileNumberForSmsAlert(String mobileNumberForSmsAlert) {
		this.mobileNumberForSmsAlert = mobileNumberForSmsAlert;
	}

	public String getPrimaryAccNo() {
		return primaryAccNo;
	}

	public void setPrimaryAccNo(String primaryAccNo) {
		this.primaryAccNo = primaryAccNo;
	}

	public String getSecondaryAccNo() {
		return secondaryAccNo;
	}

	public void setSecondaryAccNo(String secondaryAccNo) {
		this.secondaryAccNo = secondaryAccNo;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public NDBBranch getBranch() {
		return branch;
	}

	public void setBranch(NDBBranch branch) {
		this.branch = branch;
	}

	public String getPostAddress() {
		return postAddress;
	}

	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}

	public boolean isAtBranch() {
		return atBranch;
	}

	public void setAtBranch(boolean atBranch) {
		this.atBranch = atBranch;
	}

	public boolean isPostToAddress() {
		return postToAddress;
	}

	public void setPostToAddress(boolean postToAddress) {
		this.postToAddress = postToAddress;
	}
	
	
}
