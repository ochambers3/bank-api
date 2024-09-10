package com.owenc.bank_api.service;

import com.owenc.bank_api.exceptions.InsufficientFundsException;
import com.owenc.bank_api.exceptions.InvalidTransferAmountException;
import com.owenc.bank_api.exceptions.UserNotFoundException;
import com.owenc.bank_api.model.Transaction;
import com.owenc.bank_api.model.User;
import com.owenc.bank_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
//import java.util.logging.Logger;


// This is the service class that validates the endpoint data and makes calls to the repository layer
@Service
public class UserService {
    private final UserRepository userRepository;
    //private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());


    // Constructor-based dependency injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // This method validates the user's data and sends it to userRespository saveUser if it is good
    public void saveUser(User user) {
        validateUser(user);  // Validate user object
        validateBalances(user.getChecking().getBalance(), user.getSavings().getBalance());  // Validate balances
    
        userRepository.saveUser(user);
    }

    // Helper to validate user parameters
    //Not allowed: null request, missing or empty id, missing or empty name
    private void validateUser(User user) {
        if (user == null) {
            throw new UserNotFoundException("User cannot be null");
        }
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid User ID");
        }
        if (user.getName() == null || !(user.getName() instanceof String) || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid Name Parameter");
        }
    }

    // Helper to validate balances
    // Balances cannot be negative, balances are 0 by default.
    private void validateBalances(int checkingBalance, int savingsBalance) {
        if (checkingBalance < 0) {
            throw new IllegalArgumentException("Invalid Checking Balance");
        }
        if (savingsBalance < 0) {
            throw new IllegalArgumentException("Invalid Savings Balance");
        }
    }

    // This method validates the user by id, and returns the user if not null
    public User findUserById(String id) {
        User user = userRepository.findUserById(id);

        if (user == null) {
            throw new UserNotFoundException("User with ID: " + id + " Not Found.");
        }
        return user;
    }

    // This method returns a list of all users.
    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    //This method finds the user by userID, and transfers the funds "funds" into account "directionStr" from the other account.
    public String transferFunds(String userId, int funds, String directionStr) {
        //userID error handling is performed in findUserById
        User user = findUserById(userId);
        Direction direction;
    
        try {
            // direction is type enum for type safety. If account is not checking or savings throw error
            direction = Direction.valueOf(directionStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid account name: " + directionStr);
        }
    
        try {
            switch (direction) {
                case CHECKING:
                    user.transferToChecking(funds);
                    break;
                case SAVINGS:
                    user.transferToSavings(funds);
                    break;
                default:
                    throw new IllegalArgumentException("Account " + direction + " not found");
            }
        } catch (InvalidTransferAmountException e) {
            throw new InvalidTransferAmountException(generateErrorMessage(funds, direction));
        } catch (InsufficientFundsException e) {
            throw new InsufficientFundsException(generateErrorMessage(funds, direction));
        }
        
        userRepository.saveUser(user); // Save the updated user
        return "Transfer Successful"; // Return success message
    }

    // This method returns a user transaction history by userID
    // userID verification is performed in findUserById
    public List<Transaction> getTransactionHistory(String userId) {
        User user = findUserById(userId);

        return user.getTransactions();
    }

    // This method helps generate more cohesive error messages for multiple errors
    private String generateErrorMessage(int funds, Direction direction) {
        return String.format("Error with transfer amount %d for account %s", funds, direction);
    }

    // Direction enum definition
    public enum Direction {
        CHECKING, SAVINGS
    }
}
