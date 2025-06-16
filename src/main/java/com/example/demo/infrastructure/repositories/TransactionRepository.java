package com.example.demo.infrastructure.repositories;

import com.example.demo.application.interfaces.ITransactionRepository;
import com.example.demo.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

@Repository
public class TransactionRepository implements ITransactionRepository
{
    private static final String SQL_CREATE =
            "insert into demo_table (id, user_id, title, amount, category) values (?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_USER_ID =
            "select * from demo_table where user_id = ? order by created_at DESC";

    private static final String SQL_FIND_BY_ID =
            "select * from demo_table where id = ?";

    private static final String SQL_UPDATE =
            "update demo_table set user_id = ?, title = ?, amount = ?, category = ?, created_at = ? where id = ?";

    private static final String SQL_DELETE =
            "delete from demo_table where id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) ->
    {
        return new Transaction(
                rs.getString("id"),
                rs.getString("user_id"),
                rs.getString("title"),
                rs.getBigDecimal("amount"),
                rs.getString("category"),
                rs.getTimestamp("created_at")
        );
    });

    @Override
    public Transaction registerTransaction(String user_id, String title, BigDecimal amount, String category)
    {
        UUID id = UUID.randomUUID();

        jdbcTemplate.update(SQL_CREATE, id, user_id, title, amount, category);

        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, transactionRowMapper, id);
    }

    @Override
    public List<Transaction> fetchTransactionsByUser_Id(String userid)
    {
        return jdbcTemplate.query(SQL_FIND_BY_USER_ID, transactionRowMapper, userid);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction)
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        transaction.created_at = Timestamp.valueOf(localDateTime);

        jdbcTemplate.update(SQL_UPDATE,
                transaction.user_id, transaction.title, transaction.amount, transaction.category,
                Timestamp.valueOf(localDateTime), UUID.fromString(transaction.id));

        return transaction;
    }

    @Override
    public void deleteTransaction(UUID uuid)
    {
        jdbcTemplate.update(SQL_DELETE, uuid);
    }

    public Transaction queryForTransaction(UUID uuid)
    {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, transactionRowMapper, uuid);
    }
}
