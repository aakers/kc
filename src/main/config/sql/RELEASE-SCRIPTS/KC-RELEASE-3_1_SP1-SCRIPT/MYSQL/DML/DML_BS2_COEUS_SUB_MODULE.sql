insert into COEUS_SUB_MODULE (COEUS_SUB_MODULE_ID , MODULE_CODE , SUB_MODULE_CODE , DESCRIPTION, UPDATE_TIMESTAMP ,UPDATE_USER, VER_NBR, OBJ_ID)   values 
( 1,3,1,'Proposal Budget',NOW(),'admin',1,UUID());      
 
insert into COEUS_SUB_MODULE (COEUS_SUB_MODULE_ID , MODULE_CODE , SUB_MODULE_CODE , DESCRIPTION, UPDATE_TIMESTAMP ,UPDATE_USER, VER_NBR, OBJ_ID)   values 
( 2,7,1,'Amendment / Renewal',NOW(),'admin',1,UUID());    

insert into COEUS_SUB_MODULE (COEUS_SUB_MODULE_ID , MODULE_CODE , SUB_MODULE_CODE , DESCRIPTION, UPDATE_TIMESTAMP ,UPDATE_USER, VER_NBR, OBJ_ID)   values 
( 3,7,2,'Protocol Submission',NOW(),'admin',1,UUID());        

INSERT INTO COEUS_SUB_MODULE ( COEUS_SUB_MODULE_ID, MODULE_CODE, SUB_MODULE_CODE, DESCRIPTION, UPDATE_TIMESTAMP, UPDATE_USER, VER_NBR, OBJ_ID )
VALUES ( 4, 3, 2, 'S2S Questionnaires', NOW(), 'admin', '0', UUID() );

COMMIT;
