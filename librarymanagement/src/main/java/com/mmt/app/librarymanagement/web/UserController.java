package com.mmt.app.librarymanagement.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmt.app.librarymanagement.Constants;
import com.mmt.app.librarymanagement.entity.User;
import com.mmt.app.librarymanagement.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * @param newUser
	 * @return msg::String
	 */
	@PostMapping
	public String saveUser(@RequestBody User newUser) {
		LOGGER.info("UserController.saveUser");
		User user = userService.createUser(newUser);
		if(user.getId()==newUser.getId())
			return Constants.USER_CREATED;
		else
			return Constants.ERROR_CREATING_USER;
	}
	
	/**
	 * 
	 * @return list of all users
	 */
	@GetMapping
	public List<User> getAllUsers(){
		LOGGER.info("UserController.getAllUsers");
		return userService.getAllUsers();
	}
	
	/**
	 * 
	 * @param userId
	 * @return user
	 */
	@GetMapping("{userId}")
	public User getUser(@PathVariable Long userId) {
		LOGGER.info("UserController.getUser");
		return userService.getUser(userId);
	}
	
	/**
	 * 
	 * @param userId
	 * @param userDTO
	 * @return msg:String
	 */
	@PutMapping("{userId}")
	public String updateUser(@PathVariable Long userId, @RequestBody User userDTO) {
		LOGGER.info("UserController.updateUser");
		User user = userService.updateUser(userId, userDTO);
		if(user.getId()==userDTO.getId())
			return Constants.USER_UPDATED;
		else
			return Constants.ERROR_UPDATING_USER;
	}
	
	@GetMapping("/validate/{userId}")
	public boolean validateUser(@PathVariable Long userId) {
		LOGGER.info("UserController.validateUser");
		return userService.validateUser(userId);
	}
}
