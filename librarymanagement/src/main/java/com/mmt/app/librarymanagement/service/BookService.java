package com.mmt.app.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmt.app.librarymanagement.entity.Book;
import com.mmt.app.librarymanagement.repository.BookRepository;
import com.mmt.app.librarymanagement.web.BookController;

@Service
public class BookService {

	Logger LOGGER = LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	private BookRepository bookRepo;

	/**
	 * 
	 * @return list of all books
	 */
	public List<Book> getAllBooks() {
		LOGGER.info("BookService.getAllBooks");
		List<Book> list = new ArrayList<>();
		bookRepo.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	/**
	 * 
	 * @param newBook
	 * @return book
	 */
	public Book saveBook(Book bookDTO) {
		LOGGER.info("BookService.saveBook");
		return bookRepo.save(bookDTO);
		
	}

	/**
	 * 
	 * @param bookId
	 * @return book
	 */
	public Book getBook(Long bookId) {
		LOGGER.info("BookService.getBook");
		Optional<Book> book = bookRepo.findById(bookId);
		return book.orElse(null);
	}

	/**
	 * @param bookDTO
	 * @param bookId
	 * @return book
	 */
	public Book updateBook(Book bookDTO, Long bookId) {
		LOGGER.info("BookService.updateBook");
		//Optional<Book> bookEntity = bookRepo.findById(bookId);
		//Book updatedBook = fromDomainObject(bookDTO, bookEntity.get());
		return bookRepo.save(bookDTO);
	}

//	private Book fromDomainObject(Book bookDTO, Book book) {
//		if(bookDTO!=null) {
//			if(book!=null) {
//				book.setId(bookDTO.getId());
//				book.setTitle(bookDTO.getTitle());
//				book.setAuthor(bookDTO.getAuthor());
//				book.setIssued(bookDTO.getIssued());
//				book.setReserved(bookDTO.getReserved());
//			}
//		}
//		return book;
//	}
	
	
}
