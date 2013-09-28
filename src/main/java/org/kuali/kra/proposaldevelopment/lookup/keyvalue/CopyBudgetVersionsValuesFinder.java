/*
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.proposaldevelopment.lookup.keyvalue;

import org.kuali.kra.budget.versions.BudgetDocumentVersion;
import org.kuali.kra.proposaldevelopment.bo.ProposalCopyCriteria;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.proposaldevelopment.web.struts.form.ProposalDevelopmentForm;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kns.util.KNSGlobalVariables;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

import java.util.ArrayList;
import java.util.List;

/**
 * See the method <code>getKeyValues()</code> for a full description.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public class CopyBudgetVersionsValuesFinder extends KeyValuesBase {
    
    /**
     * Gets the key/value pairs for copying budget versions.
     */
    public List<KeyValue> getKeyValues() {
        
        final List<KeyValue> keyValues = new ArrayList<KeyValue>();
        
        keyValues.add(new ConcreteKeyValue(ProposalCopyCriteria.BUDGET_ALL_VERSIONS, 
            ProposalCopyCriteria.BUDGET_ALL_VERSIONS));
        
        if (this.finalVersionPresent()) {
            keyValues.add(new ConcreteKeyValue(ProposalCopyCriteria.BUDGET_FINAL_VERSION, 
                ProposalCopyCriteria.BUDGET_FINAL_VERSION));            
        }
        
        return keyValues;
    }
    
    /**
     * Checks if a final budget version is present.
     *
     * <p>
     * Default visibility to allow for easier unit testing.
     * </p>
     * @return true if present false if not.
     */
    boolean finalVersionPresent() {
        
        final ProposalDevelopmentDocument document = this.getDocument();
        if (document != null) {
            for (final BudgetDocumentVersion overview : document.getBudgetDocumentVersions()) {
                if (overview.getBudgetVersionOverview().isFinalVersionFlag()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Gets the ProposalDevelopmentDocument.
     * <p>
     * Default visibility to allow for easier unit testing.
     * </p>
     * @return the ProposalDevelopmentDocument
     */
    ProposalDevelopmentDocument getDocument() {
        final ProposalDevelopmentForm form = (ProposalDevelopmentForm) KNSGlobalVariables.getKualiForm();
        if (form == null) {
            return null;
        }
        return form.getProposalDevelopmentDocument();
    }
}
