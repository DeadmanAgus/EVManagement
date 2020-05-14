package com.example.ev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ev.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	
}
