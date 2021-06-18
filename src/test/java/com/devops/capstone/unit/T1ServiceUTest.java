package com.devops.capstone.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

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
@TestPropertySource(locations = "classpath:application-ut.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class T1ServiceUTest {
	private static String sdate;
	private static String edate;
	private static String stime;

	public T1ServiceUTest() {
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
	public void testA1SaveSurvey() {
		SurveyEntity survey = new SurveyEntity(null, null, "Trackao", sdate + "@" + stime, edate + "@" + stime, 2, 2,
				"ACTIVE");
		survey = survey_service.saveSurveyEntity(survey);
		assertNotNull(survey);
	}

	@Test
	public void testA2SaveSurveyDetail() {
		ArrayList<SurveyDetailEntity> su_detail = new ArrayList<SurveyDetailEntity>() {
			{
				add(new SurveyDetailEntity(new ResponseTypeEntity(1, "DROPDOWN", "MULTIPLE", "ACTIVE"),
						"What is fav tech. ?", "Java,C#,Angular,NodeJs", "MULTIPLE", "ACTIVE"));
				add(new SurveyDetailEntity(new ResponseTypeEntity(2, "TEXTBOX", "SINGLE", "ACTIVE"),
						"What is ur total experience ?	", "DATA", "DATA", "ACTIVE"));
			}
		};
		SurveyEntity survey = new SurveyEntity(1, su_detail, null, "Trackao", sdate + "@" + stime, edate + "@" + stime,
				2, 2, "ACTIVE");
		survey = survey__detail_service.addSurvey(survey);
		assertNotNull(survey);
	}

	@Test
	public void testA3SaveSurveyCandidate() {
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
		assertNotNull(survey);
	}

	@Test
	public void testA4SaveCandidateMail() {

		ArrayList<CandidateEntity> cad_detail = new ArrayList<CandidateEntity>() {
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
		SurveyEntity survey = new SurveyEntity(1, null, cad_detail, "Trackao", sdate + "@" + stime, edate + "@" + stime,
				2, 2, "ACTIVE");
		survey = candidate_service.addCandidate(survey);
		assertNotNull(survey);
	}

	@Test
	public void testA5SaveCandidateResponse() {

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

	@Test
	public void testA6GetSurvey() {
		List<SurveyEntity> survey_list = survey_service.getSurveyList();
		assertNotNull(survey_list);
	}

	@Test
	public void testA7GetSurveyEntity() {
		SurveyEntity survey_entity = survey_service.getSurveyEntity(1);
		assertNotNull(survey_entity);
	}

	@Test
	public void testA8GetCandidateEntity() {
		CandidateEntity candidate_entity = candidate_service.getSpecCandidate(1);
		assertNotNull(candidate_entity);
	}

	@Test
	public void testA9GetCandidateSurvey() {
		long fdata = candidate_service.getCandidate_Survey("praveenkumar1234raja@gmail.com", "Praveen Kumar Samal");
		assertEquals(1, fdata);
	}

	@Test
	public void testA10GetSurveyDetails() {
		List<SurveyDetailEntity> survey_detail_list = survey__detail_service.getAllCondSurveyeDetail(1);
		assertNotNull(survey_detail_list);
	}
}
