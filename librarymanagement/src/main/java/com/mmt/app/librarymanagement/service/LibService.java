package com.mmt.app.librarymanagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;

import com.mmt.app.librarymanagement.Constants;
import com.mmt.app.librarymanagement.entity.Book;
import com.mmt.app.librarymanagement.entity.LibRegister;
import com.mmt.app.librarymanagement.repository.LibRepository;

@Service
public class LibService {

	@Autowired
	private LibRepository libRepository;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	public boolean reserveBook(Long userId, Long bookId) {
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

	public boolean releaseBook(Long userId, Long bookId) {
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

	public boolean issueBook(Long userId, Long bookId) {
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
					entry.setIssueDate(getCurrentDate().toString());
					entry.setExpectedReturnDate(getCurrentDate().plusDays(7).toString());
					libRepository.save(entry);
					
					return true;
				} 
			}
		}
		return false;
	}

	private LocalDateTime getCurrentDate() {
		LocalDateTime localNow = LocalDateTime.now(TimeZone.getTimeZone("Asia/Calcutta").toZoneId());
		return localNow;
	}

	public boolean returnBook(Long userId, Long bookId) {
		boolean validUser = userService.validateUser(userId);
		if(validUser) {
			Book book = bookService.getBook(bookId);
			if (book != null) {
				if (book.getIssued().equalsIgnoreCase("Y")) {
					
					//update book record
					book.setIssued("N");
					bookService.updateBook(book, bookId);
					
					// creating register entry
//					LibRegister entry = new LibRegister();
//					entry.setUserId(userId);
//					entry.setBookId(bookId);
//					libRepository.save(entry);
					
					return true;
				} 
			}
		}
		return false;
	}

	public boolean isAvailableBook(Long bookId) {
		Book book = bookService.getBook(bookId);
		if(book!=null) {
			if(book.getIssued().equalsIgnoreCase("N")
					&& book.getReserved().equalsIgnoreCase("N"))
				return true;
		}
		return false;
	}
	
	public List<LibRegister> getRegister(){
		List<LibRegister> libRegisters = new ArrayList<LibRegister>();
		libRepository.findAll().forEach(libRegisters::add);
		return libRegisters;
	}
}
