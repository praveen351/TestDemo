package com.devops.capstone.model.apimodel;

import java.util.List;

import com.devops.capstone.model.CandidateEntity;

public class CandidateSelectedMail {
	List<CandidateEntity> selectedCandidate;
	
	public CandidateSelectedMail() {
		super();
	}

	public List<CandidateEntity> getSelectedCandidate() {
		return selectedCandidate;
	}

	public void setSelectedCandidate(List<CandidateEntity> selectedCandidate) {
		this.selectedCandidate = selectedCandidate;
	}
	
}
