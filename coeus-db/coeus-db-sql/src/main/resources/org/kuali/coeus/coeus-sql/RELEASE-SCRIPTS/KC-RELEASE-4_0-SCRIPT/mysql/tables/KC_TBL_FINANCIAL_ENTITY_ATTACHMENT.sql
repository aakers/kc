DELIMITER /
CREATE TABLE FINANCIAL_ENTITY_ATTACHMENT
   (FINANCIAL_ENTITY_ATTACHMENT_ID DECIMAL(12,0) NOT NULL, 
    FINANCIAL_ENTITY_ID_FK DECIMAL(12,0) NOT NULL,
    FILE_ID DECIMAL(12,0) NOT NULL,
    DESCRIPTION VARCHAR(200) NOT NULL, 
    CONTACT_NAME VARCHAR(30),
    EMAIL_ADDRESS VARCHAR(60),
    PHONE_NUMBER VARCHAR(20),
    COMMENTS VARCHAR(300),
    OBJ_ID VARCHAR(36) NOT NULL,
    UPDATE_TIMESTAMP DATE NOT NULL,
    UPDATE_USER VARCHAR(60) NOT NULL
    ) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/
DELIMITER ;
