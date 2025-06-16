package com.example.demo.infrastructure.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class InitDBRepository implements CommandLineRunner
{
    private static final String SQL_CREATE_DEMO_TABLE =
                    "CREATE TABLE IF NOT EXISTS demo_table (\n" +
                    "    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),\n" +
                    "    user_id VARCHAR(255) NOT NULL,\n" +
                    "    title VARCHAR(255) NOT NULL,\n" +
                    "    amount DECIMAL(10,2) NOT NULL,\n" +
                    "    category VARCHAR(255) NOT NULL,\n" +
                    "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
                    ");"+

                    "GRANT ALL PRIVILEGES ON TABLE demo_table TO root;"
            ;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    Environment environment;
    @Autowired
    DataSource dataSource;

    // creating a logger
    Logger logger
            = LoggerFactory.getLogger(InitDBRepository.class);

    @Override
    public void run(String... args) throws Exception
    {
        try
        {
            jdbcTemplate.update(SQL_CREATE_DEMO_TABLE);

            logger.info("Database initialised successfully.");

            getEnv();
            getDBinfo();
        }
        catch (Error error)
        {
            logger.info("Error initialising DB: .", error);
            System.exit(1);
        }
    }


    public void getEnv()
    {
        String port = environment.getProperty("local.server.port");
        String address = environment.getProperty("server.address", "localhost"); // Default to localhost if not set
        logger.info("Server is up and running on ADDRESS:PORT : " + address + ":" + port);
    }

    public void getDBinfo() throws SQLException
    {
        Connection connection = dataSource.getConnection();
        String url = connection.getMetaData().getURL();
        String userName = connection.getMetaData().getUserName();

        logger.info("Connected to: " + url);
        logger.info("Under username: " + userName);

        // Extracting the host and port from the URL
        if (url.startsWith("jdbc:mysql://") || url.startsWith("jdbc:postgresql://"))
        {
            String[] parts = url.split("//")[1].split("/");
            String hostAndPort = parts[0]; // e.g., "localhost:3306"

            logger.info("Database Host and Port: " + hostAndPort);
        }
    }
}
