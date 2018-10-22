package com.spring.starter.DTO;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

public class StaffUserDTO {

	@NonNull
	@NotBlank
	@NotEmpty
	private String name;
	@NotNull
	@NotBlank
	@NotEmpty
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message="Email Must be in Correct Format")
	private String email;
	@NonNull
	@NotBlank
	@NotEmpty
	private String username;
	@NonNull
	@NotBlank
	@NotEmpty
	private String password;
	
	@NonNull
	private int staffRole;

	@NotNull
	private int branchId;

	@NotNull
	@NotEmpty
	private String epfNumber;

	public StaffUserDTO() {
		super();
	}

	public StaffUserDTO(@NotBlank @NotEmpty String name,
			@NotNull @NotBlank @NotEmpty @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email Must be in Correct Format") String email,
			@NotBlank @NotEmpty String username, @NotBlank @NotEmpty String password, int staffRole) {
		super();
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.staffRole = staffRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStaffRole() {
		return staffRole;
	}

	public void setStaffRole(int staffRole) {
		this.staffRole = staffRole;
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public String getEpfNumber() {
		return epfNumber;
	}

	public void setEpfNumber(String epfNumber) {
		this.epfNumber = epfNumber;
	}
}
