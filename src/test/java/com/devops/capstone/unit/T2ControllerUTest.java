package com.devops.capstone.unit;

import static org.junit.Assert.assertNotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import com.fasterxml.jackson.databind.JsonNode;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CapstonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-ut.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class T2ControllerUTest {

	private static String sdate;
	private static String edate;
	private static String stime;

	public T2ControllerUTest() {
		String[] starstopdate = TimeReader.generateStartStopTime();
		sdate = starstopdate[0];
		edate = starstopdate[1];
		stime = starstopdate[2];
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testA1CreateSurvey() {
		SurveyEntity survey = new SurveyEntity(new ArrayList<SurveyDetailEntity>(), new ArrayList<CandidateEntity>(),
				"Trackajo", sdate + "@" + stime, edate + "@" + stime, 2, 2, "ACTIVE");

		ResponseEntity<JsonNode> postResponse = restTemplate.postForEntity(getRootUrl() + "/survey/saveSurvey", survey,
				JsonNode.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());

	}

	@Test
	public void testA2CreateSurveyDetail() {
		ArrayList<SurveyDetailEntity> su_detail = new ArrayList<SurveyDetailEntity>() {
			{
				add(new SurveyDetailEntity(new ResponseTypeEntity(1, "DROPDOWN", "MULTIPLE", "ACTIVE"),
						"What is fav tech. ?", "Java,C#,Angular,NodeJs", "MULTIPLE", "ACTIVE"));
				add(new SurveyDetailEntity(new ResponseTypeEntity(2, "TEXTBOX", "SINGLE", "ACTIVE"),
						"What is ur total experience ?	", "DATA", "DATA", "ACTIVE"));
			}
		};
		SurveyEntity survey = new SurveyEntity(2, su_detail, null, "Trackajo", sdate + "@" + stime, edate + "@" + stime,
				2, 2, "ACTIVE");

		ResponseEntity<JsonNode> postResponse = restTemplate.postForEntity(getRootUrl() + "/survey/saveSurvey", survey,
				JsonNode.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());

	}

	@Test
	public void testA3CreateSurveyCandidate() {
		ArrayList<CandidateEntity> cad_detail = new ArrayList<CandidateEntity>() {
			{
				add(new CandidateEntity(null, null, "Praveen Samal", "praveenkumar54321raja@gmail.com", null, null,
						null, "ACTIVE"));
			}
		};
		SurveyEntity survey = new SurveyEntity(2, null, cad_detail, "Trackajo", sdate + "@" + stime,
				edate + "@" + stime, 2, 2, "ACTIVE");

		ResponseEntity<JsonNode> postResponse = restTemplate.postForEntity(getRootUrl() + "/candidate/saveCandidate",
				survey, JsonNode.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testA4SaveCandidateMail() {
		ArrayList<CandidateEntity> cad_detail = new ArrayList<CandidateEntity>() {
			{
				add(new CandidateEntity(3, null, new ArrayList<CandidateMail>() {
					{
						add(new CandidateMail(sdate, "SUCCESS", "ACTIVE"));
					}
				}, "Praveen Samal", "praveenkumar54321raja@gmail.com", null, null, null, "ACTIVE"));
			}
		};
		SurveyEntity survey = new SurveyEntity(2, null, cad_detail, "Trackajo", sdate + "@" + stime,
				edate + "@" + stime, 2, 2, "ACTIVE");

		ResponseEntity<JsonNode> postResponse = restTemplate.postForEntity(getRootUrl() + "/candidate/saveCandidate",
				survey, JsonNode.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());

	}

	@Test
	public void testA5SaveCandidateResponse() {

		CandidateEntity candidate = new CandidateEntity(3, new ArrayList<CandidateResponseEntity>() {
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
		}, null, "Praveen Samal", "praveenkumar54321raja@gmail.com", null, null, null, "ACTIVE");
		ResponseEntity<JsonNode> postResponse = restTemplate
				.postForEntity(getRootUrl() + "/candidate/setCandidateResponse", candidate, JsonNode.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testA6GetSurvey() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(getRootUrl() + "/getAllSurvey", HttpMethod.GET,
				entity, JsonNode.class);
		assertNotNull(response.getBody());
	}

	@Test
	public void testA7GetSurveyEntity() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(getRootUrl() + "/getSurvey/2", HttpMethod.GET, entity,
				JsonNode.class);
		assertNotNull(response.getBody());
	}

	@Test
	public void testA8GetCandidateEntity() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<JsonNode> response = restTemplate.exchange(getRootUrl() + "/getCandidate/2", HttpMethod.GET,
				entity, JsonNode.class);
		assertNotNull(response.getBody());
	}

}
