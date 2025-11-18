package com.bookings.entity;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="flights")
public class Flight {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long flight_id;
	
	@NotBlank
	private String airline_name;
	
	private String airline_logo;
	
	@NotBlank
	private String flightNumber;
	
	@NotBlank
	private String fromPlace;
	
	@NotBlank
	private String toPlace;
	
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime departure;
	
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime arrival;
	
	@Min(value=1)
	private double price;
	
	private int availableSeats;
	
	@Enumerated(EnumType.STRING)
	private MealType mealType;

	public String getAirline_name() {
		return airline_name;
	}

	public void setAirline_name(String airline_name) {
		this.airline_name = airline_name;
	}

	public String getAirline_logo() {
		return airline_logo;
	}

	public void setAirline_logo(String airline_logo) {
		this.airline_logo = airline_logo;
	}
	

	public Long getFlight_id() {
		return flight_id;
	}

	public void setFlight_id(Long flight_id) {
		this.flight_id = flight_id;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public LocalDateTime getDeparture() {
		return departure;
	}

	public void setDeparture(LocalDateTime departure) {
		this.departure = departure;
	}

	public LocalDateTime getArrival() {
		return arrival;
	}

	public void setArrival(LocalDateTime arrival) {
		this.arrival = arrival;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	
	
	
}
