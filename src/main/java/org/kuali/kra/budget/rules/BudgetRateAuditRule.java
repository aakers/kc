/*
 * Copyright 2006-2008 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.budget.rules;

import static org.kuali.core.util.GlobalVariables.getAuditErrorMap;
import java.util.ArrayList;
import java.util.List;

import org.kuali.core.document.Document;
import org.kuali.core.rule.DocumentAuditRule;
import org.kuali.core.util.AuditCluster;
import org.kuali.core.util.AuditError;
import org.kuali.kra.budget.document.BudgetDocument;
import org.kuali.kra.budget.service.BudgetRatesService;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KeyConstants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.rules.ResearchDocumentRuleBase;

public class BudgetRateAuditRule  extends ResearchDocumentRuleBase implements DocumentAuditRule {
    private static final org.apache.commons.logging.Log LOG = org.apache.commons.logging.LogFactory.getLog(BudgetRateAuditRule.class);
    private static final String BUDGET_RATE_AUDIT_WARNING_KEY = "budgetRateAuditWarnings";

    /**
     * 
     * This method is to validate budget period start/end date against project start/end date if
     * project start/end date have been adjusted.
     */
    public boolean processRunAuditBusinessRules(Document document) {
        BudgetDocument budgetDocument = (BudgetDocument) document;
        boolean retval = true;
        if (KraServiceLocator.getService(BudgetRatesService.class).isOutOfSyncForRateAudit(budgetDocument)) {
            retval = false;
        }
        
        return retval;

    }
    
    /**
     * This method should only be called if an audit error is intending to be added because it will actually add a <code>{@link List<AuditError>}</code>
     * to the auditErrorMap.
     *  TODO : should this method move up to parent class
     * @return List of AuditError instances
     */
    private List<AuditError> getAuditErrors() {
        List<AuditError> auditErrors = new ArrayList<AuditError>();
        
        if (!getAuditErrorMap().containsKey(BUDGET_RATE_AUDIT_WARNING_KEY)) {
            getAuditErrorMap().put(BUDGET_RATE_AUDIT_WARNING_KEY, new AuditCluster(Constants.BUDGET_RATE_PANEL_NAME, auditErrors, Constants.AUDIT_WARNINGS));
        }
        else {
            auditErrors = ((AuditCluster) getAuditErrorMap().get(BUDGET_RATE_AUDIT_WARNING_KEY)).getAuditErrorList();
        }
        
        return auditErrors;
    }

}

