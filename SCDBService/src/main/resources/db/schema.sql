DROP TABLE CONTRACT IF EXISTS;
DROP TABLE TANK IF EXISTS;
DROP TABLE ROLE IF EXISTS;
DROP TABLE ROLETYPE IF EXISTS;


CREATE TABLE CONTRACT 
   (	
    id INTEGER IDENTITY NOT NULL, 
	country VARCHAR(100),
	state VARCHAR(100),
	county VARCHAR(100),
	city VARCHAR(100),
	terminal VARCHAR(100),
	region VARCHAR(100),
	bench VARCHAR(100),
	assetName VARCHAR(100),
	assetOwner VARCHAR(100),
	dealName VARCHAR(100),
	dealCounterParty VARCHAR(100),
	contractNum VARCHAR(100),
	otherRefNum VARCHAR(100),
	desc VARCHAR(100),
	segment VARCHAR(100),
	contractingEntity VARCHAR(100),
	leaseType VARCHAR(100),
	status VARCHAR(100),
	contractSignDate VARCHAR(100),
	contractStartDate VARCHAR(100),
	contractEndDate VARCHAR(100),
	durationInMonths VARCHAR(100),
	excessThroughputRate VARCHAR(100),
	excessThroughputRateUOM VARCHAR(100),
	throughputsPerYear VARCHAR(100),
	econs VARCHAR(100),
	econsUOM VARCHAR(100),
	notes VARCHAR(1000)
	);
 
CREATE TABLE TANK
	(
	 id INTEGER IDENTITY NOT NULL,
	 contractId INTEGER NOT NULL,
	 tankId VARCHAR(100),
	 desc VARCHAR(100),
	 quantity VARCHAR(100),
	 uom VARCHAR(100),
	 storageRate VARCHAR(100),
	 currency VARCHAR(100),
	 gradeGroup VARCHAR(100)
	);
	
CREATE TABLE ROLE
	(
	 id INTEGER IDENTITY NOT NULL,
	 contractId INTEGER NOT NULL,
	 roleId INTEGER,
	 primaryIndividual VARCHAR(100),
     secondaryIndividual VARCHAR(100)
	);
	
CREATE TABLE ROLETYPE
	(	
	 roleId INTEGER NOT NULL,
	 name VARCHAR(200)
	);

	
ALTER TABLE TANK ADD FOREIGN KEY (contractId) REFERENCES CONTRACT(id);
ALTER TABLE ROLE ADD FOREIGN KEY (contractId) REFERENCES CONTRACT(id);

INSERT INTO ROLETYPE VALUES(1,'Bench Owner');
INSERT INTO ROLETYPE VALUES(2,'Originator');


