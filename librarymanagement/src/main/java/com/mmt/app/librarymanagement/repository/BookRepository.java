package com.mmt.app.librarymanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.mmt.app.librarymanagement.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long>{

}
