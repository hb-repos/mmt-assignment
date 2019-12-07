package com.mmt.app.librarymanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.mmt.app.librarymanagement.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
