package com.devops.capstone.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devops.capstone.model.CandidateEntity;
import com.devops.capstone.model.CandidateMail;
import com.devops.capstone.model.apimodel.CandidateMailResponse;
import com.devops.capstone.model.apimodel.CandidateSelectedMail;
import com.devops.capstone.repository.CandidateMailRepository;

@Service
public class CandidateMailService {
	private CandidateMailRepository candidate_mail_repo;

	@Autowired
	public CandidateMailService(CandidateMailRepository candidate_mail_repo) {
		this.candidate_mail_repo = candidate_mail_repo;
	}

	public List<CandidateMail> getCandidateMailStatus(long candidate_id) {
		return candidate_mail_repo.findAllCandidateMail(candidate_id);
	}

	public List<CandidateMailResponse> addCandidateMail(CandidateSelectedMail candidate_mail_data) {
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = dateFormat.format(date);

		List<CandidateEntity> candidate_list = candidate_mail_data.getSelectedCandidate();
		List<String> candidate_mail = new ArrayList<String>();
		List<CandidateMailResponse> candidate_mail_list = new ArrayList<CandidateMailResponse>();
		String sent_status = "";
		for (CandidateEntity candidate_entity : candidate_list) {
			candidate_mail.add(candidate_entity.getCandidate_email());
			candidate_mail_list.add(new CandidateMailResponse(candidate_entity.getCandidate_id(),candidate_entity.getCandidate_email()));
			sent_status = "SUCCESS";	 
			CandidateMail cand_mail_obj = candidate_mail_repo.save(new CandidateMail(strDate, sent_status, "ACTIVE"));
			candidate_mail_repo.updateCandidateMail(candidate_entity.getCandidate_id(),cand_mail_obj.getCandidate_mail_id());
		}
		return candidate_mail_list;
	}

	public int sendCandidateMailStatus(CandidateEntity candidate) {
		return 1;
	}
}
