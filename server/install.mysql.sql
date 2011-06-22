CREATE TABLE user (
       id INTEGER NOT NULL,
       username VARCHAR(255) NOT NULL,
       firstName VARCHAR(255) NULL,
       lastName VARCHAR(255) NULL,
       email VARCHAR(255) NOT NULL,
       password VARCHAR(255) NOT NULL,
       added INT NOT NULL,
       updated INT NOT NULL,
       lastLogin INT NOT NULL,
       admin INT NOT NULL,
       PRIMARY KEY(id)
)ENGINE=InnoDB;

CREATE TABLE file (
       id INTEGER NOT NULL,
       ownerId INT NOT NULL REFERENCES user(id),
       name VARCHAR(255) NOT NULL,
       contents TEXT ,
       updated INT NOT NULL,
       added INT NOT NULL,
       PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE exercise (
       id INT NOT NULL,
       title VARCHAR(255) NOT NULL,
       description TEXT,
       skeleton TEXT,
       solution TEXT,   
       added INT NOT NULL,
       updated INT NOT NULL,
       PRIMARY KEY(id)
)ENGINE=InnoDB;

CREATE TABLE submission (
       id INT NOT NULL,
       code TEXT NOT NULL,
       exerciseId NOT NULL REFERENCES exercise(id),
       studentId NOT NULL REFERENCES user(id),
       added INT NOT NULL,
       PRIMARY KEY(id)
)ENGINE=InnoDB;

-- Insert some admins
-- password 123456
INSERT INTO user VALUES (1, 'admin', 'Admin', 'Istrator', 'bostrt@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 0, 0, 0, 1);