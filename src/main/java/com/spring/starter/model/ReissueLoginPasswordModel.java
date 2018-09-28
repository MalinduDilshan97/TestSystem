package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="reissue_login_password_model")
public class ReissueLoginPasswordModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reissueLoginPasswordModelId;
	
	@NotNull
	@NotEmpty
	private String bankingUserId;
	
	private boolean atBranch = false;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="branchId")
	private NDBBranch branch;
	
	private boolean postToCorrespondenceAddress = false;
	
	private boolean postToAddress = false;
	
	private String addresss;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	public ReissueLoginPasswordModel() {
		super();
	}

	public ReissueLoginPasswordModel(int reissueLoginPasswordModelId, @NotNull @NotEmpty String bankingUserId,
			boolean atBranch, NDBBranch branch, boolean postToCorrespondenceAddress, boolean postToAddress,
			String addresss, CustomerServiceRequest customerServiceRequest) {
		super();
		this.reissueLoginPasswordModelId = reissueLoginPasswordModelId;
		this.bankingUserId = bankingUserId;
		this.atBranch = atBranch;
		this.branch = branch;
		this.postToCorrespondenceAddress = postToCorrespondenceAddress;
		this.postToAddress = postToAddress;
		this.addresss = addresss;
		this.customerServiceRequest = customerServiceRequest;
	}

	public ReissueLoginPasswordModel(int reissueLoginPasswordModelId, @NotNull @NotEmpty String bankingUserId,
			boolean atBranch, NDBBranch branch, boolean postToCorrespondenceAddress, String addresss) {
		super();
		this.reissueLoginPasswordModelId = reissueLoginPasswordModelId;
		this.bankingUserId = bankingUserId;
		this.atBranch = atBranch;
		this.branch = branch;
		this.postToCorrespondenceAddress = postToCorrespondenceAddress;
		this.addresss = addresss;
	}

	public void setBranch(NDBBranch branch) {
		this.branch = branch;
	}

	public int getReissueLoginPasswordModelId() {
		return reissueLoginPasswordModelId;
	}

	public void setReissueLoginPasswordModelId(int reissueLoginPasswordModelId) {
		this.reissueLoginPasswordModelId = reissueLoginPasswordModelId;
	}

	public String getBankingUserId() {
		return bankingUserId;
	}

	public void setBankingUserId(String bankingUserId) {
		this.bankingUserId = bankingUserId;
	}

	public boolean isAtBranch() {
		return atBranch;
	}

	public void setAtBranch(boolean atBranch) {
		this.atBranch = atBranch;
	}

	public boolean isPostToCorrespondenceAddress() {
		return postToCorrespondenceAddress;
	}

	public void setPostToCorrespondenceAddress(boolean postToCorrespondenceAddress) {
		this.postToCorrespondenceAddress = postToCorrespondenceAddress;
	}

	public String getAddresss() {
		return addresss;
	}

	public void setAddresss(String addresss) {
		this.addresss = addresss;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}

	public NDBBranch getBranch() {
		return branch;
	}

	public boolean isPostToAddress() {
		return postToAddress;
	}

	public void setPostToAddress(boolean postToAddress) {
		this.postToAddress = postToAddress;
	} 
	
	
	
}
