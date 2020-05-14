package com.example.ev.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ev.model.EV;

@Repository
public interface EVRepository extends JpaRepository<EV, Integer>{

	/**
	 * Retrieves the most popular EV 
	 * 
	 * @return
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM ev WHERE id = "
			+ "(SELECT TOP 1 ev_id FROM user_ev GROUP BY ev_id ORDER BY SUM(ev_id) DESC)")
    public EV findMostPopularModel();
}

