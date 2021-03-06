DELIMITER /
--
-- Copyright 2005-2014 The Kuali Foundation
--
-- Licensed under the Educational Community License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.opensource.org/licenses/ecl2.php
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

alter table KREW_PPL_FLW_MBR_T add column ACTN_RQST_PLCY_CD VARCHAR(1)
/
alter table KREW_PPL_FLW_MBR_T add column RSP_ID VARCHAR(40) NOT NULL
/

alter table KREW_PPL_FLW_DLGT_T add column ACTN_RQST_PLCY_CD VARCHAR(1)
/
alter table KREW_PPL_FLW_DLGT_T add column RSP_ID VARCHAR(40) NOT NULL
/
DELIMITER ;
