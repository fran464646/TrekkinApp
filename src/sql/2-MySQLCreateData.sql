-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "pojo" database.
-------------------------------------------------------------------------------
INSERT INTO Category (name,name_es,name_gl) VALUES ("Technology","Tecnología","Tecnoloxía");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Food","Comida","Comida");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Clothing","Ropa","Roupa");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Weapons","Armas","Armas");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Health","Salud","Saúde");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Sport","Deporte","Deporte");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Games","Juegos","Xogos");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Furniture","Muebles","Mobles");
INSERT INTO Category (name,name_es,name_gl) VALUES ("R/C","R/C","R/C");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Art","Arte","Arte");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Books","Libros","Libros");
INSERT INTO Category (name,name_es,name_gl) VALUES ("Esoteric","Esotérico","Meigalladas");

-- ------------------------------------------------------------------------
-- User:password
-- Sindo:petado
-- Abelardo:bellota
-- Gerardo:gerente
-- ------------------------------------------------------------------------
INSERT INTO UserProfile (loginName,enPassword,firstName,lastName,email) VALUES ("Sindo","OQ3ai5GmkDqoQ","Gumersindo","Toso Soto","google@internet.com");
INSERT INTO UserProfile (loginName,enPassword,firstName,lastName,email) VALUES ("Abelardo","IZhl3Jd8y1AwA","Abelardo","Bello de la Mirándola","beli@gmail.com");
INSERT INTO UserProfile (loginName,enPassword,firstName,lastName,email) VALUES ("Gerardo","NLllfCS/ViZsw","Gerardo","Midas Mendelsohn","gerardo.mm@usc.es");

INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("Cargador Samsung Home Travel", "Cargador de móbil con enchufe de pared y enchufe compatible con el mechero del coche", "2016-05-08 17:36:53", 5.95, 5.95, "¡Los mejores productos por el menor precio!", "Sindo", "2015-05-09 17:36:53", 1, NULL, 1, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("Apple iPad Air", "Tablet de apple.", "2016-05-08 17:36:53", 1444.99, 1444.99, "¡Los mejores productos por el menor precio!", "Sindo", "2015-05-09 17:36:53", 1, NULL, 1, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("Set de auriculares blanco", "Set de auriculares y micrófono que permite conducir y hablar por el móvil.", "2016-06-08 17:36:53", 5.39, 5.39, "¡Bueno, Bonito y Barato!¡Compren que me lo quitan de las manos!", "Abelardo", "2015-05-09 17:36:53", 3, NULL, 2, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("a", "", "2016-07-08 17:36:53", 33, 33, "¡Los mejores productos por el menor precio!", "Sindo", "2015-05-09 17:36:53", 1, NULL, 1, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("b", "", "2016-08-08 17:36:53", 44, 44, "Gerardo, mayorista", "Gerardo", "2015-05-09 17:36:53", 2, NULL, 3, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("c", "", "2016-10-08 17:36:53", 11, 11, "¡Los mejores productos por el menor precio!", "Sindo", "2015-05-09 17:36:53", 3, NULL, 1, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("d", "", "2016-09-08 17:36:53", 22, 22, "¡Bueno, Bonito y Barato!¡Compren que me lo quitan de las manos!", "Abelardo", "2015-05-09 17:36:53", 4, NULL, 2, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("e", "", "2016-05-09 17:36:53", 55, 55, "¡Bueno, Bonito y Barato!¡Compren que me lo quitan de las manos!", "Abelardo", "2015-05-09 17:36:53", 3, NULL, 2, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("f", "", "2016-05-15 17:36:53", 33, 33, "¡Los mejores productos por el menor precio!", "Sindo", "2015-05-09 17:36:53", 1, NULL, 1, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("g", "", "2016-05-06 17:36:53", 44, 44, "Gerardo, mayorista", "Gerardo", "2015-05-09 17:36:53", 1, NULL, 3, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("h", "", "2016-05-04 17:36:53", 22, 22, "¡Bueno, Bonito y Barato!¡Compren que me lo quitan de las manos!", "Abelardo", "2015-05-09 17:36:53", 2, NULL, 2, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("i", "", "2016-06-01 17:36:53", 11, 11, "Gerardo, mayorista", "Gerardo", "2015-05-09 17:36:53", 5, NULL, 3, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("j", "", "2016-07-09 17:36:53", 11, 11, "Gerardo, mayorista", "Gerardo", "2015-05-09 17:36:53", 4, NULL, 3, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("k", "", "2016-02-03 17:36:53", 22, 22, "¡Los mejores productos por el menor precio!", "Sindo", "2015-05-09 17:36:53", 5, NULL, 1, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("l", "", "2016-05-21 17:36:53", 32, 32, "¡Los mejores productos por el menor precio!", "Sindo", "2015-05-09 17:36:53", 2, NULL, 1, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("m", "", "2016-06-22 17:36:53", 13, 13, "¡Bueno, Bonito y Barato!¡Compren que me lo quitan de las manos!", "Abelardo", "2015-05-09 17:36:53", 3, NULL, 2, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("n", "", "2016-10-10 17:36:53", 23, 23, "¡Bueno, Bonito y Barato!¡Compren que me lo quitan de las manos!", "Abelardo", "2015-05-09 17:36:53", 1, NULL, 2, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("o", "", "2016-06-07 17:36:53", 44, 44, "¡Los mejores productos por el menor precio!", "Sindo", "2015-05-09 17:36:53", 6, NULL, 1, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("p", "", "2016-05-07 17:36:53", 423, 423, "¡Bueno, Bonito y Barato!¡Compren que me lo quitan de las manos!", "Abelardo", "2015-05-09 17:36:53", 2, NULL, 2, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("q", "", "2016-08-10 17:36:53", 23, 23, "Gerardo, mayorista", "Gerardo", "2015-05-09 17:36:53", 2, NULL, 3, 0);
INSERT INTO Product (productName, description, expiredDate, startPrice, currentPrice, deliverInformation, nicknameSeller, creationDate, belong, currentWinner, announcer, version) VALUES ("r", "", "2016-07-11 17:36:53", 14, 14, "Gerardo, mayorista", "Gerardo", "2015-05-09 17:36:53", 2, NULL, 3, 0);


insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('12','2015-05-09','11',NULL,'1','13');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('13','2015-05-09','11','1','1','13');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('15','2015-05-09','12.5','1','2','13');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('66','2015-05-09','33',NULL,'2','15');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('55','2015-05-09','33','2','1','15');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('77','2015-05-09','55.5','2','1','15');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('77','2015-05-09','66.5','1','2','15');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('88','2015-05-09','77','1','2','15');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('100','2015-05-09','22',NULL,'1','20');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('160','2015-05-09','22','1','2','20');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('180','2015-05-09','100.5','2','1','20');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('666','2015-05-09','22',NULL,'2','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('77','2015-05-09','22','2','1','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('888','2015-05-09','77.5','2','2','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('689','2015-05-09','666.5','2','1','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('699.34','2015-05-09','689.5','2','1','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('1000','2015-05-09','699.84','2','1','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('2000','2015-05-09','888.5','1','1','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('9000','2015-05-09','1000.5','1','2','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('2500','2015-05-09','2000.5','2','1','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('4000','2015-05-09','2500.5','2','2','17');
insert into bid (maximumPrice, date, lastPrice, lastWinner, bidder, auction) values('5000','2015-05-09','4000.5','2','1','17');

