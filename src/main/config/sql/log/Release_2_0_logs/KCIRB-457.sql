CREATE OR REPLACE VIEW OSP$PROTOCOL_DOCUMENTS AS 
    SELECT pap.PROTOCOL_NUMBER, pap.SEQUENCE_NUMBER, pap.TYPE_CD, pap.DESCRIPTION, paf.FILE_NAME, paf.FILE_DATA,
    pap.UPDATE_TIMESTAMP, pap.UPDATE_USER, paf.SEQUENCE_NUMBER AS VERSION_NUMBER, pap.STATUS_CD, pap.DOCUMENT_ID
    FROM PROTOCOL_ATTACHMENT_PROTOCOL pap, PROTOCOL_ATTACHMENT_FILE paf
    WHERE pap.FILE_ID = paf.PA_FILE_ID AND pap.TYPE_CD = '9';
