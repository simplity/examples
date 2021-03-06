DROP TABLE CSCHD_CONTRACT_HEADERS IF EXISTS;
DROP TABLE CSCBS_CONTRACT_BENCHS IF EXISTS;
DROP TABLE CSCLS_CONTRACT_LOCATIONS IF EXISTS;
DROP TABLE CSCRD_CONTRACT_ROLE_DETAILS IF EXISTS;
DROP TABLE CSCRT_CONTRACT_ROLE_TYPES IF EXISTS;
DROP TABLE CSCSG_CONTRACT_STORAGES IF EXISTS;

CREATE TABLE CSCHD_CONTRACT_HEADERS (
	CSCHD_PK NUMBER(16),
	TERMINAL VARCHAR(100),
	REGION NUMBER(16),
	ASSET_NAME VARCHAR(100),
	ASSET_OWNER VARCHAR(100),
	DEAL_NAME VARCHAR(100),
	DEAL_COUNTER_PARTY VARCHAR(100),
	BP_CONTRACT_NUM VARCHAR(100),
	EXTERNAL_CONTRACT_NUM VARCHAR(100),
	OTHER_REFERENCE_NUM VARCHAR(100),
	DESCRIPTION VARCHAR(100),
	BP_CONTRACTING_ENTITY VARCHAR(100),
	LEASE_TYPE VARCHAR(40),
	LEASE_PERCENTAGE NUMBER(5),
	STATUS VARCHAR(40),
	CONTRACT_SIGN_DATE DATE,
	CONTRACT_START_DATE DATE,
	CONTRACT_END_DATE DATE,
	CONTRACT_RENEWAL_DATE DATE,
	DURATION_MONTHS NUMBER(18),
	EXCESS_THROUGHPUT_RATE NUMBER(18),
	EXCESS_THROUGHPUT_RATE_UOM VARCHAR(20),
	THROUGPUT_PER_YEAR NUMBER(18),
	ECONS NUMBER(18),
	ECONS_UOM VARCHAR(40),
	NOTES VARCHAR(2000),
	CONTRACT_LINK VARCHAR(100),
	LOCK_NUM NUMBER(16) DEFAULT 1 ,
	IS_DELETED CHAR(1),
	CREATED_BY VARCHAR(10),
	CREATED_DATE DATE DEFAULT SYSDATE ,
	LAST_UPDATED_BY VARCHAR(10),
	LAST_UPDATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
	CONSTRAINT CSCHD_PK PRIMARY KEY (CSCHD_PK),
	CONSTRAINT CSCHD_UK01 UNIQUE (BP_CONTRACT_NUM)
);

CREATE TABLE CSCBS_CONTRACT_BENCHS (
	CSCBS_PK NUMBER(16),
	CONTRACT_CSCHD_FK NUMBER(16),
	BENCH NUMBER(16),
	LOCK_NUM NUMBER(16) DEFAULT 1 ,
	IS_DELETED CHAR(1),
	CREATED_BY VARCHAR(10),
	CREATED_DATE DATE DEFAULT SYSDATE ,
	LAST_UPDATED_BY VARCHAR(10),
	LAST_UPDATED_DATE DATE DEFAULT SYSDATE ,
	CONSTRAINT CSCBS_PK PRIMARY KEY (CSCBS_PK),
	CONSTRAINT CSCBS_CSCHD_FK01 FOREIGN KEY (CONTRACT_CSCHD_FK) REFERENCES CSCHD_CONTRACT_HEADERS(CSCHD_PK)
);

CREATE TABLE CSCLS_CONTRACT_LOCATIONS (
	CSCLS_PK NUMBER(16),
	CONTRACT_CSCHD_FK NUMBER(16),
	COUNTRY NUMBER(16),
	STATE VARCHAR(100),
	COUNTY VARCHAR(100),
	CITY VARCHAR(100),
	LOCK_NUM NUMBER(16) DEFAULT 1 ,
	IS_DELETED CHAR(1),
	CREATED_BY VARCHAR(10),
	CREATED_DATE DATE DEFAULT SYSDATE ,
	LAST_UPDATED_BY VARCHAR(10),
	LAST_UPDATED_DATE DATE DEFAULT SYSDATE ,
	CONSTRAINT CSCLS_PK PRIMARY KEY (CSCLS_PK),
	CONSTRAINT CSCLS_CSCHD_FK01 FOREIGN KEY (CONTRACT_CSCHD_FK) REFERENCES CSCHD_CONTRACT_HEADERS(CSCHD_PK)
);

CREATE TABLE CSCRT_CONTRACT_ROLE_TYPES (
	CSCRT_PK NUMBER(16),
	ROLE_NAME VARCHAR(100),
	IS_DELETED CHAR(1),
	CONSTRAINT CSCRT_PK PRIMARY KEY (CSCRT_PK)
);


CREATE TABLE CSCRD_CONTRACT_ROLE_DETAILS (
	CSCRD_PK NUMBER(16),
	CONTRACT_CSCHD_FK NUMBER(16),
	ROLE_CSCRT_FK NUMBER(16),
	PRIMARY_INDIVIDUAL VARCHAR(100),
	SECONDARY_INDIVIDUAL VARCHAR(100),
	LOCK_NUM NUMBER(16) DEFAULT 1 ,
	IS_DELETED CHAR(1),
	CREATED_BY VARCHAR(10),
	CREATED_DATE DATE DEFAULT SYSDATE ,
	LAST_UPDATED_BY VARCHAR(10),
	LAST_UPDATED_DATE DATE DEFAULT SYSDATE ,
	CONSTRAINT CSCRD_PK PRIMARY KEY (CSCRD_PK),
	CONSTRAINT CSCRD_CSCHD_FK01 FOREIGN KEY (CONTRACT_CSCHD_FK) REFERENCES CSCHD_CONTRACT_HEADERS(CSCHD_PK),
	CONSTRAINT CSCRD_CSCRT_FK01 FOREIGN KEY (ROLE_CSCRT_FK) REFERENCES CSCRT_CONTRACT_ROLE_TYPES(CSCRT_PK)
);


CREATE TABLE CSCSG_CONTRACT_STORAGES (
	CSCSG_PK NUMBER(16),
	CONTRACT_CSCHD_FK NUMBER(16),
	TANK_ID VARCHAR(100),
	DESCRIPTION VARCHAR(100),
	QUANTITY NUMBER(18),
	QUANTITY_UOM VARCHAR(40),
	STORAGE_RATE NUMBER(18),
	CURRENCY NUMBER(16),
	GRADE_GROUP VARCHAR(100),
	STORAGE_START_DATE DATE,
	STORAGE_END_DATE DATE,
	LOCK_NUM NUMBER(16) DEFAULT 1 ,
	IS_DELETED CHAR(1),
	CREATED_BY VARCHAR(10),
	CREATED_DATE DATE DEFAULT SYSDATE ,
	LAST_UPDATED_BY VARCHAR(10),
	LAST_UPDATED_DATE DATE DEFAULT SYSDATE ,
	CONSTRAINT CSCSG_PK PRIMARY KEY (CSCSG_PK),
	CONSTRAINT CSCSG_CSCHD_FK01 FOREIGN KEY (CONTRACT_CSCHD_FK) REFERENCES CSCHD_CONTRACT_HEADERS(CSCHD_PK)
);
