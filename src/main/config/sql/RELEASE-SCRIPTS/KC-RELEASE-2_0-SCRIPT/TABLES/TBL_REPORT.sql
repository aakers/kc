CREATE TABLE REPORT (
    VER_NBR NUMBER (8, 0) DEFAULT 1 NOT NULL, 
    OBJ_ID VARCHAR2 (36) DEFAULT SYS_GUID () NOT NULL, 
    REPORT_CODE VARCHAR2 (3) NOT NULL, 
    DESCRIPTION VARCHAR2 (200) NOT NULL, 
    FINAL_REPORT_FLAG CHAR (1) NOT NULL, 
    UPDATE_TIMESTAMP DATE NOT NULL, 
    UPDATE_USER VARCHAR2 (60) NOT NULL) ;

ALTER TABLE REPORT 
    ADD CONSTRAINT PK_REPORT 
            PRIMARY KEY (REPORT_CODE) ;