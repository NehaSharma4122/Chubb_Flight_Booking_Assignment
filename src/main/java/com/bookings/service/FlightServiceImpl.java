package com.bookings.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookings.entity.Flight;
import com.bookings.repository.FlightRepository;
import com.bookings.requests.SearchRequests;
import com.bookings.requests.Trip;

@Service
public class FlightServiceImpl implements FlightService {
	 @Autowired
	    private FlightRepository flightRepository;
	@Override
	public Flight addFlight(Flight flight) {
		return flightRepository.save(flight);
	}

	@Override
    public List<Flight> searchFlights(SearchRequests searchRequest) {
		Trip tripType = searchRequest.getTripType();
        
        if (tripType == Trip.ONE_WAY) {
            return flightRepository.findOneWayFlights(
                searchRequest.getFromPlace(),
                searchRequest.getToPlace(),
                searchRequest.getTravelDate()
            );
        } else if (tripType == Trip.ROUND_TRIP) {
            if (searchRequest.getReturnDate() == null) {
                throw new RuntimeException("Return date is required for round trip");
            }
            if (searchRequest.getReturnDate().isBefore(searchRequest.getTravelDate())) {
                throw new RuntimeException("Return date cannot be before departure date");
            }
            
            return flightRepository.findRoundTripFlights(
                searchRequest.getFromPlace(),
                searchRequest.getToPlace(),
                searchRequest.getTravelDate(),
                searchRequest.getReturnDate()
            );
        } else {
            return flightRepository.searchFlights(
                searchRequest.getFromPlace(),
                searchRequest.getToPlace(),
                searchRequest.getTravelDate()
            );
        }
    }
	@Override
	public Flight getFlightById(Long id) {
		 return flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
	    
	}
	@Override
	public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
	
}
