package com.owenc.bank_api;

import com.owenc.bank_api.model.Transaction;
import com.owenc.bank_api.model.User;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class BankApiApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    public void testCreateUser() {
        // Arrange: Create a map to represent JSON-like structure for User
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("id", "1");
        newUser.put("name", "Ann Elk");

        Map<String, Object> savings = new HashMap<>();
        savings.put("balance", 1000);
        savings.put("accountType", "savings");

        Map<String, Object> checking = new HashMap<>();
        checking.put("balance", 500);
        checking.put("accountType", "checking");

        newUser.put("savings", savings);
        newUser.put("checking", checking);

        // Act: Send a POST request to create the user
        //ResponseEntity<User> response = restTemplate.postForEntity("/createUser", newUser, String.class);
		ResponseEntity<String> response = restTemplate.postForEntity("/createUser", newUser, String.class);

        // Assert: Check if the status code is 201 CREATED and the user object is returned
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody(), "User created: Ann Elk");
    }
	

    @Test
    @Order(2)
    public void testGetUserById() {
        //url with query parameters
        String url = "/getUser?id=1";
  
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);

        // Assert: Check if the status code is 200 OK and the user is returned
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ann Elk", response.getBody().getName());
    }

    @Test
    @Order(3)
    public void testTransferFunds() {
        // Arrange: Prepare the request data
		Map<String, Object> transferData = new HashMap<>();
        transferData.put("id", "1");
        transferData.put("funds", 100);
		transferData.put("direction", "savings");

        // Act: Send a POST request to transfer funds
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/transferFunds", transferData,
                String.class);

        // Assert: Check if the transfer was successful
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transfer Successful", response.getBody());
    }

    @Test
    @Order(4)
    public void testGetAllUsers() {
        String url = "/getAllUsers";
  
        ResponseEntity<List<User>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {});

        // Assert: Check if the status code is 200 OK and the list is returned
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty()); // Ensure the list is not empty
    }

    @Test
    @Order(4)
    public void testGetHistory() {
        String url = "/getHistory?id=1";
  
        ResponseEntity<List<Transaction>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Transaction>>() {});

        // Assert: Check if the status code is 200 OK and the list is returned
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty()); // Ensure the list is not empty
    }
}

