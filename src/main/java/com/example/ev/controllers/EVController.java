package com.example.ev.controllers;

import java.net.URI;
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
import com.example.ev.exceptions.DataNotFoundException;
import com.example.ev.model.EV;

/**
 * API Controller in charge of the User operations.
 *
 */
@RestController
public class EVController {
	
	@Autowired
	private EVRepository evRepository;
	
	/**
	 * Retrieves all EVs
	 * 
	 * @return
	 */
	@GetMapping("/evs")
	public List<EV> retrieveAllEVs(){
		return evRepository.findAll();
	}
	
	/**
	 * Retrieves an EV per Id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/evs/{id}")
	public EV retrieveEV(@PathVariable int id){
		Optional<EV> ev = evRepository.findById(id);
		
		if (!ev.isPresent()) {
			throw new DataNotFoundException("id- " + id);
		}
				
		return ev.get();
	}
	
	/**
	 * Retrieves the most popular EV
	 * @return
	 */
	@GetMapping("/evs/popular")
	public EV findMostPopularModel(){
		EV popular = evRepository.findMostPopularModel();
				
		return popular;
	}
	
	/**
	 * Creates an EV
	 * 
	 * @param ev
	 * @return
	 */
	@PostMapping("/evs")
	public ResponseEntity<String> createEV(@RequestBody EV ev) {
		EV savedUser = evRepository.save(ev);
				
		URI location  = ServletUriComponentsBuilder.
				fromCurrentRequest().
				path("/{id}").
				buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	/**
	 * Deletes an EV
	 * 
	 * @param id
	 */
	@DeleteMapping("/evs/{id}")
	public void deleteEV(@PathVariable int id){
		if (!evRepository.findById(id).isPresent()) {
			throw new DataNotFoundException("id- " + id);
		}
		
		evRepository.deleteById(id);
	}
}
