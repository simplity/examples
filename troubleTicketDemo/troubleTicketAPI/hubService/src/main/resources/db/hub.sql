DROP TABLE HUB IF EXISTS;

CREATE TABLE HUB 
   (	
    id INTEGER  PRIMARY KEY AUTO_INCREMENT, 
	callback VARCHAR(500) NOT NULL, 
	query VARCHAR(500) NOT NULL
	 );