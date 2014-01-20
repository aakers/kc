-- save old table
create table PERSON_EDITABLE_FIELDS_old as select * from PERSON_EDITABLE_FIELDS;

-- drop and recreate table
drop table PERSON_EDITABLE_FIELDS;
CREATE TABLE PERSON_EDITABLE_FIELDS (
    PERSON_EDITABLE_FIELD_ID   DECIMAL(12,0) NOT NULL,
    MODULE_CODE         DECIMAL(3,0) NOT NULL,
    FIELD_NAME          VARCHAR(255) NOT NULL,
    ACTIVE_FLAG         CHAR(1) NULL,
    UPDATE_TIMESTAMP    DATE NOT NULL,
    UPDATE_USER         VARCHAR(60) NOT NULL,
    VER_NBR             DECIMAL(8,0) DEFAULT 1 NOT NULL,
    OBJ_ID              VARCHAR(36) NOT NULL);


ALTER TABLE PERSON_EDITABLE_FIELDS 
ADD CONSTRAINT PK_PERSON_EDITABLE_FIELDS 
PRIMARY KEY (PERSON_EDITABLE_FIELD_ID);

-- restore data for PD and add data for protocol.
DROP PROCEDURE IF EXISTS pef3;
DELIMITER //
CREATE PROCEDURE pef3()
BEGIN
IF EXISTS (SELECT * FROM PERSON_EDITABLE_FIELDS_old) THEN
    set @pefnum := (SELECT MAX(ID) FROM SEQ_PERSON_EDITABLE_FIELD);
    insert into PERSON_EDITABLE_FIELDS(PERSON_EDITABLE_FIELD_ID, MODULE_CODE, FIELD_NAME, ACTIVE_FLAG, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR, OBJ_ID)
     select (select @pefnum := if(@pefnum is null,1001,@pefnum + 1)), '3', FIELD_NAME, ACTIVE_FLAG, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR, OBJ_ID from PERSON_EDITABLE_FIELDS_old;
    insert into SEQ_PERSON_EDITABLE_FIELD values (@pefnum + 1);
END IF;
END //
DELIMITER ;
call pef3 ();
DROP PROCEDURE IF EXISTS pef3;

DROP PROCEDURE IF EXISTS pef7;
DELIMITER //
CREATE PROCEDURE pef7()
BEGIN
IF EXISTS (SELECT * FROM PERSON_EDITABLE_FIELDS_old) THEN
    set @pefnum := (SELECT MAX(ID) FROM SEQ_PERSON_EDITABLE_FIELD);
    insert into PERSON_EDITABLE_FIELDS(PERSON_EDITABLE_FIELD_ID, MODULE_CODE, FIELD_NAME, ACTIVE_FLAG, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR, OBJ_ID)
     select (select @pefnum := if(@pefnum is null,1001,@pefnum + 1)), '7', FIELD_NAME, ACTIVE_FLAG, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR, UUID() from PERSON_EDITABLE_FIELDS_old;
    insert into SEQ_PERSON_EDITABLE_FIELD values (@pefnum + 1);
END IF;
END //
DELIMITER ;
call pef7 ();
DROP PROCEDURE IF EXISTS pef7;

commit;

-- drop old table
drop table PERSON_EDITABLE_FIELDS_old;