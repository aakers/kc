CREATE TABLE SUBAWARD_FORMS 
   (	FORM_ID VARCHAR2(30 BYTE) NOT NULL, 
	DESCRIPTION VARCHAR2(200 BYTE) NOT NULL, 
	UPDATE_TIMESTAMP DATE NOT NULL, 
	UPDATE_USER VARCHAR2(60 BYTE) NOT NULL , 
	FORM BLOB, 
    FILE_NAME VARCHAR2(150 BYTE), 
	CONTENT_TYPE VARCHAR2(255 BYTE),
	VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL , 
	OBJ_ID VARCHAR2(36 BYTE) NOT NULL 
  )
/
ALTER TABLE SUBAWARD_FORMS
  ADD CONSTRAINT PK_SUBAWARD_FORMS PRIMARY KEY (FORM_ID)
/
