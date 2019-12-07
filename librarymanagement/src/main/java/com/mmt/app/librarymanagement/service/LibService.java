package com.mmt.app.librarymanagement.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmt.app.librarymanagement.entity.Book;
import com.mmt.app.librarymanagement.entity.LibRegister;
import com.mmt.app.librarymanagement.repository.LibRepository;

@Service
public class LibService {

	Logger LOGGER = LoggerFactory.getLogger(LibService.class);

	@Autowired
	private LibRepository libRepository;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LocalDate localDate;
	
	/**
	 * 
	 * @param userId
	 * @param bookId
	 * @return boolean
	 */
	public boolean reserveBook(Long userId, Long bookId) {
		LOGGER.info("LibController.reserveBook");
		boolean validUser = userService.validateUser(userId);
		if(validUser) {
			Book book = bookService.getBook(bookId);
			if (book != null) {
				if (book.getReserved().equalsIgnoreCase("N")
						&& book.getIssued().equalsIgnoreCase("N")) {
					book.setReserved("Y");
					
					bookService.updateBook(book, bookId);
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 * 
	 * @param userId
	 * @param bookId
	 * @return boolean
	 */
	public boolean releaseBook(Long userId, Long bookId) {
		LOGGER.info("LibController.releaseBook");
		boolean validUser = userService.validateUser(userId);
		if(validUser) {
			Book book = bookService.getBook(bookId);
			if (book != null) {
				if (book.getReserved().equalsIgnoreCase("Y")) {
					book.setReserved("N");
					
					bookService.updateBook(book, bookId);
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 * 
	 * @param userId
	 * @param bookId
	 * @return boolean
	 */
	public boolean issueBook(Long userId, Long bookId) {
		LOGGER.info("LibController.issueBook");
		boolean validUser = userService.validateUser(userId);
		if(validUser) {
			Book book = bookService.getBook(bookId);
			if (book != null) {
				if (book.getIssued().equalsIgnoreCase("N") 
						&& book.getReserved().equalsIgnoreCase("Y")) {
					
					//update book record
					book.setReserved("N");
					book.setIssued("Y");
					bookService.updateBook(book, bookId);
					
					// creating register entry
					LibRegister entry = new LibRegister();
					entry.setUserId(userId);
					entry.setBookId(bookId);
					entry.setIssueDate(localDate.toString());
					entry.setExpectedReturnDate(localDate.plusDays(7).toString());
					libRepository.save(entry);
					
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 * 
	 * @param userId
	 * @param bookId
	 * @return boolean
	 */
	public boolean returnBook(Long userId, Long bookId) {
		LOGGER.info("LibController.returnBook");
		boolean validUser = userService.validateUser(userId);
		if(validUser) {
			Book book = bookService.getBook(bookId);
			if (book != null) {
				if (book.getIssued().equalsIgnoreCase("Y")) {
					
					//update book record
					book.setIssued("N");
					bookService.updateBook(book, bookId);
					
					
					// creating register entry
					LibRegister entry = libRepository.findByUserIdAndBookId(userId, bookId);
					int fine=5;
					if (entry!=null) {
						// check for fine
						LocalDate expReturnDate= LocalDate.parse(entry.getExpectedReturnDate());
						if(expReturnDate.compareTo(localDate)>0) {
							Period period = Period.between(expReturnDate, localDate.plusDays(10)).normalized();
							if (!period.isNegative()) {
								fine=fine*period.getDays();
							}
						}
						entry.setReturnDate(localDate.toString());
						entry.setFineAmount(fine);
						libRepository.save(entry);
					} else {
						return false;
					}
					return true;
				} 
			}
		}
		return false;
	}

	/**
	 * 
	 * @param bookId
	 * @return boolean
	 */
	public boolean isAvailableBook(Long bookId) {
		LOGGER.info("LibController.isAvailableBook");
		Book book = bookService.getBook(bookId);
		if(book!=null) {
			if(book.getIssued().equalsIgnoreCase("N")
					&& book.getReserved().equalsIgnoreCase("N"))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return list of all register entries
	 */
	public List<LibRegister> getRegister(){
		LOGGER.info("LibController.getRegister");
		List<LibRegister> libRegisters = new ArrayList<LibRegister>();
		libRepository.findAll().forEach(libRegisters::add);
		return libRegisters;
	}
}
