package com.example.ev.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.ev.dao.EVRepository;
import com.example.ev.dao.UserRepository;
import com.example.ev.exceptions.DataNotFoundException;
import com.example.ev.model.EV;
import com.example.ev.model.User;

/**
 * API Controller in charge of the User operations.
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EVRepository evRepository;
	
	/**
	 * Retrieves all users and their EVs
	 * 
	 * @return
	 */
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return userRepository.findAll();
	}
	
	/**
	 * Retrieves the user by Id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/users/{id}")
	public User retrieveUser(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		
		if (!user.isPresent()) {
			throw new DataNotFoundException("id- " + id);
		}
				
		return user.get();
	}
	
	/**
	 * Creates a user in case EV Ids arrives it will create the relationship
	 * EVs must already exist
	 *  
	 * @param user
	 * @return
	 */
	@PostMapping("/users")
	public ResponseEntity<String> createUser(@RequestBody User user) {
		User savedUser;
		List<EV> evs;
		
		/* In case the user has EV Ids they must be stored 
		   creating the relationship between user and EVs) */
		if(user.getEvs() != null && user.getEvs().size() > 0) {
			evs = new ArrayList<>();
			
			for(EV ev: user.getEvs()) {
				evs.add(evRepository.findById(ev.getId()).get());
			}
			
			user.setEvs(evs);
		}
		
		savedUser = userRepository.save(user);
				
		/* Create the headers with the URI to query the just inserted record. */
		URI location  = ServletUriComponentsBuilder.
				fromCurrentRequest().
				path("/{id}").
				buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Deletes a user (and the relationship with EVs)
	 * 
	 * @param id
	 */
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id){
		if (!userRepository.findById(id).isPresent()) {
			throw new DataNotFoundException("id- " + id);
		}
		
		userRepository.deleteById(id);
	}
}
