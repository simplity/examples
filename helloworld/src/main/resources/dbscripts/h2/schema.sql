DROP TABLE AGENTS IF EXISTS;
DROP TABLE CUSTOMER IF EXISTS;
DROP TABLE ORDERS IF EXISTS;
DROP TABLE STUDENT IF EXISTS;
DROP TABLE COMP_PARENT IF EXISTS;
DROP TABLE COMP_CHILD IF EXISTS;
DROP TABLE COMP_GRAND IF EXISTS;

CREATE TABLE STUDENT (
  ID INTEGER NOT NULL,
  NAME  VARCHAR(100),
  ADDRESS  VARCHAR(200)  
); 

CREATE TABLE  "AGENTS" 
   (	
    "AGENT_CODE" CHAR(6) NOT NULL PRIMARY KEY, 
	"AGENT_NAME" CHAR(40), 
	"WORKING_AREA" CHAR(35), 
	"COMMISSION" NUMBER(10,2), 
	"PHONE_NO" CHAR(15), 
	"COUNTRY" VARCHAR2(25) 
	 );
	 


CREATE TABLE  "CUSTOMER" 
   (	"CUST_CODE" VARCHAR2(6) NOT NULL PRIMARY KEY, 
	"CUST_NAME" VARCHAR2(40) NOT NULL, 
	"CUST_CITY" CHAR(35), 
	"WORKING_AREA" VARCHAR2(35) NOT NULL, 
	"CUST_COUNTRY" VARCHAR2(20) NOT NULL, 
	"GRADE" NUMBER, 
	"OPENING_AMT" NUMBER(12,2) NOT NULL, 
	"RECEIVE_AMT" NUMBER(12,2) NOT NULL, 
	"PAYMENT_AMT" NUMBER(12,2) NOT NULL, 
	"OUTSTANDING_AMT" NUMBER(12,2) NOT NULL, 
	"PHONE_NO" VARCHAR2(17) NOT NULL, 
	"AGENT_CODE" CHAR(6) NOT NULL REFERENCES AGENTS
);   

CREATE TABLE  "ORDERS" 
   (
     "ORD_NUM" NUMBER(6,0) NOT NULL PRIMARY KEY, 
	"ORD_AMOUNT" NUMBER(12,2) NOT NULL, 
	"ADVANCE_AMOUNT" NUMBER(12,2) NOT NULL, 
	"ORD_DATE" DATE NOT NULL, 
	"CUST_CODE" VARCHAR2(6) NOT NULL REFERENCES CUSTOMER, 
	"AGENT_CODE" CHAR(6) NOT NULL REFERENCES AGENTS, 
	"ORD_DESCRIPTION" VARCHAR2(60) NOT NULL
   );


CREATE TABLE  "COMP_PARENT" 
   (
    "PARENT_KEY_TEXT" VARCHAR2(50) NOT NULL, 
	"PARENT_KEY_NBR" NUMBER(8,0) NOT NULL,
	"CREATED_USER" VARCHAR2(25) NOT NULL,
	"CREATED_AT" TIMESTAMP (6) NOT NULL,
	"MODIFIED_USER" VARCHAR2(25) NOT NULL,
	"MODIFIED_AT" TIMESTAMP (6) NOT NULL,
	"NULLABLETEXT" VARCHAR2(50), 
	"TEXT" VARCHAR2(25) NOT NULL,
	 CONSTRAINT "COMP_PARENT_PK" PRIMARY KEY ("PARENT_KEY_TEXT", "PARENT_KEY_NBR")) ;

CREATE TABLE  "COMP_CHILD" 
   (
    "CHILD_KEY_TEXT" VARCHAR2(50) NOT NULL, 
	"CHILD_KEY_NBR" NUMBER(8,0) NOT NULL, 
	"CHILD_KEY_DATE_TIME" DATE NOT NULL, 
	"CHILD_TEXT" VARCHAR2(25) NOT NULL, 
	"CHILD_MODIFIED_AT" TIMESTAMP (6) NOT NULL, 
	 CONSTRAINT "COMP_CHILD_PK" PRIMARY KEY ("CHILD_KEY_TEXT", "CHILD_KEY_NBR", "CHILD_KEY_DATE_TIME") 
   ) ;


