package com.devops.capstone.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.devops.capstone.CapstonApplication;
import com.devops.capstone.helper.TimeReader;
import com.devops.capstone.model.CandidateEntity;
import com.devops.capstone.model.CandidateMail;
import com.devops.capstone.model.CandidateResponseEntity;
import com.devops.capstone.model.ResponseTypeEntity;
import com.devops.capstone.model.SurveyDetailEntity;
import com.devops.capstone.model.SurveyEntity;
import com.devops.capstone.service.CandidateService;
import com.devops.capstone.service.SurveyDetailService;
import com.devops.capstone.service.SurveyService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapstonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-it.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class T3ServiceTest {

	private static String sdate;
	private static String edate;
	private static String stime;

	public T3ServiceTest() {
		String[] starstopdate = TimeReader.generateStartStopTime();
		sdate = starstopdate[0];
		edate = starstopdate[1];
		stime = starstopdate[2];
	}

	@Autowired
	private SurveyService survey_service;
	@Autowired
	private SurveyDetailService survey__detail_service;
	@Autowired
	private CandidateService candidate_service;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testA1handleSurvey() {
		SurveyEntity survey = new SurveyEntity(null, null, "Trackao", sdate + "@" + stime, edate + "@" + stime, 2, 2,
				"ACTIVE");
		survey = survey_service.saveSurveyEntity(survey);
		ArrayList<SurveyDetailEntity> su_detail = new ArrayList<SurveyDetailEntity>() {
			{
				add(new SurveyDetailEntity(new ResponseTypeEntity(1, "DROPDOWN", "MULTIPLE", "ACTIVE"),
						"What is fav tech. ?", "Java,C#,Angular,NodeJs", "MULTIPLE", "ACTIVE"));
				add(new SurveyDetailEntity(new ResponseTypeEntity(2, "TEXTBOX", "SINGLE", "ACTIVE"),
						"What is ur total experience ?	", "DATA", "DATA", "ACTIVE"));
			}
		};
		survey.setSurvey_detail_list(su_detail);
		survey = survey__detail_service.addSurvey(survey);
		assertNotNull(survey);
	}

	@Test
	public void testA2handleSurveyCandidate() {
		ArrayList<CandidateEntity> cad_detail = new ArrayList<CandidateEntity>() {
			{
				add(new CandidateEntity(null, null, "Praveen Kumar Samal", "praveenkumar1234raja@gmail.com", null, null,
						null, "ACTIVE"));
				add(new CandidateEntity(null, null, "Raja Kumar Samal", "rajapraveen4321@gmail.com", null, null, null,
						"ACTIVE"));
			}
		};
		SurveyEntity survey = new SurveyEntity(1, null, cad_detail, "Trackao", sdate + "@" + stime, edate + "@" + stime,
				2, 2, "ACTIVE");
		survey = candidate_service.addCandidate(survey);

		cad_detail = new ArrayList<CandidateEntity>() {
			{
				add(new CandidateEntity(1, null, new ArrayList<CandidateMail>() {
					{
						add(new CandidateMail("04/17/2021", "SUCCESS", "ACTIVE"));
					}
				}, "Praveen Kumar Samal", "praveenkumar1234raja@gmail.com", null, null, null, "ACTIVE"));
				add(new CandidateEntity(2, null, new ArrayList<CandidateMail>() {
					{
						add(new CandidateMail("04/17/2021", "SUCCESS", "ACTIVE"));
					}
				}, "Raja Kumar Samal", "rajapraveen4321@gmail.com", null, null, null, "ACTIVE"));
			}
		};
		survey = new SurveyEntity(1, null, cad_detail, "Trackao", sdate + "@" + stime, edate + "@" + stime, 2, 2,
				"ACTIVE");
		survey = candidate_service.addCandidate(survey);
		CandidateEntity candidate = new CandidateEntity(1, new ArrayList<CandidateResponseEntity>() {
			{
				add(new CandidateResponseEntity(
						new SurveyDetailEntity(1, new ResponseTypeEntity(1, "DROPDOWN", "MULTIPLE", "ACTIVE"),
								"What is fav tech. ?", "Java,C#,Angular,NodeJs", "MULTIPLE", "ACTIVE"),
						"Java,Angular", "ACTIVE"));
				add(new CandidateResponseEntity(
						new SurveyDetailEntity(2, new ResponseTypeEntity(2, "TEXTBOX", "SINGLE", "ACTIVE"),
								"What is ur total experience ?	", "DATA", "DATA", "ACTIVE"),
						"3", "ACTIVE"));
			}
		}, null, "Praveen Kumar Samal", "praveenkumar1234raja@gmail.com", null, null, null, "ACTIVE");
		int status = candidate_service.updateCandidate(candidate);
		assertEquals(1, status);
	}

}
