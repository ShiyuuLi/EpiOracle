# [EpiOracle: Privacy-Preserving Cross-Facility Early Warning for Unknown Epidemics](https://petsymposium.org/popets/2025/popets-2025-0020.pdf)

## Description

This repository is the repository of EpiOracle, a cross-facility syndrome-based early warning system for unknown/limited-known epidemics. It was originally developed during my master's studies, under the supervision of Prof. Yuan Zhang at UESTC. The repository includes

- `./EpiOracle`: the necessary codes for obtaining the evaluate results presented in tables or diagrams in the paper
- `./DataSet`: a *synthesized* database that contains the dataset used in the experiments.

## Basic Requirements

### Hardware Requirements

No specific hardware is required.

### Software Requirements

- Packages. All required packages are contained in `./EpiOracle/lib`.
- Database. We use MySQL 9.0 as the underlying database. You can download MySQL Community Server [here](https://dev.mysql.com/downloads/mysql/).

## Environment 

### Accessibility

- GitHub Repository: [https://github.com/ShiyuuLi/EpiOracle](https://github.com/ShiyuuLi/EpiOracle)
- Branch: `main`

### Set up the environment

#### Database configuration

##### Create the required database and generate the experimental dataset

To run the experiments, you can either import the synthesized dataset (`./Dataset/EpiOracle_Symptoms.csv`) or generate a new one based on the symptom information from the World Health Organization (WHO).

- To use the synthesized dataset, run the script ` setup_database.sh` using the following command

  ```mysql
  bash <full path to setup_database.sh>
  # Replace <full path to setup_database.sh> with the full path to the script setup_database.sh on your device
  ```

  If the script has been successfully executed, ` Database and table setup complete!` will be printed. Then, turn to step <a href="#ConfigureDatabaseInCode">Configure Database in Code</a>.

- To generate a database containing a new dataset based on the symptom information from the World Health Organization (WHO), execute the following steps

  Run the following command

  ```mysql
  mysql -u root -p --local_infile=1
  # enter your password
  ```

  ```mysql
  set global local_infile=on;
  ```

  ```mysql
  CREATE DATABASE EpiOracle;
  ```

  ```mysql
  USE EpiOracle;
  CREATE TABLE symptoms
  (
      SymptomID INT AUTO_INCREMENT PRIMARY KEY,
      Symptom   TINYTEXT,
      Code      CHAR(255)
  );
  ```

  ```
  USE EpiOracle;
  LOAD DATA LOCAL INFILE 'storagePath' 
  #replace storagePath with the path of the file EpiOracle_Symptoms.csv on your device
  INTO TABLE symptoms
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  IGNORE 1 ROWS;
  ```

  **Execute the next step (Configure database in code) first** and then run `./EpiOracle/src/main/java/SymptomGen.java`.

  After finish this step, you will get a database named `EpiOracle`, in which the table `symptoms` stores the experimental dataset. In the dataset, the first 2000 rows contain COVID-19 symptoms, and next 2000 rows contain other common symptoms.

##### <a name="ConfigureDatabaseInCode">Configure database in code.</a>

Change the following strings in `./EpiOracle/src/main/java/utils/JDBCTools` based on your database setup:

- DB_URL: url of the created database. 

  Replace `<port>` in  `jdbc:mysql://localhost:<port>/mysql?useSSL=no` with your own configuration. You can find the port using:

  ```mysql
  mysql -u root -p;
  # enter your password
  use EpiOracle;
  show variables like "port";
  ```

- USER: username of the user accessing the database

  > Use "root" for simplicity.

- PASS: password corresponding to the username

### Testing the Environment

Run `EpiOracle/src/main/java/utils/JDBCTools.java`, if the environment has been successfully set, the symptom lists in the `symptoms` table would be printed.

The experimental results of fuzzy detection based on 200 real-world symptom lists are not reproducible, which are presented in Table 3 of the paper.

The reason for this non-reproducibility is that we do not publish the real-world dataset used in the experiments for privacy.

