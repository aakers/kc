INSERT INTO SUBMISSION_TYPE_QUALIFIER (SUBMISSION_TYPE_QUAL_CODE, DESCRIPTION, UPDATE_TIMESTAMP,UPDATE_USER) 
VALUES ('1', 'Modification/Amendment/Revisions/Significant New Finding', sysdate,'KRADEV');

INSERT INTO VALID_PROTO_SUB_TYPE_QUAL (VALID_PROTO_SUB_TYPE_QUAL_ID, SUBMISSION_TYPE_CODE, SUBMISSION_TYPE_QUAL_CODE, UPDATE_TIMESTAMP, UPDATE_USER ) 
VALUES (SEQ_VALID_SUBM_REVW_TYPE_QUAL.nextval, 112, 1, sysdate, 'KRADEV'); 

