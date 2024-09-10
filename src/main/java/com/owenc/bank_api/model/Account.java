package com.owenc.bank_api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
    private int balance;
    private String accountType;

    // Account class to keep track of both of the user's accounts
    @JsonCreator
    public Account(@JsonProperty("startingBalance") int startingBalance, 
                    @JsonProperty("accountType") String accountType) {
        this.balance = startingBalance;
        this.accountType = accountType;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public String getAccountType() {
        return accountType;
    }
}