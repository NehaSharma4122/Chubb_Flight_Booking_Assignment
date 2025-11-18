package com.bookings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookings.entity.Role;
import com.bookings.entity.User;
import com.bookings.repository.UserRepository;
import com.bookings.requests.UserRequest;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerUser(UserRequest userRequest) {
		if(userRepository.existsByEmail(userRequest.getEmail())) {
			throw new RuntimeException("User already exists with email: "+userRequest.getEmail());
		}
		User user = new User();
		user.setName(userRequest.getName());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setRole(Role.USER);
		return userRepository.save(user);
	}

	@Override
	public User loginUser(String email, String password) {
		User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found!"));
		if(!user.getPassword().equals(password)) {
			throw new RuntimeException("Invalid Password");
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("User not found!"));
	}
	
	
}