CREATE TABLE  "COMP_GRAND" 
   (	
    "GRAND_KEY_TEXT" VARCHAR2(50) NOT NULL, 
	"GRAND_KEY_NBR" NUMBER(8,0) NOT NULL, 
	"GRAND_KEY_DATE_TIME" DATE NOT NULL, 
	"GRAND_KEY2_TEXT" VARCHAR2(50) NOT NULL, 
	"GRAND_TEXT" VARCHAR2(50) NOT NULL, 
	 CONSTRAINT "COMP_GRAND_PK" PRIMARY KEY ("GRAND_KEY_TEXT", "GRAND_KEY_NBR", "GRAND_KEY_DATE_TIME", "GRAND_KEY2_TEXT")
   ) ;
   
   
INSERT INTO AGENTS VALUES ('A003', 'Alex ', 'London', '0.13', '075-12458969', '');
INSERT INTO AGENTS VALUES ('A008', 'Alford', 'New York', '0.12', '044-25874365', '');
INSERT INTO AGENTS VALUES ('A011', 'Ravi Kumar', 'Bangalore', '0.15', '077-45625874', '');
INSERT INTO AGENTS VALUES ('A010', 'Santakumar', 'Chennai', '0.14', '007-22388644', '');
INSERT INTO AGENTS VALUES ('A012', 'Lucida', 'San Jose', '0.12', '044-52981425', '');
INSERT INTO AGENTS VALUES ('A005', 'Anderson', 'Brisban', '0.13', '045-21447739', '');
INSERT INTO AGENTS VALUES ('A001', 'Subbarao', 'Bangalore', '0.14', '077-12346674', '');
INSERT INTO AGENTS VALUES ('A002', 'Mukesh', 'Mumbai', '0.11', '029-12358964', '');
INSERT INTO AGENTS VALUES ('A006', 'McDen', 'London', '0.15', '078-22255588', '');
INSERT INTO AGENTS VALUES ('A004', 'Ivan', 'Torento', '0.15', '008-22544166', '');
INSERT INTO AGENTS VALUES ('A009', 'Benjamin', 'Hampshair', '0.11', '008-22536178', '');
   
