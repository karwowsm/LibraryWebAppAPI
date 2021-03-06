DROP DATABASE IF  EXISTS library;
CREATE DATABASE library;

USE library;

SET NAMES 'utf8' COLLATE 'utf8_general_ci';
ALTER DATABASE library CHARACTER SET utf8 COLLATE utf8_general_ci;

SET NAMES 'utf8' COLLATE 'utf8_general_ci';


DROP TABLE IF EXISTS bookborrowings;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS bookcopy;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS publisher;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS book;


CREATE TABLE author(
  ID INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(50) NOT NULL,
  surname VARCHAR(50) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE publisher(
  ID INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE category(
  ID INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(100) NOT NULL,
  PRIMARY KEY(ID)
);

CREATE TABLE book(
  ID INT AUTO_INCREMENT NOT NULL,
  author_id INT NOT NULL,
  publisher_id INT NOT NULL,
  category_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  publish_date YEAR NOT NULL,
  FOREIGN KEY(author_id) REFERENCES author(ID),
  FOREIGN KEY(publisher_id) REFERENCES publisher(ID),
  FOREIGN KEY(category_id) REFERENCES category(ID),
  PRIMARY KEY(ID)
);

CREATE TABLE bookcopy(
  ID INT AUTO_INCREMENT NOT NULL,
  book_id INT NOT NULL,
  book_availability BOOLEAN NOT NULL,
  FOREIGN KEY(book_id) REFERENCES book(ID),
  PRIMARY KEY(ID)
);

CREATE TABLE users(
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

CREATE TABLE bookborrowings(
  ID INT AUTO_INCREMENT NOT NULL,
  user_id INT NOT NULL, 
  book_copy_id INT NOT NULL, 
  checkout_date datetime NOT NULL,
  due_date datetime NOT NULL,
  return_date datetime NULL,
  PRIMARY KEY(ID),
  FOREIGN KEY(user_id) REFERENCES users(ID),
  FOREIGN KEY(book_copy_id) REFERENCES bookcopy(ID)
);

INSERT INTO author (name, surname) VALUES
	('Stephen', 'King'),
	('Dan', 'Brown'),
	('Andrzej', 'Sapkowski'),
	('J.K.', 'Rowling'),
	('J.R.R.', 'Tolkien'),
	('Terry', 'Pratchett');
	
INSERT INTO publisher (name) VALUES
	('Iskry'),
	('Amber'),
	('Albatros'),
	('superNOWA'),
	('Media Rodzina'),
	('Spółdzielnia Wydawnicza „Czytelnik”'),
	('Prószyński i S-ka');
	
INSERT INTO category (name) VALUES
	('horror'),
	('powieść'),
	('thriller'),
	('fantasy');
  
INSERT INTO book (author_id, publisher_id, category_id, name, publish_date) VALUES
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

INSERT INTO bookcopy (book_id, book_availability) VALUES
	(1, TRUE),
	(1, FALSE),
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
	(14, FALSE),
	(15, FALSE);

INSERT INTO users (name, surname, email, birth_date, pesel, login, password) VALUES
	('Jan', 'Kowalski', 'jan.kowalski@gmail.com', '1997-01-01', '97010166666', 'jkowalski', 'asdf'),
	('Jan', 'Nowak', 'jan.nowak@gmail.com', '1983-12-12', '83121277777', 'jnowak', 'zxcv');

INSERT INTO bookborrowings (user_id, book_copy_id, checkout_date, due_date) VALUES
	(1, 2, '2018-12-01 15:15:15', '2019-01-01 15:15:15'),
	(1, 11, '2018-12-08 15:15:15', '2019-01-08 15:15:15'),
	(1, 12, '2018-11-08 15:15:15', '2018-12-08 15:15:15'),
	(2, 13, '2018-11-15 15:15:15', '2018-12-15 15:15:15'),
	(2, 14, '2018-11-22 15:15:15', '2018-12-22 15:15:15'),
	(2, 15, '2018-11-29 15:15:15', '2018-12-29 15:15:15');

INSERT INTO bookborrowings (user_id, book_copy_id, checkout_date, due_date, return_date) VALUES
	(1, 3, '2018-11-01 15:15:15', '2018-12-01 15:15:15', '2018-11-30 15:15:15'),
	(2, 4, '2018-11-02 15:15:15', '2018-12-02 15:15:15', '2018-12-03 15:15:15');

INSERT INTO users (name, surname, email, birth_date, pesel, login, password) VALUES
  ('Jan', 'Kowalski', 'jan@gmail.com', '1995-12-10', '951210111111', 'jan123', 'qwerty'),
  ('Adam', 'Nowak', 'adam@gmail.com', '1992-10-02', '921002333333', 'adam123', 'qwerty'),
  ('Adam', 'Adamowski', 'adam123@gmail.com', '2000-10-10', '921412111111', 'adam12345', 'qwerty');
