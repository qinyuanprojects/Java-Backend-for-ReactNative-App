drop database demo_db;
drop user root; -- in case need to run the script again

-- Create a new user
CREATE USER root WITH PASSWORD 'Password_123';

-- Create a new database (optional)
CREATE DATABASE demo_db;
\connect demo_db

-- Grant privileges to the new user (optional)
GRANT ALL PRIVILEGES ON DATABASE demo_db TO root;

-- Create a table
CREATE TABLE demo_table (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    category VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

GRANT ALL PRIVILEGES ON TABLE demo_table TO root;
