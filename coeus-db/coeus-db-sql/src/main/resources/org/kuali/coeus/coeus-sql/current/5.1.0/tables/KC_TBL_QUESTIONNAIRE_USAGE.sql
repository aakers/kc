alter table QUESTIONNAIRE_USAGE drop column RULE_ID
/

alter table QUESTIONNAIRE_USAGE add RULE_ID VARCHAR2(40)
/

update QUESTIONNAIRE_USAGE set RULE_ID = null where RULE_ID = 0
/
