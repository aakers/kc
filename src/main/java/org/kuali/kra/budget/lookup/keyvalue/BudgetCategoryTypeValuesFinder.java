/*
 * Copyright 2008 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
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
package org.kuali.kra.budget.lookup.keyvalue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.kuali.core.lookup.keyvalues.KeyValuesBase;
import org.kuali.core.service.KeyValuesService;
import org.kuali.core.util.GlobalVariables;
import org.kuali.core.web.struts.form.KualiForm;
import org.kuali.core.web.ui.KeyLabelPair;
import org.kuali.kra.budget.bo.BudgetCategoryType;
import org.kuali.kra.budget.bo.BudgetLineItem;
import org.kuali.kra.budget.web.struts.form.BudgetForm;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.lookup.keyvalue.KeyValueFinderService;
import org.kuali.kra.proposaldevelopment.bo.ProposalType;

public class BudgetCategoryTypeValuesFinder extends KeyValuesBase {
    KeyValueFinderService keyValueFinderService= (KeyValueFinderService)KraServiceLocator.getService("keyValueFinderService");
    
    /**
     * Constructs the list of Budget Periods.  Each entry
     * in the list is a &lt;key, value&gt; pair, where the "key" is the unique
     * status code and the "value" is the textual description that is viewed
     * by a user.  The list is obtained from the BudgetDocument if any are defined there. 
     * Otherwise, it is obtained from a lookup of the BUDGET_PERIOD database table
     * via the "KeyValueFinderService".
     * 
     * @return the list of &lt;key, value&gt; pairs of abstract types.  The first entry
     * is always &lt;"", "select:"&gt;.
     * @see org.kuali.core.lookup.keyvalues.KeyValuesFinder#getKeyValues()
     */
    public List<KeyLabelPair> getKeyValues() {
        KeyValuesService keyValuesService = (KeyValuesService) KraServiceLocator.getService("keyValuesService");
        Collection budgetCategoryTypes = keyValuesService.findAllOrderBy(BudgetCategoryType.class,"sortId",true);
        List<KeyLabelPair> keyValues = new ArrayList<KeyLabelPair>();  
        
        KualiForm form = GlobalVariables.getKualiForm();
        //Temporary Hack for KRACOEUS-944
        if(form instanceof BudgetForm){
            BudgetForm budgetForm = (BudgetForm)form;
            for (Iterator iter = budgetCategoryTypes.iterator(); iter.hasNext();) {
                BudgetCategoryType budgetCategoryType = (BudgetCategoryType) iter.next();
                keyValues.add(new KeyLabelPair(budgetCategoryType.getBudgetCategoryTypeCode().toString(), budgetCategoryType.getDescription()));
                budgetForm.getBudgetDocument().getBudgetCategoryTypes().add(budgetCategoryType);            
            }
        }else{
            for (Iterator iter = budgetCategoryTypes.iterator(); iter.hasNext();) {
                BudgetCategoryType budgetCategoryType = (BudgetCategoryType) iter.next();
                keyValues.add(new KeyLabelPair(budgetCategoryType.getBudgetCategoryTypeCode().toString(), budgetCategoryType.getDescription()));                            
            }
        }
                
        return keyValues;
    }
   
}
