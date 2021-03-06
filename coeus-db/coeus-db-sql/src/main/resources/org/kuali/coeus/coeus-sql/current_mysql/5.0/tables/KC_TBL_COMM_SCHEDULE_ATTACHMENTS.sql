DELIMITER /
CREATE TABLE COMM_SCHEDULE_ATTACHMENTS (
	ATTACHMENT_ID DECIMAL(3,0) NOT NULL, 
	DESCRIPTION VARCHAR(200),
	FILE_NAME VARCHAR(300),
	ATTACHMENT BLOB,
	MIME_TYPE VARCHAR(100),
	UPDATE_TIMESTAMP DATE,
	UPDATE_USER VARCHAR(60),
	VER_NBR DECIMAL(8,0) DEFAULT 1 NOT NULL,
	OBJ_ID VARCHAR(36) NOT NULL,
	SCHEDULE_ID_FK DECIMAL(12,0),
	ATTACHMENT_TYPE_CODE VARCHAR(20), 
	NEW_UPDATE_TIMESTAMP DATE,
	NEW_UPDATE_USER VARCHAR(60)) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/
ALTER TABLE COMM_SCHEDULE_ATTACHMENTS ADD CONSTRAINT COMM_SCHEDULE_ATTACHMENTS_PK
PRIMARY KEY (ATTACHMENT_ID)
/
DELIMITER ;
