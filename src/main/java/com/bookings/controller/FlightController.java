package com.bookings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookings.entity.Flight;
import com.bookings.requests.SearchRequests;
import com.bookings.service.FlightService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/flight")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @PostMapping("/airline/inventory/add")
    public ResponseEntity<Flight> addFlight(@RequestBody @Valid Flight flight) {
        Flight savedFlight = flightService.addFlight(flight);
        return ResponseEntity.ok(savedFlight);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Flight>> searchFlights(@Valid @RequestBody SearchRequests searchRequest) {
        List<Flight> flights = flightService.searchFlights(searchRequest);
        return ResponseEntity.ok(flights);
    }
    @GetMapping("/airline/inventory/all")
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }
}
