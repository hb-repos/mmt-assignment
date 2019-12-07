package com.mmt.app.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmt.app.librarymanagement.entity.Book;
import com.mmt.app.librarymanagement.entity.User;
import com.mmt.app.librarymanagement.repository.UserRepository;

@Service
public class UserService {

	Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepo;

	/**
	 * 
	 * @param newUser
	 * @return user
	 */
	public User createUser(User newUser) {
		LOGGER.info("UserService.createUser");
		return userRepo.save(newUser);
		
	}

	/**
	 * 
	 * @return list of all users
	 */
	public List<User> getAllUsers() {
		LOGGER.info("UserService.getAllUsers");
		List<User> list = new ArrayList<>();
		userRepo.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	
	/**
	 * 
	 * @param userId
	 * @return user
	 */
	public User getUser(Long userId) {
		LOGGER.info("UserService.getUser");
		return userRepo.findById(userId).orElse(null);
	}

	/**
	 * 
	 * @param userId
	 * @param userDTO
	 * @return user
	 */
	public User updateUser(Long userId, User userDTO) {
		LOGGER.info("UserService.updateUser");
		return userRepo.save(userDTO);
	}
	
	/**
	 * 
	 * @param userId
	 * @return boolean
	 */
	public boolean validateUser(Long userId) {
		LOGGER.info("UserService.validateUser");
		return userRepo.existsById(userId);
	}
}
