package com.devops.capstone;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import com.devops.capstone.helper.TimeReader;
import com.devops.capstone.model.ResponseTypeEntity;
import com.devops.capstone.model.SurveyEntity;
import com.devops.capstone.model.apimodel.CandidateSelectedMail;
import com.devops.capstone.service.CandidateMailService;
import com.devops.capstone.service.CandidateService;
import com.devops.capstone.service.ResponseTypeService;
import com.devops.capstone.service.SurveyDetailService;
import com.devops.capstone.service.SurveyService;
import com.google.gson.Gson;

@Configuration
public class CapstonConfig {
	@Autowired
	Environment env;

	@Autowired
	private ResponseTypeService repo_service;
	@Autowired
	private SurveyService survey_service;
	@Autowired
	private SurveyDetailService survey_detail_service;
	@Autowired
	private CandidateService candidate_service;
	@Autowired
	private CandidateMailService candidate_mail_service;

	private static String sdate;
	private static String edate;
	private static String stime;

	public CapstonConfig() {
		String[] starstopdate = TimeReader.generateStartStopTime();
		sdate = starstopdate[0];
		edate = starstopdate[1];
		stime = starstopdate[2];
	}

	@Bean
	void insertResponseTypeData() {
		if (repo_service.getResponseTypeList().size() == 0) {
			List<ResponseTypeEntity> entityList = new ArrayList<>(
					Arrays.asList(new ResponseTypeEntity("DROPDOWN", "MULTIPLE", "ACTIVE"),
							new ResponseTypeEntity("TEXTBOX", "SINGLE", "ACTIVE"),
							new ResponseTypeEntity("TEXTAREA", "SINGLE", "ACTIVE"),
							new ResponseTypeEntity("RADIOBUTTON", "MULTIPLE", "ACTIVE"),
							new ResponseTypeEntity("CHECKBOX", "MULTIPLE", "ACTIVE")));
			for (ResponseTypeEntity responseTypeEntity : entityList) {
				repo_service.saveResponseTypeEntity(responseTypeEntity);
			}
		}
	}

	@Bean
	void insertRecordData() throws Exception {
		String str = env.getProperty("server.port");
		if (survey_service.getSurveyList().size() == 0 && str.equals("7777")) {
			Gson g = new Gson();

			String ssurveyObj = getFiledata("survey").trim();
			SurveyEntity survey = g.fromJson(ssurveyObj, SurveyEntity.class);
			survey.setOpening_date_time(sdate + "@" + stime);
			survey.setClosing_date_time(edate + "@" + stime);
			survey = survey_service.saveSurveyEntity(survey);

			String ssurveyDetailObj = getFiledata("surveyquestion").trim();
			survey.setSurvey_detail_list(g.fromJson(ssurveyDetailObj, SurveyEntity.class).getSurvey_detail_list());
			survey_detail_service.addSurvey(survey);

			String ssurveyCandObj = getFiledata("surveycandidate").trim();
			survey.setCandidate_list(g.fromJson(ssurveyCandObj, SurveyEntity.class).getCandidate_list());
			candidate_service.addCandidate(survey);

			String sselectedMail = getFiledata("surveymail").trim();
			CandidateSelectedMail candidate_mail = g.fromJson(sselectedMail, CandidateSelectedMail.class);
			candidate_mail_service.addCandidateMail(candidate_mail);
		}
	}

	private static String getFiledata(String filename) throws Exception {
		Resource resource = new ClassPathResource("static/" + filename + ".json");
		InputStream inputStream = resource.getInputStream();
		try {
			byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
			String data = new String(bdata, StandardCharsets.UTF_8);
			return data;
		} catch (IOException e) {
			return "Error";
		}
	}
}
