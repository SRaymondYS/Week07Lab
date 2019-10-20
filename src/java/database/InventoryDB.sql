DROP DATABASE IF EXISTS InventoryDB;
CREATE DATABASE InventoryDB;

USE InventoryDB;

CREATE TABLE role_table
(
    ID INT(10) NOT NULL,
    role_name VARCHAR(25) NOT NULL,
    PRIMARY KEY (ID)
);

INSERT INTO role_table VALUES (1, 'System admin');
INSERT INTO role_table VALUES (2, 'Regular user');
INSERT INTO role_table VALUES (3, 'Company admin');

SELECT * FROM role_table;

CREATE TABLE if not exists user_table 
(
    email VARCHAR(40) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    fname VARCHAR(20) NOT NULL,
    lname VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    roleID INT(10) NOT NULL,

    CONSTRAINT USER_EMAIL_PK PRIMARY KEY (email),
    CONSTRAINT FK_USER_ROLE FOREIGN KEY (roleID)
        REFERENCES role_table (ID) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO user_table (active, email, fname, lname, password, roleID)
    VALUES (TRUE, 'admin@admin.com', 'First', 'Last', 'password', 1);

INSERT INTO user_table (active, email, fname, lname, password, roleID)
    VALUES (TRUE, 'arran.woodruff@sait.edu.ca', 'Arran', 'Woodruff', 'password', 2);

INSERT INTO user_table (active, email, fname, lname, password, roleID)
    VALUES (TRUE, 'david.ward@sait.edu.ca', 'David', 'Ward', 'password', 3);

INSERT INTO user_table (active, email, fname, lname, password, roleID)
    VALUES (TRUE, 'steven.wong01@sait.edu.ca', 'Steven', 'Wong', 'password', 2);

INSERT INTO user_table (active, email, fname, lname, password, roleID)
    VALUES(TRUE, 'alex@gmail.com', 'Alex', 'Carvajal', 'password', 3);

INSERT INTO user_table (active, email, fname, lname, password, roleID)
    VALUES(FALSE, 'placeholder@gmail.com', 'Place', 'Holder', 'password', 3);

SELECT * FROM user_table;