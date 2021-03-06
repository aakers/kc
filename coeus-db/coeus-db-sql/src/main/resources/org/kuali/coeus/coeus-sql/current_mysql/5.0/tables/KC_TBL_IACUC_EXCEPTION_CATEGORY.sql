DELIMITER /
CREATE TABLE IACUC_EXCEPTION_CATEGORY ( 
    EXCEPTION_CATEGORY_CODE DECIMAL(3,0) NOT NULL, 
    EXCEPTION_CATEGORY_DESC VARCHAR(200) NOT NULL, 
    ACTIVE_FLAG CHAR(1) NOT NULL, 
    UPDATE_TIMESTAMP DATE NOT NULL, 
    UPDATE_USER VARCHAR(60) NOT NULL, 
    VER_NBR DECIMAL(8,0) DEFAULT 1 NOT NULL, 
    OBJ_ID VARCHAR(36) NOT NULL) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/


ALTER TABLE IACUC_EXCEPTION_CATEGORY 
ADD CONSTRAINT PK_IACUC_EXCEPTION_CATEGORY 
PRIMARY KEY (EXCEPTION_CATEGORY_CODE) 
/

DELIMITER ;
