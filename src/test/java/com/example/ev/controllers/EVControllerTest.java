package com.example.ev.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EVControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Tests if a user can be retrieved from the DB
     * This will also test the controller is correctly setup and DAOs correctly injected 
     * 
     * @throws Exception
     */
    @DisplayName("Test EV Retrieval")
    @Test
    public void testGetEV() throws Exception {
    	String expected = "{\"id\":11001,\"model\":\"Model1\",\"make\":1997,\"batteryCapacity\":11.5}";
    	
        ResponseEntity<String> response = restTemplate.getForEntity(
        		new URL("http://localhost:" + port + "/evs/11001").toString(), String.class);
        
        assertEquals(expected, response.getBody());
    }

    /**
     * Test if the most popular EV can be retrieved from DB
     * This will also test the controller is correctly setup and DAOs correctly injected
     * 
     * @throws Exception
     */
    @DisplayName("Test Most Popular EV")
    @Test
    public void testMostPopularEV() throws Exception {
    	String expected = "{\"id\":11002,\"model\":\"Model2\",\"make\":1998,\"batteryCapacity\":12.5}";
    	
        ResponseEntity<String> response = restTemplate.getForEntity(
        		new URL("http://localhost:" + port + "/evs/popular").toString(), String.class);
        
        assertEquals(expected, response.getBody());
    }
}
