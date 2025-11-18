package com.bookings.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookings.entity.Tickets;
import com.bookings.requests.BookingRequest;

@Service
public interface BookingService {
    Tickets bookFlight(Long flightId, BookingRequest bookingRequest);
    Tickets getTicketByPnr(String pnr);
    List<Tickets> getBookingHistory(String email);
    String cancelTicket(String pnr);

}
