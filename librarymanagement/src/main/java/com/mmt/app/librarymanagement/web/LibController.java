package com.mmt.app.librarymanagement.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mmt.app.librarymanagement.Constants;
import com.mmt.app.librarymanagement.entity.Book;
import com.mmt.app.librarymanagement.entity.LibRegister;
import com.mmt.app.librarymanagement.entity.User;
import com.mmt.app.librarymanagement.service.LibService;
import com.mmt.app.librarymanagement.service.UserService;

@RestController
public class LibController {

	Logger LOGGER = LoggerFactory.getLogger(LibController.class);
	
	@Autowired
	private LibService libService;
	
	@GetMapping
	public List<LibRegister> getRegister(){
		LOGGER.info("LibController.getRegister");
		return libService.getRegister();
	}
	
	@GetMapping("/reserve/{userId}/{bookId}")
	public String reserveBook(@PathVariable Long userId, @PathVariable Long bookId) {
		LOGGER.info("LibController.reserveBook");
		boolean res= libService.reserveBook(userId, bookId);
		if(res)
			return Constants.BOOK_RESERVED;
		else
			return Constants.ERROR_RESERVING_BOOK;
	}
	
	@GetMapping("/unreserve/{userId}/{bookId}")
	public String releaseBook(@PathVariable Long userId, @PathVariable Long bookId) {
		LOGGER.info("LibController.releaseBook");
		boolean res= libService.releaseBook(userId, bookId);
		if(res)
			return Constants.BOOK_RELEASED;
		else
			return Constants.ERROR_RELEASING_BOOK;
	}
	
	@GetMapping("/issue/{userId}/{bookId}")
	public String issueBook(@PathVariable Long userId, @PathVariable Long bookId) {
		LOGGER.info("LibController.issueBook");
		boolean res= libService.issueBook(userId, bookId);
		if(res)
			return Constants.BOOK_ISSUED;
		else
			return Constants.ERROR_ISSUING_BOOK;
	}
	
	@GetMapping("/return/{userId}/{bookId}")
	public String returnBook(@PathVariable Long userId, @PathVariable Long bookId) {
		LOGGER.info("LibController.returnBook");
		boolean res= libService.returnBook(userId, bookId);
		if(res)
			return Constants.BOOK_RETURNED;
		else
			return Constants.ERROR_RETURNING_BOOK;
	}
	
	@GetMapping("/available/{bookId}")
	public String isAvailableBook(@PathVariable Long bookId) {
		LOGGER.info("LibController.isAvailableBook");
		boolean res= libService.isAvailableBook(bookId);
		if(res)
			return Constants.BOOK_AVAILABLE;
		else
			return Constants.BOOK_NOT_AVAILABLE;
	}
}
