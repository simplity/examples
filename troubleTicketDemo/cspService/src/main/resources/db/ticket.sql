DROP TABLE TICKET IF EXISTS;
DROP TABLE NOTES IF EXISTS;
DROP TABLE RELATEDPARTIES IF EXISTS;
DROP TABLE RELATEDOBJECTS IF EXISTS;


CREATE TABLE TICKET 
   (	
    id INTEGER PRIMARY KEY, 
	correlationId VARCHAR(100) NULL, 
	description VARCHAR(1000) NOT NULL, 
	severity VARCHAR(100)NOT NULL, 
	type VARCHAR(200) NOT NULL,
	creationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP(), 
	targetResolutionDate TIMESTAMP NULL, 
	status VARCHAR(100) DEFAULT 'Created', 
	subStatus VARCHAR(100) NULL, 
	statusChangeReason VARCHAR(1000) NULL, 
	statusChangeDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP(), 
	resolutionDate TIMESTAMP NULL
	 );
 
CREATE TABLE NOTES
	(
	 noteId INTEGER PRIMARY KEY,
	 ticketId INTEGER NOT NULL,
	 date TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
	 author VARCHAR(100) NOT NULL,
	 text VARCHAR(1000) NOT NULL
	);
	
CREATE TABLE RELATEDPARTIES
	(
	 partyId INTEGER PRIMARY KEY,
	 ticketId INTEGER NOT NULL,
	 reference VARCHAR(200) NOT NULL,
	 role VARCHAR(100) NOT NULL,
	 name VARCHAR(100),
	 validFor TIMESTAMP
	);

CREATE TABLE RELATEDOBJECTS
	(
	 objectId INTEGER PRIMARY KEY,
	 ticketId INTEGER NOT NULL,
	 involvement VARCHAR(200) NOT NULL,
	 reference VARCHAR(100) NOT NULL
	);
	
