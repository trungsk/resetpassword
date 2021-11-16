package com.thanhtrung.loginsecurity.dto;

import com.thanhtrung.loginsecurity.entity.User;

public class UserDTO {

	private String userName;

	private String email;

	private String password;

	private String role;

	public UserDTO() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User toEntity() {
		return new User(userName, email, password, role);
	}
}