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
package org.kuali.kra.workflow;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.bo.UnitAdministrator;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.service.UnitService;
import org.kuali.rice.kew.api.identity.Id;
import org.kuali.rice.kew.api.identity.PrincipalId;
import org.kuali.rice.kew.api.rule.RoleName;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.routeheader.DocumentContent;
import org.kuali.rice.kew.rule.GenericRoleAttribute;
import org.kuali.rice.kew.rule.QualifiedRoleName;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UnitAdministratorRoleAttribute extends GenericRoleAttribute {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2837085991084714072L;
    private static final String ROLE_NAME = "UnitAdministrator";
    private static final String UNIT_NODE_NAME = "leadUnitNumber";

    public List<String> getQualifiedRoleNames(String roleName, DocumentContent documentContent) {
        List<String> qualifiedRoleNames = new ArrayList<String>();
        qualifiedRoleNames.add(ROLE_NAME);
        
        return qualifiedRoleNames;
    }

    public List<RoleName> getRoleNames() {
        RoleName role = RoleName.Builder.create("org.kuali.kra.workflow.UnitAdministratorRoleAttribute", ROLE_NAME, ROLE_NAME).build();
        return Collections.singletonList(role);
    }
    
    @Override
    public Map<String, String> getProperties() {
        // intentionally unimplemented...not intending on using this attribute client-side
        return null;
    }

    private UnitService getUnitService() {
        return KraServiceLocator.getService(UnitService.class);
    }
    
    @Override
    protected List<Id> resolveRecipients(RouteContext routeContext, QualifiedRoleName qualifiedRoleName) {
        List<Id> members = new ArrayList<Id>();
        
        DocumentContent dc = routeContext.getDocumentContent();
        NodeList nodes = dc.getDocument().getElementsByTagName(UNIT_NODE_NAME);
        String unitNumber = nodes.item(0).getTextContent();
        if (StringUtils.isNotBlank(unitNumber)) {
            List<UnitAdministrator> unitAdministrators = getUnitService().retrieveUnitAdministratorsByUnitNumber(unitNumber);
            for ( UnitAdministrator unitAdministrator : unitAdministrators ) {
                if ( StringUtils.isNotBlank(unitAdministrator.getPersonId()) ) {
                    members.add( new PrincipalId(unitAdministrator.getPersonId()));
                }
            }
        }
        
        return members;
    }

}
