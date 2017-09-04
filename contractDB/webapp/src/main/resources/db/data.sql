/*Contract Headers*/
INSERT INTO CSCHD_CONTRACT_HEADERS (CSCHD_PK,TERMINAL,REGION,ASSET_NAME,ASSET_OWNER,DEAL_NAME,DEAL_COUNTER_PARTY,BP_CONTRACT_NUM,EXTERNAL_CONTRACT_NUM,OTHER_REFERENCE_NUM,DESCRIPTION,BP_CONTRACTING_ENTITY,LEASE_TYPE,LEASE_PERCENTAGE,STATUS,CONTRACT_SIGN_DATE,CONTRACT_START_DATE,CONTRACT_END_DATE,CONTRACT_RENEWAL_DATE,DURATION_MONTHS,EXCESS_THROUGHPUT_RATE,EXCESS_THROUGHPUT_RATE_UOM,THROUGPUT_PER_YEAR,ECONS,ECONS_UOM,NOTES,CONTRACT_LINK,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431923,'Enterprise ',1010003098000000,'Enterprise Houston Terminal-Changes','Enterprise','Enterprise Houston Crude','Enterprise Partners, LLC','Ent Houston OTI','1-hou-gcrude-12','na','Enterprise Houston Storage','BPPNA','Operating',20,'LIVE','2016-01-01','2018-09-01','2025-08-31',null,85.2,0.5,'M3',12,40000000,'NPV','Note',null,1,'N','SYSTEM','2017-08-21','SYSTEM','2017-08-21');
INSERT INTO CSCHD_CONTRACT_HEADERS (CSCHD_PK,TERMINAL,REGION,ASSET_NAME,ASSET_OWNER,DEAL_NAME,DEAL_COUNTER_PARTY,BP_CONTRACT_NUM,EXTERNAL_CONTRACT_NUM,OTHER_REFERENCE_NUM,DESCRIPTION,BP_CONTRACTING_ENTITY,LEASE_TYPE,LEASE_PERCENTAGE,STATUS,CONTRACT_SIGN_DATE,CONTRACT_START_DATE,CONTRACT_END_DATE,CONTRACT_RENEWAL_DATE,DURATION_MONTHS,EXCESS_THROUGHPUT_RATE,EXCESS_THROUGHPUT_RATE_UOM,THROUGPUT_PER_YEAR,ECONS,ECONS_UOM,NOTES,CONTRACT_LINK,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431924,'Magellan',1010003098000000,'Magellan Cushing','Magellan','Cushing Magellan','Magellan Midstream, LLC','Mag - Cushing','BP - Cushing','na','Cushing Storage','BPPNA','Operating',30,'LIVE', '2010-09-01', '2010-09-01', '2025-07-31',null,181.57,0.31,'BBL',15,77000000,'NPV','Former USPL site sold to Magellan 2010',null,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCHD_CONTRACT_HEADERS (CSCHD_PK,TERMINAL,REGION,ASSET_NAME,ASSET_OWNER,DEAL_NAME,DEAL_COUNTER_PARTY,BP_CONTRACT_NUM,EXTERNAL_CONTRACT_NUM,OTHER_REFERENCE_NUM,DESCRIPTION,BP_CONTRACTING_ENTITY,LEASE_TYPE,LEASE_PERCENTAGE,STATUS,CONTRACT_SIGN_DATE,CONTRACT_START_DATE,CONTRACT_END_DATE,CONTRACT_RENEWAL_DATE,DURATION_MONTHS,EXCESS_THROUGHPUT_RATE,EXCESS_THROUGHPUT_RATE_UOM,THROUGPUT_PER_YEAR,ECONS,ECONS_UOM,NOTES,CONTRACT_LINK,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431925,'Plains',1010003098000000,'Plains Cushing','Plains','Cushing Plains','Plains Midstream, LLC','Plains - Cushing','5717-11-09-0127','na','Cushing Storage','BPPNA','Operating',25,'LIVE', '2007-05-30', '2009-10-01', '2019-09-30',null,121.7,0.5,'BBL',15,57000000,'NPV','Added two new tanks 2017',null,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCHD_CONTRACT_HEADERS (CSCHD_PK,TERMINAL,REGION,ASSET_NAME,ASSET_OWNER,DEAL_NAME,DEAL_COUNTER_PARTY,BP_CONTRACT_NUM,EXTERNAL_CONTRACT_NUM,OTHER_REFERENCE_NUM,DESCRIPTION,BP_CONTRACTING_ENTITY,LEASE_TYPE,LEASE_PERCENTAGE,STATUS,CONTRACT_SIGN_DATE,CONTRACT_START_DATE,CONTRACT_END_DATE,CONTRACT_RENEWAL_DATE,DURATION_MONTHS,EXCESS_THROUGHPUT_RATE,EXCESS_THROUGHPUT_RATE_UOM,THROUGPUT_PER_YEAR,ECONS,ECONS_UOM,NOTES,CONTRACT_LINK,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431926,'Plains',1010003098000000,'Plains St. James','Plains','St. James ','Plains Midstream, LLC','Plains - St. James','5717-11-09-0127','na','St. James Storage','BPPNA','Operating',50,'LIVE', '2013-06-15', '2013-10-01', '2018-09-30',null,60.83,0.47,'BBL',12,47753665,'NPV','Note',null,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCHD_CONTRACT_HEADERS (CSCHD_PK,TERMINAL,REGION,ASSET_NAME,ASSET_OWNER,DEAL_NAME,DEAL_COUNTER_PARTY,BP_CONTRACT_NUM,EXTERNAL_CONTRACT_NUM,OTHER_REFERENCE_NUM,DESCRIPTION,BP_CONTRACTING_ENTITY,LEASE_TYPE,LEASE_PERCENTAGE,STATUS,CONTRACT_SIGN_DATE,CONTRACT_START_DATE,CONTRACT_END_DATE,CONTRACT_RENEWAL_DATE,DURATION_MONTHS,EXCESS_THROUGHPUT_RATE,EXCESS_THROUGHPUT_RATE_UOM,THROUGPUT_PER_YEAR,ECONS,ECONS_UOM,NOTES,CONTRACT_LINK,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431927,'Enterprise ',1010003098000000,'Enterprise BMW','Enterprise','Enterprise Beaumont Marine West','Enterprise Partners, LLC','Ent BMW','BMW - 5763 - 2210','na','Beaumont','BPPNA','Operating',60,'LIVE', '2014-08-06', '2016-04-01', '2023-03-31',null,85.17,0.55,'BBL',15,63500000,'NPV','Note',null,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');

/*Locations*/
INSERT INTO CSCLS_CONTRACT_LOCATIONS (CSCLS_PK,CONTRACT_CSCHD_FK,COUNTRY,STATE,COUNTY,CITY,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431958,2000000003431925,1010002001001403,'Oklahoma','Payne','Magellan',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCLS_CONTRACT_LOCATIONS (CSCLS_PK,CONTRACT_CSCHD_FK,COUNTRY,STATE,COUNTY,CITY,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431959,2000000003431924,1010002001001403,'Oklahoma','Payne','Cushing',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCLS_CONTRACT_LOCATIONS (CSCLS_PK,CONTRACT_CSCHD_FK,COUNTRY,STATE,COUNTY,CITY,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431960,2000000003431925,1010002001001403,'Texas','Harris','Beaumont',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCLS_CONTRACT_LOCATIONS (CSCLS_PK,CONTRACT_CSCHD_FK,COUNTRY,STATE,COUNTY,CITY,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431961,2000000003431926,1010002001001403,'Oklahoma','Payne','Cushing',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCLS_CONTRACT_LOCATIONS (CSCLS_PK,CONTRACT_CSCHD_FK,COUNTRY,STATE,COUNTY,CITY,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431962,2000000003431925,1010002001001403,'Oklahoma','Jefferson','Beaumont',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCLS_CONTRACT_LOCATIONS (CSCLS_PK,CONTRACT_CSCHD_FK,COUNTRY,STATE,COUNTY,CITY,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431963,2000000003431927,1010002001001403,'Oklahoma','Payne','Cushing',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');

/*Benches*/
INSERT INTO CSCBS_CONTRACT_BENCHS (CSCBS_PK,CONTRACT_CSCHD_FK,BENCH,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431964,2000000003431925,1010003073000004,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCBS_CONTRACT_BENCHS (CSCBS_PK,CONTRACT_CSCHD_FK,BENCH,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431965,2000000003431924,1010003073000004,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCBS_CONTRACT_BENCHS (CSCBS_PK,CONTRACT_CSCHD_FK,BENCH,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431966,2000000003431925,1010003073000004,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCBS_CONTRACT_BENCHS (CSCBS_PK,CONTRACT_CSCHD_FK,BENCH,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431967,2000000003431926,1010003073000004,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCBS_CONTRACT_BENCHS (CSCBS_PK,CONTRACT_CSCHD_FK,BENCH,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431968,2000000003431925,1010003073000004,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCBS_CONTRACT_BENCHS (CSCBS_PK,CONTRACT_CSCHD_FK,BENCH,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431969,2000000003431927,1010003073000004,1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');

/*Storages*/
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431936,2000000003431926,'5040','Segregated',480000,'BBL',0.47,1010003029000024,'Crude', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431937,2000000003431926,'5060','Segregated',670000,'BBL',0.47,1010003029000024,'ULSD', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431938,2000000003431926,'5200','Segregated',275000,'BBL',0.47,1010003029000024,'Biodiesel', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431939,2000000003431926,'5290','Segregated',275000,'BBL',0.47,1010003029000024,'Crude', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431940,2000000003431923,'390-1','Segregated',390000,'BBL',0.55,1010003029000024,'ETHANOL', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431941,2000000003431923,'390-2','Segregated',390000,'BBL',0.55,1010003029000024,'MTBE', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431942,2000000003431923,'390-3','Segregated',390000,'BBL',0.55,1010003029000024,'BIODIESEL', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431943,2000000003431923,'390-4','Segregated',390000,'BBL',0.65,1010003029000024,'FAME', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431944,2000000003431924,'300-1','Segregated',300000,'BBL',0.31,1010003029000024,'Crude', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431945,2000000003431924,'300-2','Segregated',300000,'BBL',0.31,1010003029000024,'FAME', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431946,2000000003431924,'5001','Segregated',500000,'BBL',0.31,1010003029000024,'MTBE', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431947,2000000003431924,'5002','Segregated',500000,'BBL',0.31,1010003029000024,'Diluent', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431948,2000000003431924,'250-1','Segregated',250000,'BBL',0.31,1010003029000024,'MTBE', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431949,2000000003431924,'250-2','Segregated',250000,'BBL',0.31,1010003029000024,'Diluent', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431950,2000000003431924,'various','commingled',1000000,'BBL',0.39,1010003029000024,'Biodiesel', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431951,2000000003431925,'1850','Segregated',270000,'BBL',0.27,1010003029000024,'Biodiesel', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431952,2000000003431925,'1851','Segregated',270000,'BBL',0.27,1010003029000024,'Curde', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431953,2000000003431925,'1860','Segregated',270000,'BBL',0.27,1010003029000024,'MTBE', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431954,2000000003431925,'5001','Segregated',500000,'BBL',0.27,1010003029000024,'Curde', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431955,2000000003431925,'5002','Segregated',500000,'BBL',0.27,1010003029000024,'MTBE', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431956,2000000003431925,'5003','Segregated',500000,'BBL',0.27,1010003029000024,'Curde', '2013-10-01', '2013-10-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCSG_CONTRACT_STORAGES (CSCSG_PK,CONTRACT_CSCHD_FK,TANK_ID,DESCRIPTION,QUANTITY,QUANTITY_UOM,STORAGE_RATE,CURRENCY,GRADE_GROUP,STORAGE_START_DATE,STORAGE_END_DATE,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431957,2000000003431925,'5004','Segregated',500000,'BBL',0.27,1010003029000024,'ULSD', '2016-10-13', '2016-12-01',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');

/*Role Types*/
INSERT INTO CSCRT_CONTRACT_ROLE_TYPES (CSCRT_PK,ROLE_NAME,IS_DELETED) VALUES (2000000003431928,'Originator','N');
INSERT INTO CSCRT_CONTRACT_ROLE_TYPES (CSCRT_PK,ROLE_NAME,IS_DELETED) VALUES (2000000003431929,'Bench Owner','N');
INSERT INTO CSCRT_CONTRACT_ROLE_TYPES (CSCRT_PK,ROLE_NAME,IS_DELETED) VALUES (2000000003431930,'Commerical Developer','N');
INSERT INTO CSCRT_CONTRACT_ROLE_TYPES (CSCRT_PK,ROLE_NAME,IS_DELETED) VALUES (2000000003431931,'Strategic change','N');

/*Roles Details*/
INSERT INTO CSCRD_CONTRACT_ROLE_DETAILS (CSCRD_PK,CONTRACT_CSCHD_FK,ROLE_CSCRT_FK,PRIMARY_INDIVIDUAL,SECONDARY_INDIVIDUAL,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431932,2000000003431926,2000000003431928,'Recktenwall','Chung',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCRD_CONTRACT_ROLE_DETAILS (CSCRD_PK,CONTRACT_CSCHD_FK,ROLE_CSCRT_FK,PRIMARY_INDIVIDUAL,SECONDARY_INDIVIDUAL,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431933,2000000003431923,2000000003431929,'Pederson','Recktenwall',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCRD_CONTRACT_ROLE_DETAILS (CSCRD_PK,CONTRACT_CSCHD_FK,ROLE_CSCRT_FK,PRIMARY_INDIVIDUAL,SECONDARY_INDIVIDUAL,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431934,2000000003431924,2000000003431928,'Recktenwall','Chung',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');
INSERT INTO CSCRD_CONTRACT_ROLE_DETAILS (CSCRD_PK,CONTRACT_CSCHD_FK,ROLE_CSCRT_FK,PRIMARY_INDIVIDUAL,SECONDARY_INDIVIDUAL,LOCK_NUM,IS_DELETED,CREATED_BY,CREATED_DATE,LAST_UPDATED_BY,LAST_UPDATED_DATE) VALUES (2000000003431935,2000000003431925,2000000003431929,'Pederson','Recktenwall',1,'N','SYSTEM', '2017-08-21','SYSTEM', '2017-08-21');

