DROP DATABASE IF  EXISTS Library;
CREATE DATABASE Library;

USE Library;

SET NAMES 'utf8' COLLATE 'utf8_general_ci';
ALTER DATABASE Library CHARACTER SET utf8 COLLATE utf8_general_ci;

SET NAMES 'utf8' COLLATE 'utf8_general_ci';


DROP TABLE IF EXISTS BookBorrowings;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS BookCopy;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Publisher;
DROP TABLE IF EXISTS Author;
DROP TABLE IF EXISTS Book;


CREATE TABLE Author(
  ID INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE Publisher(
  ID INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE Category(
  ID INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE Book(
  ID INT AUTO_INCREMENT NOT NULL,
  author_id INT NOT NULL,
  publisher_id INT NOT NULL,
  category_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  publish_date YEAR NOT NULL,
  FOREIGN KEY(author_id) REFERENCES Author(ID),
  FOREIGN KEY(publisher_id) REFERENCES Publisher(ID),
  FOREIGN KEY(category_id) REFERENCES Category(ID),
  PRIMARY KEY(ID)
);

CREATE TABLE BookCopy(
  ID INT AUTO_INCREMENT NOT NULL,
  book_id INT NOT NULL,
  book_availability BOOLEAN NOT NULL,
  FOREIGN KEY(book_id) REFERENCES Book(ID),
  PRIMARY KEY(ID)
);

CREATE TABLE Users(
  ID INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(40) NOT NULL,
  surname VARCHAR(40) NOT NULL,
  email VARCHAR(255) NOT NULL,
  birth_date DATE NOT NULL,
  pesel VARCHAR(15) NOT NULL,
  login VARCHAR(40) NOT NULL,
  password VARCHAR(100) NOT NULL,
  PRIMARY KEY(ID),
  UNIQUE(login),
  UNIQUE(email)
);

CREATE TABLE BookBorrowings(
  ID INT AUTO_INCREMENT NOT NULL,
  user_id INT NOT NULL, 
  book_copy_id INT NOT NULL, 
  checkout_date datetime NOT NULL,
  return_date datetime NOT NULL,
  due_date datetime NULL,
  PRIMARY KEY(ID),
  FOREIGN KEY(user_id) REFERENCES Users(ID),
  FOREIGN KEY(book_copy_id) REFERENCES BookCopy(ID)
);

INSERT INTO Author (name, surname) VALUES
	('Stephen', 'King'),
	('Dan', 'Brown'),
	('Andrzej', 'Sapkowski'),
	('J.K.', 'Rowling'),
	('J.R.R.', 'Tolkien'),
	('Terry', 'Pratchett');
	
INSERT INTO Publisher (name) VALUES
	('Iskry'),
	('Amber'),
	('Albatros'),
	('superNOWA'),
	('Media Rodzina'),
	('Spółdzielnia Wydawnicza „Czytelnik”'),
	('Prószyński i S-ka');
	
INSERT INTO Category (name) VALUES
	('horror'),
	('powieść'),
	('thriller'),
	('fantasy');
  
INSERT INTO Book (author_id, publisher_id, category_id, name, publish_date) VALUES
	(1, 1, 1, 'Lśnienie', 1977),
	(1, 2, 1, 'To', 1986),
	(1, 1, 1, 'Carrie', 1974),
	(1, 3, 2, 'Zielona Mila', 1996),
	(2, 3, 3, 'Kod Leonarda da Vinci', 2003),
	(2, 3, 3, 'Anioły i Demony', 2000),
	(2, 3, 3, 'Inferno', 2013),
	(3, 4, 4, 'Krew elfów', 1994),
	(3, 4, 4, 'Czas pogardy', 1995),
	(3, 4, 4, 'Pani Jeziora', 1999),
	(4, 5, 4, 'Harry Potter i Komnata Tajemnic', 1998),
	(4, 5, 4, 'Harry Potter i Insygnia Śmierci', 2007),
	(5, 6, 4, 'Drużyna Pierścienia', 1954),
	(6, 7, 4, 'Mort', 1987),
	(6, 7, 4, 'Straż! Straż!', 1989);

INSERT INTO BookCopy (book_id, book_availability) VALUES
	(1, TRUE),
	(2, TRUE),
	(3, TRUE),
	(4, TRUE),
	(5, TRUE),
	(6, TRUE),
	(7, TRUE),
	(8, TRUE),
	(9, TRUE),
	(10, FALSE),
	(11, FALSE),
	(12, FALSE),
	(13, FALSE),
	(14, FALSE),
	(15, FALSE);
