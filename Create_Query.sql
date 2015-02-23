use samcloud;

# Create Query to create Revenue table
CREATE TABLE state_revenue
(
State varchar(5) NOT NULL,
Revenue int NOT NULL,
Rank int,
RevYear int NOT NULL,
PRIMARY KEY (State, RevYear)
)


# Create query to create Institution table
CREATE TABLE state_institution
(
ins_id int NOT NULL,
ins_Name varchar(255) NOT NULL,
ins_Address varchar(255),
ins_City varchar(50),
ins_State varchar(5) NOT NULL,
ins_Zip varchar(30),
PRIMARY KEY (ins_id)
)