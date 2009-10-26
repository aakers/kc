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
package org.kuali.kra.s2s.generator.impl;

import static org.kuali.kra.s2s.util.S2SConstants.FORMVERSION_1_3;
import gov.grants.apply.forms.phs398Checklist13V13.PHS398Checklist13Document;
import gov.grants.apply.forms.phs398Checklist13V13.PHS398Checklist13Document.PHS398Checklist13;
import gov.grants.apply.forms.phs398Checklist13V13.PHS398Checklist13Document.PHS398Checklist13.ApplicationType;
import gov.grants.apply.forms.phs398Checklist13V13.PHS398Checklist13Document.PHS398Checklist13.IncomeBudgetPeriod;
import gov.grants.apply.system.globalLibraryV20.HumanNameDataType;
import gov.grants.apply.system.globalLibraryV20.YesNoDataType;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlObject;
import org.kuali.kra.budget.distributionincome.BudgetProjectIncome;
import org.kuali.kra.budget.document.BudgetDocument;
import org.kuali.kra.proposaldevelopment.bo.DevelopmentProposal;
import org.kuali.kra.proposaldevelopment.bo.ProposalYnq;
import org.kuali.kra.proposaldevelopment.document.ProposalDevelopmentDocument;
import org.kuali.kra.s2s.util.S2SConstants;

;

/**
 * 
 * This class is used to generate XML Document object for grants.gov
 * PHS398ChecklistV1.3. This form is generated using XMLBean API's generated by
 * compiling PHS398ChecklistV1.1 schema.
 * 
 * @author Kuali Research Administration Team (kualidev@oncourse.iu.edu)
 */
public class PHS398ChecklistV1_3Generator extends PHS398ChecklistBaseGenerator {
	private static final String YNQANSWER_29 = "29";
	private static final Logger LOG = Logger
			.getLogger(PHS398ChecklistV1_3Generator.class);

	/*
	 * This method returns PHS398ChecklistDocument object based on proposal
	 * development document which contains the PHS398ChecklistDocument
	 * informations
	 * ApplicationType,FederalID,ChangeOfPDPI,FormerPDName,ChangeOfInstitution,
	 * FormerInstitutionName,InventionsAndPatents, ProgramIncome and
	 * CertificationExplanation for a particular proposal
	 * 
	 */
	private PHS398Checklist13Document getPHS398Checklist() {
		PHS398Checklist13Document phsChecklistDocument = PHS398Checklist13Document.Factory
				.newInstance();
		PHS398Checklist13 phsChecklist = PHS398Checklist13.Factory
				.newInstance();
		setPhsCheckListBasicProperties(phsChecklist);
		setFormerPDNameAndIsChangeOfPDPI(phsChecklist);
		setFormerInstitutionNameAndChangeOfInstitution(phsChecklist);
		setIsInventionsAndPatentsAndIsPreviouslyReported(phsChecklist);
		BudgetDocument budgetDoc = null;
		try {
			budgetDoc = s2sBudgetCalculatorService.getFinalBudgetVersion(pdDoc);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}

		if (budgetDoc != null && budgetDoc.getBudget() != null) {
			setIncomeBudgetPeriods(phsChecklist, budgetDoc.getBudget()
					.getBudgetProjectIncomes());
		} else {
			phsChecklist.setProgramIncome(YesNoDataType.N_NO);
		}
		phsChecklist.setDisclosurePermission(getYNQAnswer(YNQANSWER_29));
		phsChecklistDocument.setPHS398Checklist13(phsChecklist);
		return phsChecklistDocument;
	}

	/*
	 * This method will set values to following attributes setFederalID,
	 * setApplicationType ,setFormVersion
	 */
	private void setPhsCheckListBasicProperties(PHS398Checklist13 phsChecklist) {
		phsChecklist.setFormVersion(FORMVERSION_1_3);
		DevelopmentProposal developmentProposal = pdDoc
				.getDevelopmentProposal();
		ApplicationType.Enum applicationEnum = null;
		if (developmentProposal.getProposalTypeCode() != null
				&& Integer.parseInt(developmentProposal.getProposalTypeCode()) < PROPOSAL_TYPE_CODE_6) {
			applicationEnum = ApplicationType.Enum.forInt(Integer
					.valueOf(developmentProposal.getProposalTypeCode()));
		}
		phsChecklist.setApplicationType(applicationEnum);
		String federalId = s2sUtilService.getFederalId(pdDoc);
		if (federalId != null
				&& !federalId.equals(S2SConstants.FEDERAL_ID_NOT_FOUND)) {
			phsChecklist.setFederalID(federalId);
		}
	}

