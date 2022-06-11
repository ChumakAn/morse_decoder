CREATE DATABASE IF NOT EXISTS morse;
USE morse;

DROP TABLE IF EXISTS message;

CREATE TABLE message(
                       id INT NOT NULL AUTO_INCREMENT,
                       date DATE NOT NULL,
                       text VARCHAR(40) NOT NULL,
                       PRIMARY KEY(id)
);
