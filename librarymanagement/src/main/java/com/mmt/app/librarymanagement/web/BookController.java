package com.mmt.app.librarymanagement.web;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmt.app.librarymanagement.Constants;
import com.mmt.app.librarymanagement.entity.Book;
import com.mmt.app.librarymanagement.service.BookService;

@RestController
@RequestMapping(path = "/book")
public class BookController {
	
	Logger LOGGER = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private BookService bookService;

	/**
	 * 
	 * @return list of all books
	 */
	@GetMapping
	public List<Book> getAllBooks() {
		LOGGER.info("BookController.getAllBooks");
		return bookService.getAllBooks();
	}
	
	/**
	 * 
	 * @param newBook
	 * @return msg::String
	 * 
	 * TODO: multiple books as input
	 */
	@PostMapping
	public String saveBook(@RequestBody Book newBook) {
		LOGGER.info("BookController.saveBook");
		Book savedBook = bookService.saveBook(newBook);
		if(savedBook.getId()==newBook.getId())
			return Constants.BOOK_SAVED;
		else
			return Constants.ERROR_SAVING_BOOK;
	}
	
	/**
	 * 
	 * @param bookId
	 * @return book
	 */
	@GetMapping("{bookId}")
	public Book getBook(@PathVariable("bookId") Long bookId) {
		LOGGER.info("BookController.getBook");
		Book book = bookService.getBook(bookId);
		return book;
	}
	
	/**
	 * 
	 * @param updatedBook
	 * @return msg::String
	 */
	@PutMapping("{bookId}")
	public String updateBook(@RequestBody Book updatedBook, @PathVariable Long bookId) {
		LOGGER.info("BookController.updateBook");
		Book savedBook =bookService.updateBook(updatedBook, bookId);
		if(savedBook.getId()==updatedBook.getId())
			return Constants.BOOK_UPDATED;
		else
			return Constants.ERROR_UPDATING_BOOK;
	}
	
//	@DeleteMapping
//	public String deleteBook() {
//		
//	}
}
