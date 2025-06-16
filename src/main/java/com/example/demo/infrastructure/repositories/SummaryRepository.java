package com.example.demo.infrastructure.repositories;

import com.example.demo.application.interfaces.ISummaryRepository;
import com.example.demo.domain.Summary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SummaryRepository implements ISummaryRepository
{
    private static final String SQL_Summary =
            "select \n" +
                    "sum (case when (user_id = ?) then amount else 0 end) as BalanceResult,\n" +
                    "sum (case when (amount > 0 and user_id = ?) then amount else 0 end) as IncomeResult,\n" +
                    "sum (case when (amount < 0 and user_id = ?) then amount else 0 end) as ExpensesResult\n" +
                    "from demo_table";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Summary> summaryRowMapper = ((rs, rowNum) ->
    {
        return new Summary(
                rs.getBigDecimal("balanceresult"),
                rs.getBigDecimal("incomeresult"),
                rs.getBigDecimal("expensesresult")
        );
    });

    @Override
    public Summary fetchSummary(String user_id)
    {
        return jdbcTemplate.queryForObject(SQL_Summary, summaryRowMapper, user_id, user_id, user_id);
    }
}