INSERT INTO CUSTOMER VALUES ('C00013', 'Holmes', 'London', 'London', 'UK', '2', '6000.00', '5000.00', '7000.00', '4000.00', 'BBBBBBB', 'A003');
INSERT INTO CUSTOMER VALUES ('C00001', 'Micheal', 'New York', 'New York', 'USA', '2', '3000.00', '5000.00', '2000.00', '6000.00', 'CCCCCCC', 'A008');
INSERT INTO CUSTOMER VALUES ('C00020', 'Albert', 'New York', 'New York', 'USA', '3', '5000.00', '7000.00', '6000.00', '6000.00', 'BBBBSBB', 'A008');
INSERT INTO CUSTOMER VALUES ('C00025', 'Ravindran', 'Bangalore', 'Bangalore', 'India', '2', '5000.00', '7000.00', '4000.00', '8000.00', 'AVAVAVA', 'A011');
INSERT INTO CUSTOMER VALUES ('C00024', 'Cook', 'London', 'London', 'UK', '2', '4000.00', '9000.00', '7000.00', '6000.00', 'FSDDSDF', 'A006');
INSERT INTO CUSTOMER VALUES ('C00015', 'Stuart', 'London', 'London', 'UK', '1', '6000.00', '8000.00', '3000.00', '11000.00', 'GFSGERS', 'A003');
INSERT INTO CUSTOMER VALUES ('C00002', 'Bolt', 'New York', 'New York', 'USA', '3', '5000.00', '7000.00', '9000.00', '3000.00', 'DDNRDRH', 'A008');
INSERT INTO CUSTOMER VALUES ('C00018', 'Fleming', 'Brisban', 'Brisban', 'Australia', '2', '7000.00', '7000.00', '9000.00', '5000.00', 'NHBGVFC', 'A005');
INSERT INTO CUSTOMER VALUES ('C00021', 'Jacks', 'Brisban', 'Brisban', 'Australia', '1', '7000.00', '7000.00', '7000.00', '7000.00', 'WERTGDF', 'A005');
INSERT INTO CUSTOMER VALUES ('C00019', 'Yearannaidu', 'Chennai', 'Chennai', 'India', '1', '8000.00', '7000.00', '7000.00', '8000.00', 'ZZZZBFV', 'A010');
INSERT INTO CUSTOMER VALUES ('C00005', 'Sasikant', 'Mumbai', 'Mumbai', 'India', '1', '7000.00', '11000.00', '7000.00', '11000.00', '147-25896312', 'A002');
INSERT INTO CUSTOMER VALUES ('C00007', 'Ramanathan', 'Chennai', 'Chennai', 'India', '1', '7000.00', '11000.00', '9000.00', '9000.00', 'GHRDWSD', 'A010');
INSERT INTO CUSTOMER VALUES ('C00022', 'Avinash', 'Mumbai', 'Mumbai', 'India', '2', '7000.00', '11000.00', '9000.00', '9000.00', '113-12345678','A002');
INSERT INTO CUSTOMER VALUES ('C00004', 'Winston', 'Brisban', 'Brisban', 'Australia', '1', '5000.00', '8000.00', '7000.00', '6000.00', 'AAAAAAA', 'A005');
INSERT INTO CUSTOMER VALUES ('C00023', 'Karl', 'London', 'London', 'UK', '0', '4000.00', '6000.00', '7000.00', '3000.00', 'AAAABAA', 'A006');
INSERT INTO CUSTOMER VALUES ('C00006', 'Shilton', 'Torento', 'Torento', 'Canada', '1', '10000.00', '7000.00', '6000.00', '11000.00', 'DDDDDDD', 'A004');
INSERT INTO CUSTOMER VALUES ('C00010', 'Charles', 'Hampshair', 'Hampshair', 'UK', '3', '6000.00', '4000.00', '5000.00', '5000.00', 'MMMMMMM', 'A009');
INSERT INTO CUSTOMER VALUES ('C00012', 'Steven', 'San Jose', 'San Jose', 'USA', '1', '5000.00', '7000.00', '9000.00', '3000.00', 'KRFYGJK', 'A012');
INSERT INTO CUSTOMER VALUES ('C00008', 'Karolina', 'Torento', 'Torento', 'Canada', '1', '7000.00', '7000.00', '9000.00', '5000.00', 'HJKORED', 'A004');
INSERT INTO CUSTOMER VALUES ('C00003', 'Martin', 'Torento', 'Torento', 'Canada', '2', '8000.00', '7000.00', '7000.00', '8000.00', 'MJYURFD', 'A004');
INSERT INTO CUSTOMER VALUES ('C00009', 'Ramesh', 'Mumbai', 'Mumbai', 'India', '3', '8000.00', '7000.00', '3000.00', '12000.00', 'Phone No', 'A002');
INSERT INTO CUSTOMER VALUES ('C00014', 'Rangarappa', 'Bangalore', 'Bangalore', 'India', '2', '8000.00', '11000.00', '7000.00', '12000.00', 'AAAATGF', 'A001');
INSERT INTO CUSTOMER VALUES ('C00011', 'Sundariya', 'Chennai', 'Chennai', 'India', '3', '7000.00', '11000.00', '7000.00', '11000.00', 'PPHGRTS', 'A010');
   
   
INSERT INTO ORDERS VALUES('200100', '1000.00', '600.00','2008-08-01', 'C00013', 'A003', 'SOD');
INSERT INTO ORDERS VALUES('200110', '3000.00', '500.00','2008-04-15', 'C00019', 'A010', 'SOD');
INSERT INTO ORDERS VALUES('200107', '4500.00', '900.00','2008-08-30', 'C00007', 'A010', 'SOD');
INSERT INTO ORDERS VALUES('200113', '4000.00', '600.00','2008-06-10', 'C00022', 'A002', 'SOD');
INSERT INTO ORDERS VALUES('200102', '2000.00', '300.00','2008-05-25', 'C00012', 'A012', 'SOD');
INSERT INTO ORDERS VALUES('200114', '3500.00', '2000.00','2008-08-15', 'C00002', 'A008', 'SOD');
INSERT INTO ORDERS VALUES('200122', '2500.00', '400.00','2008-09-16', 'C00003', 'A004', 'SOD');
INSERT INTO ORDERS VALUES('200118', '500.00', '100.00','2008-07-20', 'C00023', 'A006', 'SOD');
INSERT INTO ORDERS VALUES('200119', '4000.00', '700.00','2008-09-16', 'C00007', 'A010', 'SOD');
INSERT INTO ORDERS VALUES('200121', '1500.00', '600.00','2008-09-23', 'C00008', 'A004', 'SOD');
INSERT INTO ORDERS VALUES('200130', '2500.00', '400.00','2008-07-30', 'C00025', 'A011', 'SOD');
INSERT INTO ORDERS VALUES('200134', '4200.00', '1800.00','2008-09-25', 'C00004', 'A005', 'SOD');
INSERT INTO ORDERS VALUES('200108', '4000.00', '600.00','2008-02-15', 'C00008', 'A004', 'SOD');
INSERT INTO ORDERS VALUES('200103', '1500.00', '700.00','2008-05-15', 'C00021', 'A005', 'SOD');
INSERT INTO ORDERS VALUES('200105', '2500.00', '500.00','2008-07-18', 'C00025', 'A011', 'SOD');
INSERT INTO ORDERS VALUES('200109', '3500.00', '800.00','2008-07-30', 'C00011', 'A010', 'SOD');
INSERT INTO ORDERS VALUES('200101', '3000.00', '1000.00','2008-07-15', 'C00001', 'A008', 'SOD');
INSERT INTO ORDERS VALUES('200111', '1000.00', '300.00','2008-07-10', 'C00020', 'A008', 'SOD');
INSERT INTO ORDERS VALUES('200104', '1500.00', '500.00','2008-03-13', 'C00006', 'A004', 'SOD');
INSERT INTO ORDERS VALUES('200106', '2500.00', '700.00','2008-04-20', 'C00005', 'A002', 'SOD');
INSERT INTO ORDERS VALUES('200125', '2000.00', '600.00','2008-10-10', 'C00018', 'A005', 'SOD');
INSERT INTO ORDERS VALUES('200117', '800.00', '200.00','2008-10-20', 'C00014', 'A001', 'SOD');
INSERT INTO ORDERS VALUES('200123', '500.00', '100.00','2008-09-16', 'C00022', 'A002', 'SOD');
INSERT INTO ORDERS VALUES('200120', '500.00', '100.00','2008-07-20', 'C00009', 'A002', 'SOD');
INSERT INTO ORDERS VALUES('200116', '500.00', '100.00','2008-07-13', 'C00010', 'A009', 'SOD');
INSERT INTO ORDERS VALUES('200126', '500.00', '100.00','2008-06-24', 'C00022', 'A002', 'SOD');
INSERT INTO ORDERS VALUES('200129', '2500.00', '500.00','2008-07-20', 'C00024', 'A006', 'SOD');
INSERT INTO ORDERS VALUES('200127', '2500.00', '400.00','2008-07-20', 'C00015', 'A003', 'SOD');
INSERT INTO ORDERS VALUES('200128', '3500.00', '1500.00','2008-07-20', 'C00009', 'A002', 'SOD');
INSERT INTO ORDERS VALUES('200135', '2000.00', '800.00','2008-09-16', 'C00007', 'A010', 'SOD');
INSERT INTO ORDERS VALUES('200131', '900.00', '150.00','2008-08-26', 'C00012', 'A012', 'SOD');
INSERT INTO ORDERS VALUES('200133', '1200.00', '400.00','2008-06-29', 'C00009', 'A002', 'SOD');
   

