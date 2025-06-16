package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
        {"balanceResult", "incomeResult", "expensesResult", "message"}
)
public class Summary
{
    public BigDecimal balanceResult;

    public BigDecimal incomeResult;

    public BigDecimal expensesResult;

    public String message;


    public Summary(BigDecimal balanceResult, BigDecimal incomeResult, BigDecimal expensesResult)
    {
        this.balanceResult = balanceResult;
        this.incomeResult = incomeResult;
        this.expensesResult = expensesResult;
    }
}
