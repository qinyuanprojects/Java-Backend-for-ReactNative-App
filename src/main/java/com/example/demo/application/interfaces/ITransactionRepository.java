package com.example.demo.application.interfaces;

import com.example.demo.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ITransactionRepository
{
    Transaction registerTransaction(String user_id, String title, BigDecimal amount, String category);

    List<Transaction> fetchTransactionsByUser_Id(String user_id);

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(UUID id);

    Transaction queryForTransaction(UUID uuid);
}
