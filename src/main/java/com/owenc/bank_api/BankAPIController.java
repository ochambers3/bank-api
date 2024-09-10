package com.owenc.bank_api;

import com.owenc.bank_api.model.Transaction;
import com.owenc.bank_api.model.User;

//import statements
import java.util.List;
import java.util.Map;

import com.owenc.bank_api.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
public class BankAPIController {

    private final UserService userService;

    // Constructor-based dependency injection
    public BankAPIController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to create a new user
    @PostMapping("/createUser")
    public String createUser(@RequestBody User user) {
        userService.saveUser(user);
        return "User created: " + user.getName();
    }

    // Endpoint to get a user by ID
    @GetMapping("/getUser")
    public User getUser(@RequestParam String id) {
        return userService.findUserById(id);
    }

    // Endpoint to return all users in the Hashmap
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // Endpoint to transfer funds from one account to another
    @PostMapping("/transferFunds")
    public String transferFunds(@RequestBody Map<String, Object> requestData) {
        // Extracting values from the request body map
        String id = (String) requestData.get("id");
        int funds = (Integer) requestData.get("funds");
        String direction = (String) requestData.get("direction");

        // Call the userService to handle the transfer
        return userService.transferFunds(id, funds, direction);
    }
    
    // Endpoint for getting a user's transfer history using id parameter
    @GetMapping("/getHistory")
    public List<Transaction> getHistory(@RequestParam String id) {
        return userService.getTransactionHistory(id);
    }

}

