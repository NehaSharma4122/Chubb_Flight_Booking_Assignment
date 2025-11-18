package com.bookings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookings.entity.Tickets;
import com.bookings.requests.BookingRequest;
import com.bookings.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/flight")
public class BookingController {
	@Autowired
    private BookingService bookingService;

    @PostMapping("/booking/{flightid}")
    public ResponseEntity<Tickets> bookFlight(@PathVariable Long flightid, @Valid @RequestBody BookingRequest bookingRequest) {
        Tickets ticket = bookingService.bookFlight(flightid, bookingRequest);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/ticket/{pnr}")
    public ResponseEntity<Tickets> getTicket(@PathVariable String pnr) {
        Tickets ticket = bookingService.getTicketByPnr(pnr);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/booking/history/{emailId}")
    public ResponseEntity<List<Tickets>> getBookingHistory(@PathVariable String emailId) {
        List<Tickets> tickets = bookingService.getBookingHistory(emailId);
        return ResponseEntity.ok(tickets);
    }

    @DeleteMapping("/booking/cancel/{pnr}")
    public ResponseEntity<String> cancelTicket(@PathVariable String pnr) {
        String message = bookingService.cancelTicket(pnr);
        return ResponseEntity.ok(message);
    }
}
