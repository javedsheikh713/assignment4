--DROP TABLE users IF EXISTS;

CREATE TABLE users (
   id         VARCHAR(100) PRIMARY KEY,
  username VARCHAR(30),
  email  VARCHAR(50),
  firstname varchar(50),
  password varchar(50),
  role varchar(50)
);
