package com.bookings.service;

import org.springframework.stereotype.Service;

import com.bookings.entity.User;
import com.bookings.requests.UserRequest;

@Service
public interface UserService {
	User registerUser(UserRequest userRequest);
	User loginUser(String email, String password);
	User getUserByEmail(String email);
}