	/*
	 * This method will set values to income budget periods
	 */
	private void setIncomeBudgetPeriods(PHS398Checklist13 phsChecklist,
			List<BudgetProjectIncome> projectIncomes) {
		phsChecklist.setProgramIncome(YesNoDataType.Y_YES);
		IncomeBudgetPeriod incomeBudgPeriod = IncomeBudgetPeriod.Factory
				.newInstance();
		IncomeBudgetPeriod[] budgetPeriodsArray = null;
		if (projectIncomes != null) {
			budgetPeriodsArray = new IncomeBudgetPeriod[projectIncomes.size()];
		}
		int periodCount = 0;
		BigDecimal amount = BigDecimal.ZERO;
		for (BudgetProjectIncome projectIncome : projectIncomes) {
			if (projectIncome.getProjectIncome() != null) {
				amount = amount.add(projectIncome.getProjectIncome()
						.bigDecimalValue());
			}
			incomeBudgPeriod.setAnticipatedAmount(amount);
			String description = getProjectIncomeDescription(projectIncome);
			if (description != null) {
				incomeBudgPeriod.setSource(description);
			}
			incomeBudgPeriod.setBudgetPeriod(projectIncome
					.getBudgetPeriodNumber());
			if (budgetPeriodsArray != null) {
				budgetPeriodsArray[periodCount] = incomeBudgPeriod;
			}
			periodCount++;
		}
		phsChecklist.setIncomeBudgetPeriodArray(budgetPeriodsArray);
	}

	/*
	 * This method will get the project income description
	 */
	private String getProjectIncomeDescription(BudgetProjectIncome projectIncome) {
		String description = null;
		if (projectIncome.getDescription() != null) {
			if (projectIncome.getDescription().length() > PROJECT_INCOME_DESCRIPTION_MAX_LENGTH) {
				description = projectIncome.getDescription().substring(0,
						PROJECT_INCOME_DESCRIPTION_MAX_LENGTH);
			} else {
				description = projectIncome.getDescription();
			}
		}
		return description;
	}

	/*
	 * This method will set the values to
	 * setIsInventionsAndPatents,setIsPreviouslyReported based on condition
	 */
	private void setIsInventionsAndPatentsAndIsPreviouslyReported(
			PHS398Checklist13 phsChecklist) {
		DevelopmentProposal developmentProposal = pdDoc
				.getDevelopmentProposal();
		for (ProposalYnq proposalYnq : developmentProposal.getProposalYnqs()) {
			if (proposalYnq.getQuestionId() != null
					&& proposalYnq.getQuestionId().equals(
							PROPOSAL_YNQ_QUESTION_16)) {
				String answer = proposalYnq.getAnswer();
				if (S2SConstants.PROPOSAL_YNQ_ANSWER_Y.equals(answer)) {
					phsChecklist.setIsInventionsAndPatents(YesNoDataType.Y_YES);
					phsChecklist.setIsPreviouslyReported(YesNoDataType.Y_YES);
				} else if (S2SConstants.PROPOSAL_YNQ_ANSWER_NA.equals(answer)) {
					phsChecklist.setIsInventionsAndPatents(YesNoDataType.N_NO);
				} else {
					phsChecklist.setIsInventionsAndPatents(YesNoDataType.Y_YES);
					phsChecklist.setIsPreviouslyReported(YesNoDataType.N_NO);
				}
			}
		}
	}

