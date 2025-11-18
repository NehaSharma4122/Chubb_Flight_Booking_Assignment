package com.bookings.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookings.entity.Tickets;
import com.bookings.entity.User;

@Repository
public interface TicketRepository extends JpaRepository<Tickets, String>{
    List<Tickets> findByUser(User user);
    Optional<Tickets> findByPnr(String pnr);	
}
