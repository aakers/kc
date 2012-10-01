DELIMITER /
ALTER TABLE IACUC_PROTO_AMEND_RENEW_MOD 
ADD CONSTRAINT FK_IACUC_PROTO_AMEND_RENEW_MOD 
FOREIGN KEY (PROTOCOL_MODULE_CODE) 
REFERENCES IACUC_PROTOCOL_MODULES (PROTOCOL_MODULE_CODE)
/

ALTER TABLE IACUC_PROTO_AMEND_RENEW_MOD 
ADD CONSTRAINT FK_IACUC_PROTO_AMEND_REN_ID 
FOREIGN KEY (PROTO_AMEND_RENEWAL_ID) 
REFERENCES IACUC_PROTO_AMEND_RENEWAL (PROTO_AMEND_RENEWAL_ID)
/


DELIMITER ;
