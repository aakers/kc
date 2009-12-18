CREATE TABLE PROTOCOL_STATUS (
    PROTOCOL_STATUS_CODE VARCHAR2 (3) NOT NULL, 
    DESCRIPTION VARCHAR2 (200) NOT NULL, 
    UPDATE_TIMESTAMP DATE NOT NULL, 
    UPDATE_USER VARCHAR2 (60) NOT NULL, 
    VER_NBR NUMBER (8, 0) DEFAULT 1 NOT NULL, 
    OBJ_ID VARCHAR2 (36) DEFAULT SYS_GUID () NOT NULL) ;

ALTER TABLE PROTOCOL_STATUS 
    ADD CONSTRAINT PK_PROTOCOL_STATUS 
            PRIMARY KEY (PROTOCOL_STATUS_CODE) ;