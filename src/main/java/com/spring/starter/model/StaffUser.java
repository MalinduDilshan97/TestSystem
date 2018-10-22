package com.spring.starter.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="staff_user")
public class StaffUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int staffId;
	@NonNull
	@NotBlank
	@NotEmpty
	private String Name;
	@NotNull
	@NotBlank
	@NotEmpty
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message="Email Must be in Correct Format")
	@Column(unique=true) 
	private String email;
	@NonNull
	@NotBlank
	@NotEmpty
	@Column(unique=true) 
	private String username;
	@NonNull
	@NotBlank
	@NotEmpty
	@JsonIgnore
	private String password;
	
	private int active = 0;

	@Column(unique=true)
	private String epfNumber;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "branchId")
	private Branch branch;
	
	@NonNull
	@OneToOne
	@JoinColumn(name="roleId")
	private StaffRole staffRole;
		
	public StaffUser() {
		super();
	}

	public StaffUser(@NotBlank @NotEmpty String name,
			@NotNull @NotBlank @NotEmpty @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email Must be in Correct Format") String email,
			@NotBlank @NotEmpty String username, @NotBlank @NotEmpty String password, int active, StaffRole staffRole) {
		super();
		Name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.active = active;
		this.staffRole = staffRole;
	}
	
	public StaffUser(int staffId, @NotBlank @NotEmpty String name,
			@NotNull @NotBlank @NotEmpty @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email Must be in Correct Format") String email,
			@NotBlank @NotEmpty String username, @NotBlank @NotEmpty String password, int active, StaffRole staffRole) {
		super();
		this.staffId = staffId;
		Name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.active = active;
		this.staffRole = staffRole;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public StaffRole getStaffRole() {
		return staffRole;
	}

	public void setStaffRole(StaffRole staffRole) {
		this.staffRole = staffRole;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public String getEpfNumber() {
		return epfNumber;
	}

	public void setEpfNumber(String epfNumber) {
		this.epfNumber = epfNumber;
	}
}
