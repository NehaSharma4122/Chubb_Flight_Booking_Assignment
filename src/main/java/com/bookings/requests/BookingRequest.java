package com.bookings.requests;

import java.util.List;

import com.bookings.entity.MealType;
import com.bookings.entity.Passengers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookingRequest {
	@NotBlank(message="Name is required")
	private String name;
	
	@NotBlank(message="Email is required")
	@Email(message="Invalid email")
	private String email;
	
	@NotNull(message="Number of seats required")
	@Min(value=1,message="Atleast 1 seat required")
	private Integer totalSeats;
	
	@NotNull(message="Passenger Details are required.")
	private List<Passengers> passenger;
	private MealType mealpref;
	private String seatNumber;
	
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
	public Integer getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}
	public MealType getMealpref() {
		return mealpref;
	}
	public void setMealpref(MealType mealpref) {
		this.mealpref = mealpref;
	}
	public String getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}
	public List<Passengers> getPassenger() {
		return passenger;
	}
	public void setPassenger(List<Passengers> passenger) {
		this.passenger = passenger;
	}
	
	
	
}
