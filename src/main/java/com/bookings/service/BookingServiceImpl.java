package com.bookings.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookings.entity.Flight;
import com.bookings.entity.Passengers;
import com.bookings.entity.Role;
import com.bookings.entity.Tickets;
import com.bookings.entity.User;
import com.bookings.repository.FlightRepository;
import com.bookings.repository.TicketRepository;
import com.bookings.repository.UserRepository;
import com.bookings.requests.BookingRequest;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Tickets bookFlight(Long flightId, BookingRequest bookingRequest) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));
        
        User user = userRepository.findByEmail(bookingRequest.getEmail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(bookingRequest.getEmail());
                    newUser.setName(bookingRequest.getName());
                    newUser.setPassword(UUID.randomUUID().toString()); 
                    newUser.setRole(Role.USER);
                    return userRepository.save(newUser);
                });
        
        if (flight.getAvailableSeats() < bookingRequest.getTotalSeats()) {
            throw new RuntimeException("Not enough seats available");
        }
        
        String pnr = generatePNR();
        
        List<Passengers> passengers = bookingRequest.getPassenger().stream().map(this::convertToPassenger).collect(Collectors.toList()); 
   
        Tickets ticket = new Tickets();
        ticket.setPnr(pnr);
        ticket.setUser(user);
        ticket.setFlight(flight);
        ticket.setNumSeats(bookingRequest.getTotalSeats());
        ticket.setPassenger(passengers);
        ticket.setMealpref(bookingRequest.getMealpref());
        ticket.setSeatNumber(bookingRequest.getSeatNumber());
        ticket.setBookingDate(LocalDateTime.now());
        ticket.setStatus("CONFIRMED");
        
        flight.setAvailableSeats(flight.getAvailableSeats() - bookingRequest.getTotalSeats());
        flightRepository.save(flight);
        
        return ticketRepository.save(ticket);
    }

    @Override
    public Tickets getTicketByPnr(String pnr) {
        return ticketRepository.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("Ticket not found with PNR: " + pnr));
    }

    @Override
    public List<Tickets> getBookingHistory(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        return ticketRepository.findByUser(user);
    }
    @Override
    public String cancelTicket(String pnr) {
        Tickets ticket = getTicketByPnr(pnr);
        
        LocalDateTime departureTime = ticket.getFlight().getDeparture();
        if (LocalDateTime.now().plusHours(24).isAfter(departureTime)) {
            throw new RuntimeException("Cancellation not allowed within 24 hours of departure");
        }
        
        ticket.setStatus("CANCELLED");
        
        Flight flight = ticket.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + ticket.getNumSeats());
        flightRepository.save(flight);
        
        ticketRepository.save(ticket);
        return "Ticket cancelled successfully";
    }

    private String generatePNR() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private Passengers convertToPassenger(Passengers passengerReq) {
        Passengers passenger = new Passengers();
        passenger.setName(passengerReq.getName());
        passenger.setGender(passengerReq.getGender());
        passenger.setAge(passengerReq.getAge());
        return passenger;
    }}
