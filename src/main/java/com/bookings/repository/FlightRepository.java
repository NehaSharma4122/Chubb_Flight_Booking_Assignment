package com.bookings.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookings.entity.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

	@Query(value = "SELECT * FROM flights f WHERE f.from_place = :fromPlace AND f.to_place = :toPlace "
            + "AND DATE(f.departure) = :travelDate", nativeQuery = true)
    List<Flight> findOneWayFlights(@Param("fromPlace") String fromPlace, 
                                  @Param("toPlace") String toPlace,
                                  @Param("travelDate") LocalDate travelDate);

    // FIXED: Use actual database column names with underscores  
    @Query(value = "SELECT * FROM flights f WHERE "
            + "((f.from_place = :fromPlace AND f.to_place = :toPlace AND DATE(f.departure) = :departureDate) OR "
            + "(f.from_place = :toPlace AND f.to_place = :fromPlace AND DATE(f.departure) = :returnDate))", nativeQuery = true)
    List<Flight> findRoundTripFlights(@Param("fromPlace") String fromPlace, 
                                     @Param("toPlace") String toPlace,
                                     @Param("departureDate") LocalDate departureDate, 
                                     @Param("returnDate") LocalDate returnDate);

    // FIXED: Use actual database column names with underscores
    @Query(value = "SELECT * FROM flights f WHERE f.from_place = :fromPlace AND f.to_place = :toPlace "
            + "AND DATE(f.departure) = :travelDate", nativeQuery = true)
    List<Flight> searchFlights(@Param("fromPlace") String fromPlace, 
                              @Param("toPlace") String toPlace,
                              @Param("travelDate") LocalDate travelDate);

}
