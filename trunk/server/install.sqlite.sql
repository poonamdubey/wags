CREATE TABLE user (
       id INTEGER PRIMARY KEY AUTOINCREMENT ,
       username VARCHAR(255) NOT NULL,
       firstName VARCHAR(255) NULL,
       lastName VARCHAR(255) NULL,
       email VARCHAR(255) NOT NULL,
       password VARCHAR(255) NOT NULL,
       added INT NOT NULL,
       updated INT NOT NULL,
       lastLogin INT NOT NULL
);

CREATE TABLE file (
       id INTEGER PRIMARY KEY AUTOINCREMENT ,
       name VARCHAR(255) NOT NULL,
       ownerId INT NOT NULL REFERENCES user(id),
       contents TEXT ,
       updated INT NOT NULL,
       added INT NOT NULL
);
