package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
        {"id", "user_id", "title", "amount", "category", "created_at", "message"}
)
public class Transaction {

    public String id;

    public String user_id;

    public String title;

    public BigDecimal amount;

    public String category;

    public Timestamp created_at;

    public String message;

    public Transaction(String id, String user_id, String title, BigDecimal amount, String category, Timestamp created_at)
    {
        this.id = id;
        this.user_id = user_id;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.created_at = created_at;
    }


    public void displayInfo()
    {
        BigDecimal roundedAmount = amount.setScale(2, RoundingMode.HALF_UP);

        System.out.println(
                "Id: " + id + "\n" +
                        "User Id: " + user_id + "\n" +
                        "Title: " + title + "\n" +
                        "Amount: " + roundedAmount + "\n" +
                        "Category: " + category + "\n" +
                        "Created_at: " + created_at + "\n" +
                        "Message: " + message
        );
    }
}
