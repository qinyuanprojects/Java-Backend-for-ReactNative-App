package com.example.demo.application.services;

import com.example.demo.application.interfaces.IMapMessageAndHttpStatus;
import com.example.demo.domain.Transaction;
import com.example.demo.domain.TransactionList;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MapMessageAndHttpStatus implements IMapMessageAndHttpStatus
{
    public ResponseEntity<Map<String, String>> returnResponse (String message, Enum httpStatus)
    {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        return new ResponseEntity<>(map, (HttpStatusCode) httpStatus);
    }

    public ResponseEntity<Transaction> returnResponseWithTransaction (String message, Enum httpStatus, Transaction transaction)
    {
        if(transaction == null)
        {
            transaction = new Transaction(null, null, null, null, null, null);
            transaction.message = message;
        }
        return new ResponseEntity<>(transaction, (HttpStatusCode) httpStatus);
    }

    public ResponseEntity<TransactionList> returnResponseWithTransactionList (String message, Enum httpStatus, List<Transaction> transactions)
    {
        TransactionList transactionList = new TransactionList();
        transactionList.transactions = transactions;

        if(transactions.isEmpty())
        {
            transactionList.transactions = null;
            transactionList.message = "No transactions found for that user.";
            //httpStatus = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(transactionList, (HttpStatusCode) httpStatus);
    }
}
