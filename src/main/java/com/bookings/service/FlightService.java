package com.bookings.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookings.entity.Flight;
import com.bookings.requests.SearchRequests;

@Service
public interface FlightService {
	Flight addFlight(Flight flight);
    List<Flight> searchFlights(SearchRequests searchRequest);
    Flight getFlightById(Long id);
    List<Flight> getAllFlights();
}
