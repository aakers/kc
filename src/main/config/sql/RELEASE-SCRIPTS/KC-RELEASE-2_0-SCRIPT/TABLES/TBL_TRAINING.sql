CREATE TABLE TRAINING (
    TRAINING_CODE NUMBER (4, 0) NOT NULL, 
    DESCRIPTION VARCHAR2 (200) NOT NULL, 
    UPDATE_TIMESTAMP DATE NOT NULL, 
    UPDATE_USER VARCHAR2 (60) NOT NULL, 
    VER_NBR NUMBER (8, 0) DEFAULT 1 NOT NULL, 
    OBJ_ID VARCHAR2 (36) DEFAULT SYS_GUID () NOT NULL) ;

ALTER TABLE TRAINING 
    ADD CONSTRAINT PK_TRAINING 
            PRIMARY KEY (TRAINING_CODE) ;