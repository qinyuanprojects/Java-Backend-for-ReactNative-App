package com.example.demo.application.interfaces;

import com.example.demo.domain.Summary;
import com.example.demo.domain.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public interface ICheckIfNullZeroOrEmpty
{
    Boolean stringIsNullorEmpty(String string);

    Boolean integerIsNullorZero(Integer integer);

    Boolean bigDecimalIsNullorZero(BigDecimal bigDecimal);

    Boolean checkForMapUserId(String user_id, String title, BigDecimal amount, String category);

    Boolean checkSummary(Summary summary);

    Boolean idIsUUID(String id);

    Transaction transactionExists(UUID uuid);
}
