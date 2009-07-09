CREATE TABLE INSTITUTE_PROPOSAL_DOCUMENT (
DOCUMENT_NUMBER VARCHAR2(10) NOT NULL,
VER_NBR NUMBER(8,0) DEFAULT 1 NOT NULL,
OBJ_ID VARCHAR2(36) DEFAULT SYS_GUID()  NOT NULL,
UPDATE_TIMESTAMP DATE NOT NULL,
UPDATE_USER VARCHAR2(10) NOT NULL);

ALTER TABLE INSTITUTE_PROPOSAL_DOCUMENT
ADD CONSTRAINT PK_INSTITUTE_PROPOSAL_DOCUMENT
PRIMARY KEY (DOCUMENT_NUMBER);

