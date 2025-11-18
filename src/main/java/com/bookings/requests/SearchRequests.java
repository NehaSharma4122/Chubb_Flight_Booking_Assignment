package com.bookings.requests;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public class SearchRequests {
    @NotNull(message = "From place is required")
	private String fromPlace;
    
    @NotNull(message = "To place is required")
	private String toPlace;
	
    @NotNull(message = "Travel date is required")
	@Future(message = "Travel date must be in the future")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate travelDate;
	
    @JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate returnDate;
    
	@Enumerated(EnumType.STRING)
	private Trip tripType;
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
   
	public LocalDate getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(LocalDate travelDate) {
		this.travelDate = travelDate;
	}
	public Trip getTripType() {
		return tripType;
	}
	public void setTripType(Trip tripType) {
		this.tripType = tripType;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	
	
}
