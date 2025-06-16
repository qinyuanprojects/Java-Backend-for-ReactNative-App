package com.example.demo.application.interfaces;

import com.example.demo.domain.Transaction;
import com.example.demo.domain.TransactionList;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IMapMessageAndHttpStatus
{
    ResponseEntity<Transaction> returnResponseWithTransaction (String message, Enum httpStatus, Transaction transaction);

    ResponseEntity<TransactionList> returnResponseWithTransactionList (String message, Enum httpStatus, List<Transaction> transactions);
}
