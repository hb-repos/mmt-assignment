package com.mmt.app.librarymanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.mmt.app.librarymanagement.entity.LibRegister;
import com.sun.xml.bind.v2.model.core.ID;

public interface LibRepository extends CrudRepository<LibRegister, Long> {

	LibRegister findByUserIdAndBookId(Long userId, Long bookId);
}
