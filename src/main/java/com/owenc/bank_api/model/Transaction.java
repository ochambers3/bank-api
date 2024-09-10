package com.owenc.bank_api.model;

import java.time.LocalDateTime;

public class Transaction {
    private String type;
    private int amount;
    private LocalDateTime timestamp;

    //Transaction class to keep track of user transaction history.
    public Transaction(String type, int amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }


    public String getType() {
        return type;
    }

    public int getAmmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction: " + type + ", Amount: " + amount + ", Timestamp: " + timestamp;
    }
}
