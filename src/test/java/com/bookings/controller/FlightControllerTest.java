package com.bookings.controller;

import com.bookings.controller.FlightController;
import com.bookings.entity.Flight;
import com.bookings.entity.MealType;
import com.bookings.requests.SearchRequests;
import com.bookings.requests.Trip;
import com.bookings.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Autowired
    private ObjectMapper objectMapper;

    private Flight createSampleFlight() {
        Flight flight = new Flight();
        flight.setFlight_id(1L);
        flight.setFlightNumber("AI101");
        flight.setFromPlace("DEL");
        flight.setToPlace("BOM");
        flight.setDeparture(LocalDateTime.of(2026, 2, 15, 8, 0));
        flight.setArrival(LocalDateTime.of(2026, 2, 15, 10, 0));
        flight.setPrice(4500.0);
        flight.setAvailableSeats(150);
        flight.setMealType(com.bookings.entity.MealType.BOTH);
        flight.setAirline_name("Air India");
        return flight;
    }

    private SearchRequests createSampleSearchRequest() {
        SearchRequests request = new SearchRequests();
        request.setFromPlace("DEL");
        request.setToPlace("BOM");
        request.setTravelDate(LocalDate.of(2026, 2, 15));
        request.setTripType(Trip.ONE_WAY);
        return request;
    }

    // Test for POST /api/v1.0/flight/airline/inventory/add
    @Test
    void addFlight_Success() throws Exception {
        Flight flight = createSampleFlight();
        
        when(flightService.addFlight(any(Flight.class))).thenReturn(flight);

        mockMvc.perform(post("/api/v1.0/flight/airline/inventory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flight_id").value(1))
                .andExpect(jsonPath("$.flightNumber").value("AI101"))
                .andExpect(jsonPath("$.fromPlace").value("DEL"))
                .andExpect(jsonPath("$.toPlace").value("BOM"))
                .andExpect(jsonPath("$.price").value(4500.0))
                .andExpect(jsonPath("$.availableSeats").value(150));
    }

    @Test
    void addFlight_ValidationError() throws Exception {
        Flight flight = new Flight(); 
        
        mockMvc.perform(post("/api/v1.0/flight/airline/inventory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flight)))
                .andExpect(status().isBadRequest());
    }

    // Test for POST /api/v1.0/flight/search
    @Test
    void searchFlights_Success() throws Exception {
        SearchRequests searchRequest = createSampleSearchRequest();
        Flight flight1 = createSampleFlight();
        Flight flight2 = createSampleFlight();
        flight2.setFlight_id(2L);
        flight2.setFlightNumber("6E201");
        
        List<Flight> flights = Arrays.asList(flight1, flight2);
        
        when(flightService.searchFlights(any(SearchRequests.class))).thenReturn(flights);

        mockMvc.perform(post("/api/v1.0/flight/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].flightNumber").value("AI101"))
                .andExpect(jsonPath("$[1].flightNumber").value("6E201"));
    }

    @Test
    void searchFlights_NoResults() throws Exception {
        SearchRequests searchRequest = createSampleSearchRequest();
        
        when(flightService.searchFlights(any(SearchRequests.class))).thenReturn(List.of());

        mockMvc.perform(post("/api/v1.0/flight/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void searchFlights_ValidationError() throws Exception {
        SearchRequests searchRequest = new SearchRequests(); 
        
        mockMvc.perform(post("/api/v1.0/flight/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isBadRequest());
    }

    // Test for GET /api/v1.0/flight/airline/inventory/all
    @Test
    void getAllFlights_Success() throws Exception {
        Flight flight1 = createSampleFlight();
        Flight flight2 = createSampleFlight();
        flight2.setFlight_id(2L);
        flight2.setFlightNumber("6E201");
        flight2.setFromPlace("DEL");
        flight2.setToPlace("BLR");
        
        List<Flight> flights = Arrays.asList(flight1, flight2);
        
        when(flightService.getAllFlights()).thenReturn(flights);

        mockMvc.perform(get("/api/v1.0/flight/airline/inventory/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].flightNumber").value("AI101"))
                .andExpect(jsonPath("$[0].fromPlace").value("DEL"))
                .andExpect(jsonPath("$[0].toPlace").value("BOM"))
                .andExpect(jsonPath("$[1].flightNumber").value("6E201"))
                .andExpect(jsonPath("$[1].fromPlace").value("DEL"))
                .andExpect(jsonPath("$[1].toPlace").value("BLR"));
    }

    @Test
    void getAllFlights_Empty() throws Exception {
        when(flightService.getAllFlights()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1.0/flight/airline/inventory/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}