/*	DELETE USER si existe	*/
/* DROP USER IF EXISTS 'root'@'localhost'; */

/* DROP TABLE si existe */
DROP TABLE IF EXISTS users;

/* DROP DATABASE si existe */
DROP DATABASE IF EXISTS ezchat_server;
CREATE DATABASE ezchat_server DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE ezchat_server;

/* Table des utilisateur */
CREATE TABLE users (
	id INT(10) NOT NULL AUTO_INCREMENT,
	username VARCHAR(25) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
    salt VARBINARY(100) NOT NULL,
    inscription_date DATETIME NOT NULL DEFAULT NOW(),
    last_connexion DATETIME DEFAULT NULL,
    last_message DATETIME DEFAULT NULL,
    count_message INT(10) NOT NULL DEFAULT 0, 
	PRIMARY KEY (id)
);
ALTER TABLE users CONVERT TO CHARACTER SET 'UTF8';

/*	CREATE USER FOR DATABASE	*/
/*
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';
GRANT ALL PRIVILEGES ON ezchat_server.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
*/