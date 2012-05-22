/*
 * Copyright 2005-2010 The Kuali Foundation
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
package org.kuali.kra.iacuc;

import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.Query;
import org.apache.ojb.broker.query.QueryFactory;
import org.apache.ojb.broker.query.ReportQueryByCriteria;
import org.kuali.kra.iacuc.actions.submit.IacucProtocolSubmission;
import org.kuali.kra.protocol.Protocol;
import org.kuali.kra.protocol.actions.submit.ProtocolSubmission;
import org.kuali.rice.core.framework.persistence.ojb.dao.PlatformAwareDaoBaseOjb;

/**
 * The ProtocolFinderDao implementation for OJB.
 */
public class IacucProtocolFinderDaoOjb extends PlatformAwareDaoBaseOjb implements IacucProtocolFinderDao {

    private static final String PROTOCOL_NUMBER = "protocolNumber";
    private static final String SEQUENCE_NUMBER = "sequenceNumber";
    private static final String MAX_SEQUENCE_NUMBER = "max(" + SEQUENCE_NUMBER + ")";
    private static final String SUBMISSION_NUMBER = "submissionNumber"; 

    /**
     * @see org.kuali.kra.protocol.ProtocolFinderDao#findCurrentProtocolByNumber(java.lang.String)
     */
    public IacucProtocol findCurrentProtocolByNumber(String protocolNumber) {
        
        Criteria subCrit = new Criteria();
        subCrit.addEqualTo(PROTOCOL_NUMBER, protocolNumber);
        ReportQueryByCriteria subQuery = QueryFactory.newReportQuery(IacucProtocol.class, subCrit);
        subQuery.setAttributes(new String[] { MAX_SEQUENCE_NUMBER });

        Criteria crit = new Criteria();
        crit.addEqualTo(PROTOCOL_NUMBER, protocolNumber);
        crit.addEqualTo(SEQUENCE_NUMBER, subQuery);
        Query q = QueryFactory.newQuery(IacucProtocol.class, crit);

        return (IacucProtocol) getPersistenceBrokerTemplate().getObjectByQuery(q);
    }
    
    
    /**
     * 
     * @see org.kuali.kra.protocol.IacucProtocolFinderDao#findProtocolSubmissions(java.lang.String, int)
     */
    @SuppressWarnings("unchecked")
    public List<ProtocolSubmission> findProtocolSubmissions(String protocolNumber, int submissionNumber) {
        
        Criteria crit = new Criteria();
        crit.addLike(PROTOCOL_NUMBER, protocolNumber + "%");
        crit.addEqualTo(SUBMISSION_NUMBER, submissionNumber);
        Query q = QueryFactory.newQuery(IacucProtocolSubmission.class, crit);

        return (List<ProtocolSubmission>) getPersistenceBrokerTemplate().getCollectionByQuery(q);
    }

    /**
     * 
     * @see org.kuali.kra.protocol.ProtocolFinderDao#findProtocols(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<Protocol> findProtocols(String protocolNumber) {
        
        Criteria crit = new Criteria();
        crit.addLike(PROTOCOL_NUMBER, protocolNumber + "%");
        Query q = QueryFactory.newQuery(IacucProtocol.class, crit);

        return (List<Protocol>) getPersistenceBrokerTemplate().getCollectionByQuery(q);
    }

}
