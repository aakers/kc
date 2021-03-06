DELIMITER /
CREATE TABLE NEGOTIATION_ACTIVITY (
	NEGOTIATION_ACTIVITY_ID			DECIMAL(22) NOT NULL,
	NEGOTIATION_ID					DECIMAL(22) NOT NULL,
	LOCATION_ID						DECIMAL(22) NOT NULL,
	ACTIVITY_TYPE_ID				DECIMAL(22) NOT NULL,
	START_DATE						DATE NOT NULL,
	END_DATE						DATE,
	CREATE_DATE						DATE NOT NULL,
	FOLLOWUP_DATE					DATE,
	LAST_MODIFIED_USER				VARCHAR(60) NOT NULL,
	LAST_MODIFIED_DATE				DATE NOT NULL,
	DESCRIPTION						VARCHAR(2000),
	RESTRICTED						CHAR(1) NOT NULL,
	UPDATE_TIMESTAMP     			DATE NOT NULL,
	UPDATE_USER          			VARCHAR(60) NOT NULL,
	VER_NBR              			DECIMAL(8,0) NOT NULL,
	OBJ_ID               			VARCHAR(36) NOT NULL
) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin
/

ALTER TABLE NEGOTIATION_ACTIVITY
  ADD CONSTRAINT NEGOTIATION_ACTIVITY_PK1
  PRIMARY KEY (NEGOTIATION_ACTIVITY_ID)
/
DELIMITER ;
