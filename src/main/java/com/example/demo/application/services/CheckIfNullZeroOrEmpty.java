package com.example.demo.application.services;

import com.example.demo.application.interfaces.ICheckIfNullZeroOrEmpty;
import com.example.demo.application.interfaces.ITransactionRepository;
import com.example.demo.domain.Summary;
import com.example.demo.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public class CheckIfNullZeroOrEmpty implements ICheckIfNullZeroOrEmpty
{
    @Autowired
    ITransactionRepository iTransactionRepository;

    public Boolean stringIsNullorEmpty(String string)
    {
        return string == null || string.isEmpty() || string.trim().isEmpty();
    }
    public Boolean integerIsNullorZero(Integer integer)
    {
        return integer == null || integer == 0;
    }
    public Boolean bigDecimalIsNullorZero(BigDecimal bigDecimal)
    {
        return bigDecimal == null || bigDecimal.compareTo(BigDecimal.ZERO) == 0;
    }


    public Boolean checkForMapUserId(String user_id, String title, BigDecimal amount, String category)
    {
        return stringIsNullorEmpty(user_id) || stringIsNullorEmpty(title)
                || bigDecimalIsNullorZero(amount)
                || stringIsNullorEmpty(category);
    }
    public Boolean checkSummary(Summary summary)
    {
        return bigDecimalIsNullorZero(summary.expensesResult) &&
                bigDecimalIsNullorZero(summary.balanceResult) &&
                bigDecimalIsNullorZero(summary.incomeResult);
    }


    public Boolean idIsUUID(String id)
    {
        try
        {
            UUID uuid = UUID.fromString(id);
            return true;
        }
        catch (Exception exception)
        {
            return false;
        }
    }
    public Transaction transactionExists(UUID uuid)
    {
        try
        {
            return iTransactionRepository.queryForTransaction(uuid);
        }
        catch (Exception exception)
        {
            return null;
        }
    }
}
