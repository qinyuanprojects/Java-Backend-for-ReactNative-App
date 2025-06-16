package com.example.demo.controller;

import com.example.demo.application.interfaces.*;
import com.example.demo.domain.Summary;
import com.example.demo.domain.Transaction;

import com.example.demo.domain.TransactionList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController
{
    @Autowired
    ITransactionRepository iTransactionRepository;
    @Autowired
    ISummaryRepository iSummaryRepository;
    @Autowired
    IMappingUsersAndId iMappingUsersAndId;
    @Autowired
    ICheckIfNullZeroOrEmpty iCheckIfNullZeroOrEmpty;
    @Autowired
    IMapMessageAndHttpStatus iMapMessageAndHttpStatus;
    @Autowired
    IMapSummaryMessageAndHttpStatus iMapSummaryMessageAndHttpStatus;

    Logger logger
            = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/register")
    public ResponseEntity<Transaction> registeritem(@RequestBody Map<String, Object> objectMap)
    {
        try
        {
            Transaction mapUser = iMappingUsersAndId.mapIdAndSoOn(objectMap);

            if(iCheckIfNullZeroOrEmpty.checkForMapUserId(mapUser.user_id, mapUser.title, mapUser.amount, mapUser.category))
            {
                return iMapMessageAndHttpStatus.returnResponseWithTransaction ("All fields are required", HttpStatus.BAD_REQUEST, null);
            }

            Transaction transaction = iTransactionRepository.registerTransaction(mapUser.user_id, mapUser.title, mapUser.amount, mapUser.category);

            return iMapMessageAndHttpStatus.returnResponseWithTransaction("registered successfully", HttpStatus.CREATED, transaction);
        }
        catch (Error error)
        {
            logger.error("Error creating the transaction ", error);

            return iMapMessageAndHttpStatus.returnResponseWithTransaction("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/fetch/{User_Id}")
    public ResponseEntity<TransactionList>fetchitems(@PathVariable("User_Id") String user_id)
    {
        try
        {
            List<Transaction> transactions = iTransactionRepository.fetchTransactionsByUser_Id(user_id);

            return iMapMessageAndHttpStatus.returnResponseWithTransactionList(null, HttpStatus.OK, transactions);
            //return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        catch (Error error)
        {
            logger.error("Error fetching the transaction ", error);

            return iMapMessageAndHttpStatus.returnResponseWithTransactionList ("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    @GetMapping("/fetch/summary/{User_Id}")
    public ResponseEntity<Summary>fetchsummary(@PathVariable("User_Id") String user_id)
    {
        try
        {
            Summary summary = iSummaryRepository.fetchSummary(user_id);

            if (iCheckIfNullZeroOrEmpty.checkSummary(summary))
            {
                return iMapSummaryMessageAndHttpStatus.returnResponseWithSummary("No transactions found for that user.", HttpStatus.OK, null);
            }

            return iMapSummaryMessageAndHttpStatus.returnResponseWithSummary(null, HttpStatus.OK, summary);
        }
        catch (Error error)
        {
            logger.error("Error fetching Balance, Income, and Expenses ", error);

            return iMapSummaryMessageAndHttpStatus.returnResponseWithSummary("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<Transaction>updateitem(@RequestBody Map<String, Object> objectMap)
    {
        try
        {
            Transaction mapUser = iMappingUsersAndId.mapIdAndSoOn(objectMap);

            if(!iCheckIfNullZeroOrEmpty.idIsUUID(mapUser.id))
            {
                return iMapMessageAndHttpStatus.returnResponseWithTransaction ("Invalid Transaction Id", HttpStatus.BAD_REQUEST, null);
            }
            else if(iCheckIfNullZeroOrEmpty.checkForMapUserId(mapUser.user_id, mapUser.title, mapUser.amount, mapUser.category))
            {
                return iMapMessageAndHttpStatus.returnResponseWithTransaction ("All fields are required", HttpStatus.BAD_REQUEST, null);
            }

            Transaction transaction = iCheckIfNullZeroOrEmpty.transactionExists(UUID.fromString(mapUser.id));

            if(transaction == null)
            {
                return iMapMessageAndHttpStatus.returnResponseWithTransaction ("Transaction not found", HttpStatus.NOT_FOUND, null);
            }

            transaction = iTransactionRepository.updateTransaction(mapUser);

            return iMapMessageAndHttpStatus.returnResponseWithTransaction ("Transaction updated successfully", HttpStatus.OK, transaction);
        }
        catch (Error error)
        {
            logger.error("Error deleting the transaction ", error);

            return iMapMessageAndHttpStatus.returnResponseWithTransaction("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/remove/{Id}")
    public ResponseEntity<Transaction>removeitem(@PathVariable("Id") String id)
    {
        try
        {
            if(!iCheckIfNullZeroOrEmpty.idIsUUID(id))
            {
                return iMapMessageAndHttpStatus.returnResponseWithTransaction ("Invalid Transaction Id", HttpStatus.BAD_REQUEST, null);
            }

            Transaction transaction = iCheckIfNullZeroOrEmpty.transactionExists(UUID.fromString(id));

            if(transaction == null)
            {
                return iMapMessageAndHttpStatus.returnResponseWithTransaction ("Transaction not found", HttpStatus.NOT_FOUND, null);
            }

            iTransactionRepository.deleteTransaction(UUID.fromString(id));

            return iMapMessageAndHttpStatus.returnResponseWithTransaction ("Transaction deleted successfully", HttpStatus.OK, transaction);
        }
        catch (Error error)
        {
            logger.error("Error deleting the transaction ", error);

            return iMapMessageAndHttpStatus.returnResponseWithTransaction("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

}
