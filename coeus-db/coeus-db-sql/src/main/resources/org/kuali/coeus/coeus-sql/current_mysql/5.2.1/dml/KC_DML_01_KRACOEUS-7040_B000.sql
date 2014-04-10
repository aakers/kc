DELIMITER /

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_CODE, DESCRIPTION, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('30', 'Internal Contact', UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_CODE, DESCRIPTION, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('31', 'Subaward Administrative Contact 1', UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_CODE, DESCRIPTION, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('32', 'Subaward Financial Contact 1', UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_CODE, DESCRIPTION, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('33', 'Subaward Authorized Official', UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_CODE, DESCRIPTION, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('34', 'Prime Administrative Contact', UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_CODE, DESCRIPTION, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('35', 'Prime Financial Contact', UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_CODE, DESCRIPTION, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('36', 'Prime Authorized Official', UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_CODE, DESCRIPTION, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('37', 'Authorizing Official', UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_TYPE (CONTACT_TYPE_CODE, DESCRIPTION, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('38', 'Financial Contact', UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_USAGE (CONTACT_TYPE_CODE, MODULE_CODE, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('30', 4, UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_USAGE (CONTACT_TYPE_CODE, MODULE_CODE, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('31', 4, UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_USAGE (CONTACT_TYPE_CODE, MODULE_CODE, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('32', 4, UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_USAGE (CONTACT_TYPE_CODE, MODULE_CODE, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('33', 4, UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_USAGE (CONTACT_TYPE_CODE, MODULE_CODE, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('34', 4, UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_USAGE (CONTACT_TYPE_CODE, MODULE_CODE, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('35', 4, UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_USAGE (CONTACT_TYPE_CODE, MODULE_CODE, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('36', 4, UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_USAGE (CONTACT_TYPE_CODE, MODULE_CODE, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('37', 4, UUID(), SYSDATE(), 'admin', 1)
/

INSERT INTO CONTACT_USAGE (CONTACT_TYPE_CODE, MODULE_CODE, OBJ_ID, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR) 
VALUES ('38', 4, UUID(), SYSDATE(), 'admin', 1)
/

DELIMITER ;
