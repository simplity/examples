DROP TABLE HUB IF EXISTS;

CREATE TABLE HUB 
   (	
    id INTEGER  PRIMARY KEY AUTO_INCREMENT, 
	callback VARCHAR(500) NOT NULL, 
	query VARCHAR(500) 
	 );
	 
INSERT INTO HUB (callback) VALUES ('http://localhost:8086/api/troubleTicket');
INSERT INTO HUB (callback) VALUES ('http://localhost:8087/api/troubleTicket');