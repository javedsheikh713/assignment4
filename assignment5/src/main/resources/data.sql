drop table users;

CREATE TABLE users (
   id INT AUTO_INCREMENT  PRIMARY KEY,
  username VARCHAR(30),
  email  VARCHAR(50),
  firstname varchar(50),
  password varchar(50),
  role varchar(50)
);

INSERT INTO users (username,email,firstname,password,role) VALUES ('javeds', 'javed.sheikh@gmail.com','javed','sheikh','ROLE_ADMIN');
INSERT INTO users (username,email,firstname,password,role) VALUES ('shiza', 'javed.sheikh@gmail.com','shiza','shiza','ROLE_USER');
INSERT INTO users (username,email,firstname,password,role) VALUES ('test', 'test.sheikh@test.com','test','test','TEST');