package com.spring.starter.DTO;

import javax.validation.constraints.NotNull;

public class CustomerRequestDTO {

	@NotNull
	private int cutomerId;
	@NotNull
	private int serviceRequestId;
	public CustomerRequestDTO(@NotNull int cutomerId, @NotNull int serviceRequestId) {
		super();
		this.cutomerId = cutomerId;
		this.serviceRequestId = serviceRequestId;
	}
	public int getCutomerId() {
		return cutomerId;
	}
	public void setCutomerId(int cutomerId) {
		this.cutomerId = cutomerId;
	}
	public int getServiceRequestId() {
		return serviceRequestId;
	}
	public void setServiceRequestId(int serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}
}
