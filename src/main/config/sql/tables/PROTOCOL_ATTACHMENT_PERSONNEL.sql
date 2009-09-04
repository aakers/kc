CREATE TABLE PROTOCOL_ATTACHMENT_PERSONNEL
   (PA_PERSONNEL_ID NUMBER(12,0) NOT NULL ENABLE,
    PROTOCOL_ID_FK NUMBER(12,0) NOT NULL, 
	PROTOCOL_NUMBER VARCHAR2(20) NOT NULL, 
    SEQUENCE_NUMBER NUMBER(4,0) NOT NULL ENABLE,
    TYPE_CD VARCHAR2(3) NOT NULL ENABLE,
    DOCUMENT_ID NUMBER(4,0) NOT NULL ENABLE,
    FILE_ID NUMBER(12,0) NOT NULL ENABLE,
    DESCRIPTION VARCHAR2(200), 
    PERSON_ID NUMBER(12,0) NOT NULL ENABLE,
    VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL ENABLE,
    OBJ_ID VARCHAR2(36) DEFAULT SYS_GUID()  NOT NULL ENABLE,
    UPDATE_TIMESTAMP DATE NOT NULL ENABLE,
    UPDATE_USER VARCHAR2(10) NOT NULL ENABLE);
);

ALTER TABLE PROTOCOL_ATTACHMENT_PERSONNEL
    ADD CONSTRAINT PK_PROTOCOL_ATTACH_PERSONNEL
    PRIMARY KEY (PA_PERSONNEL_ID);

ALTER TABLE PROTOCOL_ATTACHMENT_PERSONNEL
    ADD CONSTRAINT FK_PA_PERSONNEL_FILE 
    FOREIGN KEY (FILE_ID) 
    REFERENCES ATTACHMENT_FILE (FILE_ID)
    ON DELETE CASCADE;

ALTER TABLE PROTOCOL_ATTACHMENT_PERSONNEL
    ADD CONSTRAINT FK_PERSON 
    FOREIGN KEY (PERSON_ID) 
    REFERENCES PROTOCOL_PERSONS (PROTOCOL_PERSON_ID);
    
ALTER TABLE PROTOCOL_ATTACHMENT_PERSONNEL
    ADD CONSTRAINT FK_PA_PERSONNEL_TYPE 
    FOREIGN KEY (TYPE_CD) 
    REFERENCES PROTOCOL_ATTACHMENT_TYPE (TYPE_CD);

ALTER TABLE PROTOCOL_ATTACHMENT_PERSONNEL 
	ADD CONSTRAINT FK_PA_PERSONNEL_PROTOCOL 
	FOREIGN KEY (PROTOCOL_ID_FK) 
	REFERENCES PROTOCOL (PROTOCOL_ID);