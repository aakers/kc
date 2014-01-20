TRUNCATE TABLE MINUTE_ENTRY_TYPE DROP STORAGE
/
INSERT INTO MINUTE_ENTRY_TYPE (MINUTE_ENTRY_TYPE_CODE,DESCRIPTION,SORT_ID,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('1','General Comments',1,'admin',SYSDATE,SYS_GUID(),1)
/
INSERT INTO MINUTE_ENTRY_TYPE (MINUTE_ENTRY_TYPE_CODE,DESCRIPTION,SORT_ID,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('2','Attendance',2,'admin',SYSDATE,SYS_GUID(),1)
/
INSERT INTO MINUTE_ENTRY_TYPE (MINUTE_ENTRY_TYPE_CODE,DESCRIPTION,SORT_ID,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('3','Protocol',3,'admin',SYSDATE,SYS_GUID(),1)
/
INSERT INTO MINUTE_ENTRY_TYPE (MINUTE_ENTRY_TYPE_CODE,DESCRIPTION,SORT_ID,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('4','Other Business',4,'admin',SYSDATE,SYS_GUID(),1)
/
INSERT INTO MINUTE_ENTRY_TYPE (MINUTE_ENTRY_TYPE_CODE,DESCRIPTION,SORT_ID,UPDATE_USER,UPDATE_TIMESTAMP,OBJ_ID,VER_NBR) 
    VALUES ('5','Adverse Events',5,'admin',SYSDATE,SYS_GUID(),1)
/
