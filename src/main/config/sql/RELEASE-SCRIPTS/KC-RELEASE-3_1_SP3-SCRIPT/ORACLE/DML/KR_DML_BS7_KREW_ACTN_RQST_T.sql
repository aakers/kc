-- KEW data for Proposal Development Person Certification Questionnaire

SET DEFINE OFF;

INSERT INTO KREW_ACTN_RQST_T (ACTN_RQST_ID,ACTN_RQST_CD,DOC_HDR_ID,STAT_CD,RSP_ID,PRNCPL_ID,RECIP_TYP_CD,PRIO_NBR,RTE_LVL_NBR,RTE_NODE_INSTN_ID,ACTN_TKN_ID,DOC_VER_NBR,CRTE_DT,RSP_DESC_TXT,FRC_ACTN,APPR_PLCY,CUR_IND,VER_NBR) 
  VALUES (KREW_ACTN_RQST_S.NEXTVAL,'C',(SELECT DOC_HDR_ID FROM KREW_DOC_HDR_T WHERE TTL = 'Proposal Person Certification'),'D',-3,'10000000001','U',0,0,(SELECT RTE_NODE_INSTN_ID FROM KREW_RTE_NODE_INSTN_T WHERE DOC_HDR_ID = (SELECT DOC_HDR_ID FROM KREW_DOC_HDR_T WHERE TTL = 'Proposal Person Certification')),(SELECT ACTN_TKN_ID FROM KREW_ACTN_TKN_T WHERE DOC_HDR_ID = (SELECT DOC_HDR_ID FROM KREW_DOC_HDR_T WHERE TTL = 'Proposal Person Certification') AND ACTN_CD = 'C'),1,TO_TIMESTAMP('08-JUL-10','DD-MON-RR HH.MI.SSXFF AM'),'Initiator needs to complete document.',1,'F',1,0);

COMMIT;