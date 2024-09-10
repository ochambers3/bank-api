package com.owenc.bank_api.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.owenc.bank_api.exceptions.InsufficientFundsException;
import com.owenc.bank_api.exceptions.InvalidTransferAmountException;

public class User {
    //Class variables
    private final String id;
    private final String name;
    private Account savings;
    private Account checking;
    private List<Transaction> transactions;

    //Constructor-based injection for deserialization
    //Json Creator is used as the user information is passed as a json
    @JsonCreator
    public User(@JsonProperty("id") String id, 
                @JsonProperty("name") String name, 
                @JsonProperty("savingsBalance") int savingsBalance, 
                @JsonProperty("checkingBalance") int checkingBalance) {
        this.id = id;
        this.name = name;
        this.savings = new Account(savingsBalance, "savings");
        this.checking = new Account(checkingBalance, "checking");
        this.transactions = new ArrayList<>();
    }

    // Getters and setters for User class
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Account getSavings() {
        return savings;
    }

    public Account getChecking() {
        return checking;
    }


    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Transfer funds to checking account
    public void transferToChecking(int funds) {
        // funds cannot be less than 0
        if (funds <= 0) {
            throw new InvalidTransferAmountException("Cannot transfer a negative or zero amount");
        }

        // only transfer if there is enough money to do so
        if (savings.getBalance() > funds) {
            savings.withdraw(funds);
            checking.deposit(funds);
            transactions.add(new Transaction("Transfer to Checking", funds));
        } else {
            throw new InsufficientFundsException("Insufficient funds in savings.");
        }
    }

    // transfer funds to savings account
    public void transferToSavings(int funds) {
        // funds cannot be less than 0
        if (funds <= 0) {
            throw new InvalidTransferAmountException("Cannot transfer a negative or zero amount");
        }

        // only transfer if there is enough money to do so
        if (checking.getBalance() > funds) {
            savings.deposit(funds);
            checking.withdraw(funds);
            transactions.add(new Transaction("Transfer to Savings", funds));
        } else {
            throw new InsufficientFundsException("Insufficient funds in checking.");
        }
    }
}

