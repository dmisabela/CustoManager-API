package com.customanagerapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.customanagerapi.model.User;

public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
	
	User findById(long id);

	List<User> findAll();
}	
