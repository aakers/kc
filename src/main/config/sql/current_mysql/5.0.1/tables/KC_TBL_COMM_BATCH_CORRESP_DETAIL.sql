DELIMITER /
ALTER TABLE
  COMM_BATCH_CORRESP_DETAIL 
DROP INDEX UQ_COMM_BATCH_CORRESP_DETAIL
/

ALTER TABLE
  COMM_BATCH_CORRESP_DETAIL 
MODIFY
  PROTOCOL_ACTION_ID DECIMAL(12,0) NULL
/

ALTER TABLE
  COMM_BATCH_CORRESP_DETAIL 
MODIFY
  PROTOCOL_CORRESPONDENCE_ID DECIMAL(12,0) NULL
/

ALTER TABLE
  COMM_BATCH_CORRESP_DETAIL 
ADD
(
  IACUC_PROT_CORRESPONDENCE_ID DECIMAL(12,0) NULL,
  IACUC_PROTOCOL_ACTION_ID DECIMAL(12,0) NULL
)
/

DELIMITER ;
