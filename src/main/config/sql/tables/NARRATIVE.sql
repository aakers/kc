 CREATE TABLE NARRATIVE 
   (	PROPOSAL_NUMBER NUMBER(12,0) constraint NARRATIVEN1 NOT NULL ENABLE, 
	MODULE_NUMBER NUMBER(4,0) constraint NARRATIVEN2 NOT NULL ENABLE, 
	MODULE_SEQUENCE_NUMBER NUMBER(4,0) constraint NARRATIVEN3 NOT NULL ENABLE, 
	MODULE_TITLE VARCHAR2(150), 
	MODULE_STATUS_CODE VARCHAR2(3) constraint NARRATIVEN4 NOT NULL ENABLE, 
	CONTACT_NAME VARCHAR2(30), 
	PHONE_NUMBER VARCHAR2(20), 
	EMAIL_ADDRESS VARCHAR2(60), 
	COMMENTS VARCHAR2(300), 
	UPDATE_USER VARCHAR2(8) constraint NARRATIVEN5 NOT NULL ENABLE, 
	UPDATE_TIMESTAMP DATE constraint NARRATIVEN6 NOT NULL ENABLE, 
	NARRATIVE_TYPE_CODE NUMBER(3,0), 
	 VER_NBR NUMBER(8,0) DEFAULT 1  constraint  NARRATIVEN7  NOT NULL ENABLE,
	OBJ_ID VARCHAR2(36) DEFAULT SYS_GUID()  constraint  NARRATIVEN8  NOT NULL ENABLE,
	CONSTRAINT PK_NARRATIVE_KRA PRIMARY KEY (PROPOSAL_NUMBER, MODULE_NUMBER) ENABLE
) 
 

 ALTER TABLE NARRATIVE ADD (CONSTRAINT FK_NARRATIVE_KRA FOREIGN KEY (PROPOSAL_NUMBER)
	  REFERENCES EPS_PROPOSAL (PROPOSAL_NUMBER) ) 
 

 ALTER TABLE NARRATIVE ADD (CONSTRAINT FK_NARRATIVE_TYPE_CODE_KRA FOREIGN KEY (NARRATIVE_TYPE_CODE)
	  REFERENCES NARRATIVE_TYPE (NARRATIVE_TYPE_CODE) ) 

 ALTER TABLE NARRATIVE ADD (CONSTRAINT FK_NARRATIVE_STATUS_CODE_KRA FOREIGN KEY (MODULE_STATUS_CODE)
	  REFERENCES NARRATIVE_STATUS (NARRATIVE_STATUS_CODE) )
 