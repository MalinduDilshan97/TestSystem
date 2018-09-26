package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;
@Entity
@Table(name="staff_roles")
public class StaffRole {
	

	@Id
	@GeneratedValue
	private int roleId;
	@NonNull
	@NotEmpty
	private String roleType;
	
	public StaffRole() {
		super();
	}
	public StaffRole(int roleId, @NotEmpty String roleType) {
		super();
		this.roleId = roleId;
		this.roleType = roleType;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}
