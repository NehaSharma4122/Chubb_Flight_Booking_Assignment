package com.bookings.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequest {
	@NotBlank(message="Name is required")
	private String name;
	
	@NotBlank(message="Email is required")
	@Email(message="Email is invalid")
	private String email;
	
	@NotBlank
	@Size(min=8,max=14,message = "Password must be between 8 and 14")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,14}$",
             message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace")
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
