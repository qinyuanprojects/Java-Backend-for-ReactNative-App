package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionList {

    public List<Transaction> transactions;

    public String message;
}
