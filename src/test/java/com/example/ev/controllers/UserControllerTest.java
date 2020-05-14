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
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Tests if a user can be retrieved from the DB.
     * This will also test the controller is correctly setup and DAOs correctly injected
     * 
     * @throws Exception
     */
    @DisplayName("Test User retrieval")
    @Test
    public void testGetUser() throws Exception {
    	String expected = "{\"id\":10001,\"name\":\"Tom Well\",\"address\":\"5th Avenue\",\"evs\":"
    			+ "[{\"id\":11002,\"model\":\"Model2\",\"make\":1998,\"batteryCapacity\":12.5}]}";
    	
        ResponseEntity<String> response = restTemplate.getForEntity(
        		new URL("http://localhost:" + port + "/users/10001").toString(), String.class);
        
        assertEquals(expected, response.getBody());
    }

}
