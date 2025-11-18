package com.bookings.controller;

import com.bookings.controller.BookingController;
import com.bookings.entity.Flight;
import com.bookings.entity.Tickets;
import com.bookings.entity.User;
import com.bookings.entity.MealType;
import com.bookings.entity.Passengers;
import com.bookings.requests.BookingRequest;
import com.bookings.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    private BookingRequest createSampleBookingRequest() {
        BookingRequest request = new BookingRequest();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setTotalSeats(2);
        request.setMealpref(MealType.VEG);
        request.setSeatNumber("12A,12B");
        
        Passengers passenger1 = new Passengers();
        passenger1.setName("John Doe");
        passenger1.setGender("MALE");
        passenger1.setAge(30);
        
        Passengers passenger2 = new Passengers();
        passenger2.setName("Jane Doe");
        passenger2.setGender("FEMALE");
        passenger2.setAge(28);
        
        request.setPassenger(Arrays.asList(passenger1, passenger2));
        return request;
    }

    private Tickets createSampleTicket() {
        Tickets ticket = new Tickets();
        ticket.setPnr("ABC12345");
        
        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        ticket.setUser(user);
        
        Flight flight = new Flight();
        flight.setFlight_id(1L);
        flight.setFlightNumber("AI101");
        flight.setFromPlace("DEL");
        flight.setToPlace("BOM");
        flight.setDeparture(LocalDateTime.of(2026, 2, 15, 8, 0));
        flight.setArrival(LocalDateTime.of(2026, 2, 15, 10, 0));
        flight.setPrice(4500.0);
        ticket.setFlight(flight);
        
        ticket.setNumSeats(2);
        ticket.setMealpref(MealType.VEG);
        ticket.setSeatNumber("12A,12B");
        ticket.setBookingDate(LocalDateTime.now());
        ticket.setStatus("CONFIRMED");
        
        return ticket;
    }

    @Test
    void bookFlight_Success() throws Exception {
        BookingRequest request = createSampleBookingRequest();
        Tickets ticket = createSampleTicket();
        
        when(bookingService.bookFlight(anyLong(), any(BookingRequest.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/v1.0/flight/booking/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pnr").value("ABC12345"))
                .andExpect(jsonPath("$.numSeats").value(2))
                .andExpect(jsonPath("$.status").value("CONFIRMED"))
                .andExpect(jsonPath("$.mealpref").value("VEG"))
                .andExpect(jsonPath("$.seatNumber").value("12A,12B"))
                .andExpect(jsonPath("$.user.name").value("John Doe"))
                .andExpect(jsonPath("$.flight.flightNumber").value("AI101"))
                .andExpect(jsonPath("$.flight.fromPlace").value("DEL"))
                .andExpect(jsonPath("$.flight.toPlace").value("BOM"));
    }

    @Test
    void bookFlight_ValidationError() throws Exception {
        BookingRequest request = new BookingRequest(); 
        
        mockMvc.perform(post("/api/v1.0/flight/booking/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void bookFlight_ServiceError() throws Exception {
        BookingRequest request = createSampleBookingRequest();
        
        when(bookingService.bookFlight(anyLong(), any(BookingRequest.class)))
                .thenThrow(new RuntimeException("Flight not found"));

        mockMvc.perform(post("/api/v1.0/flight/booking/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // Test for GET /api/v1.0/flight/ticket/{pnr}
    @Test
    void getTicket_Success() throws Exception {
        Tickets ticket = createSampleTicket();
        
        when(bookingService.getTicketByPnr(anyString())).thenReturn(ticket);

        mockMvc.perform(get("/api/v1.0/flight/ticket/ABC12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pnr").value("ABC12345"))
                .andExpect(jsonPath("$.user.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.flight.flightNumber").value("AI101"))
                .andExpect(jsonPath("$.flight.fromPlace").value("DEL"))
                .andExpect(jsonPath("$.flight.toPlace").value("BOM"));
    }

    @Test
    void getTicket_NotFound() throws Exception {
        when(bookingService.getTicketByPnr(anyString()))
                .thenThrow(new RuntimeException("Ticket not found with PNR: INVALIDPNR"));

        mockMvc.perform(get("/api/v1.0/flight/ticket/INVALIDPNR"))
                .andExpect(status().isBadRequest());
    }

    // Test for GET /api/v1.0/flight/booking/history/{emailId}
    @Test
    void getBookingHistory_Success() throws Exception {
        Tickets ticket1 = createSampleTicket();
        Tickets ticket2 = createSampleTicket();
        ticket2.setPnr("DEF67890");
        ticket2.getFlight().setFlightNumber("6E201");
        ticket2.getFlight().setFromPlace("BOM");
        ticket2.getFlight().setToPlace("DEL");
        
        List<Tickets> tickets = Arrays.asList(ticket1, ticket2);
        
        when(bookingService.getBookingHistory(anyString())).thenReturn(tickets);

        mockMvc.perform(get("/api/v1.0/flight/booking/history/john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].pnr").value("ABC12345"))
                .andExpect(jsonPath("$[0].flight.flightNumber").value("AI101"))
                .andExpect(jsonPath("$[1].pnr").value("DEF67890"))
                .andExpect(jsonPath("$[1].flight.flightNumber").value("6E201"));
    }

    @Test
    void getBookingHistory_Empty() throws Exception {
        when(bookingService.getBookingHistory(anyString())).thenReturn(List.of());

        mockMvc.perform(get("/api/v1.0/flight/booking/history/nonexistent@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // Test for DELETE /api/v1.0/flight/booking/cancel/{pnr}
    @Test
    void cancelTicket_Success() throws Exception {
        when(bookingService.cancelTicket(anyString())).thenReturn("Ticket cancelled successfully");

        mockMvc.perform(delete("/api/v1.0/flight/booking/cancel/ABC12345"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket cancelled successfully"));
    }

    @Test
    void cancelTicket_NotFound() throws Exception {
        when(bookingService.cancelTicket(anyString()))
                .thenThrow(new RuntimeException("Ticket not found with PNR: INVALIDPNR"));

        mockMvc.perform(delete("/api/v1.0/flight/booking/cancel/INVALIDPNR"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cancelTicket_AlreadyCancelled() throws Exception {
        when(bookingService.cancelTicket(anyString()))
                .thenThrow(new RuntimeException("Ticket is already cancelled"));

        mockMvc.perform(delete("/api/v1.0/flight/booking/cancel/CANCELLEDPNR"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void cancelTicket_Within24Hours() throws Exception {
        when(bookingService.cancelTicket(anyString()))
                .thenThrow(new RuntimeException("Cancellation not allowed within 24 hours of departure"));

        mockMvc.perform(delete("/api/v1.0/flight/booking/cancel/LASTMINUTE"))
                .andExpect(status().isBadRequest());
    }
}