INSERT INTO COMP_PARENT ("PARENT_KEY_TEXT","PARENT_KEY_NBR","CREATED_USER","CREATED_AT","MODIFIED_USER","MODIFIED_AT","NULLABLETEXT","TEXT") VALUES('k1','1','222','2001-12-31T12:22:23.123Z','222','2001-12-31T12:22:23.123Z','','Text11111111');
INSERT INTO COMP_PARENT ("PARENT_KEY_TEXT","PARENT_KEY_NBR","CREATED_USER","CREATED_AT","MODIFIED_USER","MODIFIED_AT","NULLABLETEXT","TEXT") VALUES('k1','2','222','2001-12-31T12:22:23.123Z','222','2001-12-31T12:22:23.123Z','text2222','Text2');
INSERT INTO COMP_PARENT ("PARENT_KEY_TEXT","PARENT_KEY_NBR","CREATED_USER","CREATED_AT","MODIFIED_USER","MODIFIED_AT","NULLABLETEXT","TEXT") VALUES('k2','1','111','2001-12-31T12:22:23.123Z','222','2001-12-31T12:22:23.123Z','NotNullText','Text22');
INSERT INTO COMP_PARENT ("PARENT_KEY_TEXT","PARENT_KEY_NBR","CREATED_USER","CREATED_AT","MODIFIED_USER","MODIFIED_AT","NULLABLETEXT","TEXT") VALUES('k3','1','111','2001-12-31T12:22:23.123Z','222','2001-12-31T12:22:23.123Z','NotNullText','Text22');
INSERT INTO COMP_PARENT ("PARENT_KEY_TEXT","PARENT_KEY_NBR","CREATED_USER","CREATED_AT","MODIFIED_USER","MODIFIED_AT","NULLABLETEXT","TEXT") VALUES('k3','2','111','2001-12-31T12:22:23.123Z','222','2001-12-31T12:22:23.123Z','NotNullText','Text22');
INSERT INTO COMP_PARENT ("PARENT_KEY_TEXT","PARENT_KEY_NBR","CREATED_USER","CREATED_AT","MODIFIED_USER","MODIFIED_AT","NULLABLETEXT","TEXT") VALUES('k1','4','222','2001-12-31T12:22:23.123Z','222','2001-12-31T12:22:23.123Z','','Text4');
INSERT INTO COMP_PARENT ("PARENT_KEY_TEXT","PARENT_KEY_NBR","CREATED_USER","CREATED_AT","MODIFIED_USER","MODIFIED_AT","NULLABLETEXT","TEXT") VALUES('r','1','222','2001-12-31T12:22:23.123Z','222','2001-12-31T12:22:23.123Z','abcd','Text11111111');


