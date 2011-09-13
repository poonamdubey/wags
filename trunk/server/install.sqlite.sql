CREATE TABLE user (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       username VARCHAR(255) NOT NULL,
       firstName VARCHAR(255) NULL,
       lastName VARCHAR(255) NULL,
       email VARCHAR(255) NOT NULL,
       password VARCHAR(255) NOT NULL,
       added INT NOT NULL,
       updated INT NOT NULL,
       lastLogin INT NOT NULL,
       admin INT NOT NULL,
	   section INT NOT NULL REFERENCES class(id)
);

CREATE TABLE file (
       id INTEGER PRIMARY KEY AUTOINCREMENT ,
       name VARCHAR(255) NOT NULL,
       ownerId INT NOT NULL REFERENCES user(id),
       exerciseId INT NOT NULL REFERENCES exercise(id),
       contents TEXT,
	   section INT NOT NULL,
       updated INT NOT NULL,
       added INT NOT NULL
);

CREATE TABLE exercise (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       title VARCHAR(255) NOT NULL,
       description TEXT,
       skeleton TEXT,
       solution TEXT,  
	   testClass TEXT,
	   multiUser INT NOT NULL,
       visible INT NOT NULL,
	   openDate INT,
	   closeDate INT,
       added INT NOT NULL,
       updated INT NOT NULL,
	   section INT NOT NULL REFERENCES class(id)
);

CREATE TABLE submission (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       exerciseId INT NOT NULL REFERENCES exercise(id),
       fileId INT NOT NULL REFERENCES file(id),
       userId INT NOT NULL REFERENCES user(id),
	   partner TEXT,
       success INT NOT NULL,
       added INT NOT NULL,
       updated INT NOT NULL
);

CREATE TABLE section (
	   id INTEGER PRIMARY KEY AUTOINCREMENT,
	   name VARCHAR(255) NOT NULL,
	   administrator INT REFERENCES user(id)
);

CREATE TABLE node (
		id integer PRIMARY KEY AUTOINCREMENT,
		key VARCHAR(255) NOT NULL,
		problemResultId INT NULL,
		problemId INT NULL,
		top INT NOT NULL,
		left INT NOT NULL,
		added INT NOT NULL,
		updated INT NOT NULL
);

CREATE TABLE edge (
		id integer PRIMARY KEY AUTOINCREMENT,
		problemResultId INT NULL,
		problemId INT NULL,
		node1Key VARCHAR(255),
		node2Key VARCHAR(255),
		added INT NOT NULL,
		updated INT NOT NULL
);

CREATE TABLE problem (
		id integer PRIMARY KEY AUTOINCREMENT,
		name TEXT,
		problemText TEXT,
		evaluation INT NOT NULL,
		rules INT NOT NULL,
		arguments INT NOT NULL,
		section INT NOT NULL REFERENCES section(id),
		added INT NOT NULL,
		updated INT NOT NULL
);

CREATE TABLE problemResult (
		id integer PRIMARY KEY AUTOINCREMENT,
		problemId INT NOT NULL,
		userId INT NOT NULL,
		attemptNumber INT NOT NULL,
		currFeedback TEXT,
		problemText TEXT,
		added INT NOT NULL,
		updated INT NOT NULL
);

-- Insert some admins and sections
-- password wags
INSERT INTO user VALUES (1, 'admin1', 'Philip', 'Meznar', 'pmeznar@gmail.com', 'c80464fb24d5c80602bb8cd7e4e2d491', 0, 0, 0, 1, 0);
INSERT INTO user VALUES (2, 'admin2', 'Philip', 'Meznar', 'pmeznar@gmail.com', 'c80464fb24d5c80602bb8cd7e4e2d491', 0, 0, 0, 1, 1);
INSERT INTO user VALUES (3, 'admin3', 'Admin', 'Istrator', 'bostrt@gmail.com', 'e10adc3949ba59abbe56e057f20f883e', 0, 0, 0, 1, 2);
INSERT INTO section VALUES (0, 'Class 1', 1);
INSERT INTO section VALUES (1, 'Class 2', 2);
INSERT INTO section VALUES (2, 'Class 3', 3);

