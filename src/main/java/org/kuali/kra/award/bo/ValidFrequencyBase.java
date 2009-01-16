/*
 * Copyright 2006-2008 The Kuali Foundation
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
package org.kuali.kra.award.bo;

import java.util.LinkedHashMap;

import org.kuali.kra.bo.KraPersistableBusinessObjectBase;

/**
 * 
 * This class represents the ValidFrequencyBase business object.
 */
public class ValidFrequencyBase extends KraPersistableBusinessObjectBase { 
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2875079003137515732L;
    private Integer validFrequencyBaseId; 
    private String frequencyCode; 
    private String frequencyBaseCode; 
    
    private Frequency frequency; 
    private FrequencyBase frequencyBase; 
    
    /**
     * 
     * Constructs a ValidFrequencyBase.java.
     */
    public ValidFrequencyBase() { 

    } 
    
    /**
     *
     * @return
     */
    public Integer getValidFrequencyBaseId() {
        return validFrequencyBaseId;
    }

    /**
     * 
     * @param validFrequencyBaseId
     */
    public void setValidFrequencyBaseId(Integer validFrequencyBaseId) {
        this.validFrequencyBaseId = validFrequencyBaseId;
    }

    /**
     * 
     * @return
     */
    public String getFrequencyCode() {
        return frequencyCode;
    }

    /**
     * 
     * @param frequencyCode
     */
    public void setFrequencyCode(String frequencyCode) {
        this.frequencyCode = frequencyCode;
    }

    /**
     * 
     * @return
     */
    public String getFrequencyBaseCode() {
        return frequencyBaseCode;
    }

    /**
     * 
     * @param frequencyBaseCode
     */
    public void setFrequencyBaseCode(String frequencyBaseCode) {
        this.frequencyBaseCode = frequencyBaseCode;
    }

    /**
     * 
     * @return
     */
    public Frequency getFrequency() {
        return frequency;
    }

    /**
     * 
     * @param frequency
     */
    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    /**
     * 
     * @return
     */
    public FrequencyBase getFrequencyBase() {
        return frequencyBase;
    }

    /**
     * 
     * @param frequencyBase
     */
    public void setFrequencyBase(FrequencyBase frequencyBase) {
        this.frequencyBase = frequencyBase;
    }

    /**
     * 
     * @see org.kuali.core.bo.BusinessObjectBase#toStringMapper()
     */
    @Override 
    protected LinkedHashMap<String,Object> toStringMapper() {
        LinkedHashMap<String,Object> hashMap = new LinkedHashMap<String,Object>();
        hashMap.put("validFrequencyBaseId", getValidFrequencyBaseId());
        hashMap.put("frequencyCode", getFrequencyCode());
        hashMap.put("frequencyBaseCode", getFrequencyBaseCode());
        return hashMap;
    }
    
}