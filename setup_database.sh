#!/bin/bash

# Prompt for MySQL credentials
read -p "Enter MySQL root password: " MYSQL_ROOT_PASSWORD

# Set up MySQL commands
mysql -u root -p"$MYSQL_ROOT_PASSWORD" --local_infile=1 <<EOF

# Drop the database if it already exists
DROP DATABASE IF EXISTS EpiOracle;

# Enable local infile setting
SET GLOBAL local_infile=ON;

# Create the EpiOracle database and symptoms table
CREATE DATABASE IF NOT EXISTS EpiOracle;
USE EpiOracle;

CREATE TABLE IF NOT EXISTS symptoms (
    SymptomID INT AUTO_INCREMENT PRIMARY KEY,
    Symptom   TINYTEXT,
    Code      CHAR(255)
);

EOF

# Ask for dataset path
read -p "Enter the full path to EpiOracle_Symptoms.csv: " CSV_PATH

# Import the dataset into the symptoms table
mysql -u root -p"$MYSQL_ROOT_PASSWORD" --local_infile=1 <<EOF
USE EpiOracle;
LOAD DATA LOCAL INFILE '$CSV_PATH'
INTO TABLE symptoms
FIELDS TERMINATED BY ',' ENCLOSED BY '"'
IGNORE 1 ROWS;
EOF

echo "Database and table setup complete!"