INSERT INTO COMP_CHILD ("CHILD_KEY_TEXT","CHILD_KEY_NBR","CHILD_KEY_DATE_TIME","CHILD_TEXT","CHILD_MODIFIED_AT") VALUES('k1','2','2001-12-31','childText_k1_2_1','2001-12-31T12:22:23.123Z');
INSERT INTO COMP_CHILD ("CHILD_KEY_TEXT","CHILD_KEY_NBR","CHILD_KEY_DATE_TIME","CHILD_TEXT","CHILD_MODIFIED_AT") VALUES('k1','1','2001-12-30','childText_k1_1_1','2001-12-31T12:22:23.123Z');
INSERT INTO COMP_CHILD ("CHILD_KEY_TEXT","CHILD_KEY_NBR","CHILD_KEY_DATE_TIME","CHILD_TEXT","CHILD_MODIFIED_AT") VALUES('k2','1','2001-12-29','childText_k1_2_1','2001-12-31T12:22:23.123Z');
INSERT INTO COMP_CHILD ("CHILD_KEY_TEXT","CHILD_KEY_NBR","CHILD_KEY_DATE_TIME","CHILD_TEXT","CHILD_MODIFIED_AT") VALUES('k3','2','2001-12-28','K3_2_child_1_text','2001-12-31T12:22:23.123Z');
INSERT INTO COMP_CHILD ("CHILD_KEY_TEXT","CHILD_KEY_NBR","CHILD_KEY_DATE_TIME","CHILD_TEXT","CHILD_MODIFIED_AT") VALUES('k3','2','2001-12-27','K3_2_child_2_text','2001-12-31T12:22:23.123Z');
INSERT INTO COMP_CHILD ("CHILD_KEY_TEXT","CHILD_KEY_NBR","CHILD_KEY_DATE_TIME","CHILD_TEXT","CHILD_MODIFIED_AT") VALUES('r','1','2001-12-26','childText_k1_1_1','2001-12-31T12:22:23.123Z');

INSERT INTO COMP_GRAND ("GRAND_KEY_TEXT","GRAND_KEY_NBR","GRAND_KEY_DATE_TIME","GRAND_KEY2_TEXT","GRAND_TEXT") VALUES('k1','2','2001-12-31','grandK2','grandText_k1_2_1');
INSERT INTO COMP_GRAND ("GRAND_KEY_TEXT","GRAND_KEY_NBR","GRAND_KEY_DATE_TIME","GRAND_KEY2_TEXT","GRAND_TEXT") VALUES('k1','2','2001-12-31','grandK22','grandText_k1_2_2222');
INSERT INTO COMP_GRAND ("GRAND_KEY_TEXT","GRAND_KEY_NBR","GRAND_KEY_DATE_TIME","GRAND_KEY2_TEXT","GRAND_TEXT") VALUES('k1','2','2001-12-31','grandK3','grandText_k1_2_3');

DROP SCHEMA TEST_SCHEMA IF EXISTS;
CREATE SCHEMA TEST_SCHEMA;
DROP TABLE TEST_SCHEMA .AGENTS IF EXISTS;
CREATE TABLE  TEST_SCHEMA.AGENTS 
   (	
    "AGENT_CODE" CHAR(6) NOT NULL PRIMARY KEY, 
	"AGENT_NAME" CHAR(40), 
	"WORKING_AREA" CHAR(35), 
	"COMMISSION" NUMBER(10,2), 
	"PHONE_NO" CHAR(15), 
	"COUNTRY" VARCHAR2(25) 
	 );
INSERT INTO TEST_SCHEMA.AGENTS VALUES ('A007', 'Ramasundar', 'Bangalore', '0.15', '077-25814763', '');
INSERT INTO TEST_SCHEMA.AGENTS VALUES ('A003', 'Alex ', 'London', '0.13', '075-12458969', '');
	 
	 