	/*
	 * This method will set the values to setFormerInstitutionName
	 * ,setIsChangeOfInstitution based on condition
	 */
	private void setFormerInstitutionNameAndChangeOfInstitution(
			PHS398Checklist13 phsChecklist) {
		DevelopmentProposal developmentProposal = pdDoc
				.getDevelopmentProposal();
		for (ProposalYnq proposalYnq : developmentProposal.getProposalYnqs()) {
			if (proposalYnq.getQuestionId() != null
					&& proposalYnq.getQuestionId().equals(
							PROPOSAL_YNQ_QUESTION_23)) {
				String answer = proposalYnq.getAnswer();
				String explanation = proposalYnq.getExplanation();

				if (S2SConstants.PROPOSAL_YNQ_ANSWER_Y.equals(answer)) {
					phsChecklist.setIsChangeOfInstitution(YesNoDataType.Y_YES);
					if (explanation != null) {
						phsChecklist.setFormerInstitutionName(explanation);
					}
				} else {
					phsChecklist.setIsChangeOfInstitution(YesNoDataType.N_NO);
				}
			}
		}
	}

	/*
	 * This method will set the values to setFormerPDName ,setIsChangeOfPDPI
	 * based on condition
	 */
	private void setFormerPDNameAndIsChangeOfPDPI(PHS398Checklist13 phsChecklist) {
		DevelopmentProposal developmentProposal = pdDoc
				.getDevelopmentProposal();
		for (ProposalYnq proposalYnq : developmentProposal.getProposalYnqs()) {
			if (proposalYnq.getQuestionId() != null
					&& proposalYnq.getQuestionId().equals(
							PROPOSAL_YNQ_QUESTION_22)) {
				String answer = proposalYnq.getAnswer();
				String explanation = proposalYnq.getExplanation();

				if (S2SConstants.PROPOSAL_YNQ_ANSWER_Y.equals(answer)) {
					phsChecklist.setIsChangeOfPDPI(YesNoDataType.Y_YES);
					if (explanation != null) {
						HumanNameDataType formerPDName = globLibV20Generator
								.getHumanNameDataType(explanation);
						if (formerPDName != null
								&& formerPDName.getFirstName() != null
								&& formerPDName.getLastName() != null) {
							phsChecklist.setFormerPDName(formerPDName);
						}
					}
				} else {
					phsChecklist.setIsChangeOfPDPI(YesNoDataType.N_NO);
				}
			}
		}
	}

	/*
	 * This method will get the YNQ Answer for question id
	 */
	private YesNoDataType.Enum getYNQAnswer(String questionID) {
		YesNoDataType.Enum answerType = null;
		DevelopmentProposal developmentProposal = pdDoc
				.getDevelopmentProposal();
		List<ProposalYnq> proposalYnqList = developmentProposal
				.getProposalYnqs();
		if (proposalYnqList != null && !proposalYnqList.isEmpty()) {
			for (ProposalYnq proposalYnq : proposalYnqList) {
				if (proposalYnq.getQuestionId() != null) {
					if (questionID.equals(proposalYnq.getQuestionId())) {
						String answer = proposalYnq.getAnswer();
						answerType = "Y".equals(answer) ? YesNoDataType.Y_YES
								: YesNoDataType.N_NO;
						break;
					}
				}
			}
		}
		return answerType;
	}

	/**
	 * This method creates {@link XmlObject} of type
	 * {@link PHS398Checklist13Document by populating data from the given
	 * {@link ProposalDevelopmentDocument}
	 * 
	 * @param proposalDevelopmentDocument
	 *            for which the {@link XmlObject} needs to be created
	 * @return {@link XmlObject} which is generated using the given
	 *         {@link ProposalDevelopmentDocument}
	 * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject(ProposalDevelopmentDocument)
	 */
	public XmlObject getFormObject(
			ProposalDevelopmentDocument proposalDevelopmentDocument) {
		this.pdDoc = proposalDevelopmentDocument;
		return getPHS398Checklist();
	}

	/**
	 * This method type casts the given {@link XmlObject} to the required
	 * generator type and returns back the document of that generator type.
	 * 
	 * @param xmlObject
	 *            which needs to be converted to the document type of the
	 *            required generator
	 * @return {@link XmlObject} document of the required generator type
	 * @see org.kuali.kra.s2s.generator.S2SFormGenerator#getFormObject
	 */
	public XmlObject getFormObject(XmlObject xmlObject) {
		PHS398Checklist13 phsChecklist = (PHS398Checklist13) xmlObject;
		PHS398Checklist13Document phsChecklistDocument = PHS398Checklist13Document.Factory
				.newInstance();
		phsChecklistDocument.setPHS398Checklist13(phsChecklist);
		return phsChecklistDocument;
	}
}
