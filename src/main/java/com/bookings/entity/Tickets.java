package com.bookings.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="tickets")
public class Tickets {
	@Id
	private String pnr;
	
	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@ManyToOne
	@JoinColumn(name="flight_id",nullable = false)
	private Flight flight;
	
	@Min(value=1)
	private Integer numSeats;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="ticket_pnr")
	private List<Passengers> passenger = new ArrayList<>();

	
	@Enumerated(EnumType.STRING)
	private MealType mealpref;
	
	private String seatNumber;
	
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime bookingDate;
	private String status;
	
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Flight getFlight() {
		return flight;
	}
	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	public Integer getNumSeats() {
		return numSeats;
	}
	public void setNumSeats(Integer numSeats) {
		this.numSeats = numSeats;
	}
	public List<Passengers> getPassenger() {
		return passenger;
	}
	public void setPassenger(List<Passengers> passenger) {
		this.passenger = passenger;
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
	public LocalDateTime getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
