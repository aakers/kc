set define off
set sqlblanklines on
spool KC-RELEASE-5_0_1-Upgrade-ORACLE-Install.log
@../../current/5.0.1/sequences/KC_SEQ_BUD_FRMLTD_COST_DETAIL_ID.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_BATCH_CORRESPONDENCE_DETAIL.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROC_CAT_CUSTOM_DATA.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROC_PERSON_RESPONSIBLE.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROTOCOL_STUDY_GROUPS.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROTOCOL_STUDY_GROUP_DTL.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROTOCOL_STUDY_GROUP_HDR.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROTO_AMEND_RENEWAL.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROTO_AMEND_RENEW_MOD.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROTO_CORRESP_TEMPL.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROTO_STUDY_CUSTOM_DATA.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROTO_STUDY_GROUP_LOCS.sql
@../../current/5.0.1/sequences/KC_SEQ_IACUC_PROTO_SUBMIS_DOC_ID.sql
@../../current/5.0.1/sequences/KC_SEQ_PERSON_TRAINING_ID.sql
@../../current/5.0.1/sequences/KC_SEQ_UNIT_FRMULATED_COST_ID.sql
@../../current/5.0.1/tables/KC_TBL_BUDGET_DETAILS.sql
@../../current/5.0.1/tables/KC_TBL_BUD_FORMULATED_COST_DETAIL.sql
@../../current/5.0.1/tables/KC_TBL_COI_DISCLOSURE.sql
@../../current/5.0.1/tables/KC_TBL_COI_DISCL_PROJECTS.sql
@../../current/5.0.1/tables/KC_TBL_COI_USER_ROLES.sql
@../../current/5.0.1/tables/KC_TBL_COMM_BATCH_CORRESP.sql
@../../current/5.0.1/tables/KC_TBL_COMM_BATCH_CORRESP_DETAIL.sql
@../../current/5.0.1/tables/KC_TBL_COMM_SCHEDULE_MINUTES.sql
@../../current/5.0.1/tables/KC_TBL_FORMULATED_TYPE.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_BATCH_CORRESPONDENCE.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_BATCH_CORRESP_DETAIL.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_LOCATION_NAME.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_LOCATION_TYPE.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PAIN_CATEGORY.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PERSON_TRAINING.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PRINCIPLES.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROC_CAT_CUSTOM_DATA.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROC_PERSON_RESPONSIBLE.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_ACTIONS.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_ACTION_TYPE.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_ALT_SEARCH.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_CONTINGENCY.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_DOCUMENT.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_STUDY_GROUPS.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_STUDY_GROUP_DTL.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_STUDY_GROUP_HDR.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_SUBMISSION_DOC.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTOCOL_UNITS.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTO_AMEND_RENEWAL.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTO_AMEND_RENEW_MOD.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTO_CORRESPONDENCE.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTO_STUDY_CUSTOM_DATA.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_PROTO_STUDY_GROUP_LOCS.sql
@../../current/5.0.1/tables/KC_TBL_IACUC_RESEARCH_AREAS.sql
@../../current/5.0.1/tables/KC_TBL_PROPOSAL_NOTEPAD.sql
@../../current/5.0.1/tables/KC_TBL_PROTOCOL_VOTE_ABSTAINEES.sql
@../../current/5.0.1/tables/KC_TBL_PROTOCOL_VOTE_RECUSED.sql
@../../current/5.0.1/tables/KC_TBL_QUESTIONNAIRE_ANSWER_HEADER.sql
@../../current/5.0.1/tables/KC_TBL_QUESTIONNAIRE_QUESTIONS.sql
@../../current/5.0.1/tables/KC_TBL_UNIT_FORMULATED_COST.sql
@../../current/5.0.1/tables/KC_TBL_VALID_IACUC_PROTO_ACTN_ACTN.sql
@../../current/5.0.1/dml/KC_DML_01_KCCOI-253_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCCOI-332_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-113_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-126_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-159_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-164_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-170_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-193_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-197_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-214_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-250_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-277_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-278_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-285_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-286_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-287_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-288_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-289_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-290_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-291_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-297_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-307_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-310_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-311_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-312_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-313_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-314_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-315_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-316_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-317_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-319_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-321_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-324_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-378_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-379_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-400_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-408_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-419_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-425_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-55_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-68_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-69_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC-80_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIAC_338_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIRB-1697_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIRB-1779_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KCIRB-1844_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KRACOEUS-5232_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KRACOEUS-5446_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KRACOEUS-5561_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KRACOEUS-5562_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KRACOEUS-5583_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KRACOEUS-5593_B000.sql
@../../current/5.0.1/dml/KC_DML_01_KRACOEUS-5610_B000.sql
@../../current/5.0.1/dml/KC_DML_02_KCIAC-340_B000.sql
@../../current/5.0.1/dml/KC_DML_03_KRACOEUS-5493_B000.sql
@../../current/5.0.1/dml/KC_DML_04_KRACOEUS-5493_B000.sql
@../../current/5.0.1/dml/KC_DML_04_KRACOEUS-5571_B000.sql
@../../current/5.0.1/dml/KC_DML_04_KRACOEUS-5572_B000.sql
@../../current/5.0.1/dml/KC_DML_04_KRACOEUS-5573_B000.sql
@../../current/5.0.1/dml/KC_DML_05_KRACOEUS-5636_B000.sql
@../../current/5.0.1/constraints/KC_FK_BUD_FRMLTD_COST_DETAIL.sql
@../../current/5.0.1/constraints/KC_FK_COMM_BATCH_CORRESP.sql
@../../current/5.0.1/constraints/KC_FK_COMM_BATCH_CORRESP_DETAIL.sql
@../../current/5.0.1/constraints/KC_FK_COMM_SCHEDULE_MINUTES.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_BATCH_CORRESPONDENCE.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_BATCH_CORRESP_DETAIL.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_PERSON_TRAINING.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_PROC_PERSON_RESPONSIBLE.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_PROTOCOL_RESEARCH_AREAS.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_PROTOCOL_STUDY_GROUPS.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_PROTOCOL_STUDY_GROUP_DTL.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_PROTOCOL_STUDY_GROUP_HDR.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_PROTO_AMEND_RENEWAL.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_PROTO_AMEND_RENEW_MOD.sql
@../../current/5.0.1/constraints/KC_FK_IACUC_PROTO_STUDY_GROUP_LOCS.sql
@../../current/5.0.1/constraints/KC_FK_PROTOCOL_VOTE_ABSTAINEES.sql
@../../current/5.0.1/constraints/KC_FK_PROTOCOL_VOTE_RECUSED.sql
@../../current/5.0.1/constraints/KC_FK_UNIT_FORMULATED_COST.sql
@../../current/5.0.1/constraints/KC_FK_VALID_IACUC_PROTO_ACTN_ACTN.sql
@../../current/5.0.1/constraints/KC_UQ_IACUC_PERSON_TRAINING.sql
@../../current/5.0.1/constraints/KC_UQ_IACUC_PROC_PERSON_RESPONSIBLE.sql
@../../current/5.0.1/constraints/KC_UQ_IACUC_PROTO_AMEND_RENEW_MOD.sql
@../../current/5.0.1/constraints/KC_UQ_IACUC_PROTO_STUDY_GROUP_LOCS.sql
commit;
exit
