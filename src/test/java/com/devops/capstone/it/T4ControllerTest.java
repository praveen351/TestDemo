package com.devops.capstone.it;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
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
@TestPropertySource(locations = "classpath:application-it.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class T4ControllerTest {
	private static String sdate;
	private static String edate;
	private static String stime;

	public T4ControllerTest() {
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
	public void testA1handleSurvey() {
		SurveyEntity survey = new SurveyEntity(new ArrayList<SurveyDetailEntity>(), new ArrayList<CandidateEntity>(),
				"Trackajo", sdate + "@" + stime, edate + "@" + stime, 2, 2, "ACTIVE");

		ResponseEntity<JsonNode> postResponse = restTemplate.postForEntity(getRootUrl() + "/survey/saveSurvey", survey,
				JsonNode.class);

		ArrayList<SurveyDetailEntity> su_detail = new ArrayList<SurveyDetailEntity>() {
			{
				add(new SurveyDetailEntity(new ResponseTypeEntity(1, "DROPDOWN", "MULTIPLE", "ACTIVE"),
						"What is fav tech. ?", "Java,C#,Angular,NodeJs", "MULTIPLE", "ACTIVE"));
				add(new SurveyDetailEntity(new ResponseTypeEntity(2, "TEXTBOX", "SINGLE", "ACTIVE"),
						"What is ur total experience ?	", "DATA", "DATA", "ACTIVE"));
			}
		};
		SurveyEntity survey1 = new SurveyEntity(2, su_detail, null, "Trackajo", sdate + "@" + stime,
				edate + "@" + stime, 2, 2, "ACTIVE");

		ResponseEntity<JsonNode> postResponse1 = restTemplate.postForEntity(getRootUrl() + "/survey/saveSurvey",
				survey1, JsonNode.class);

		assertNotNull(postResponse);
		assertNotNull(postResponse1);
		assertNotNull(postResponse.getBody());
		assertNotNull(postResponse1.getBody());
	}

	@Test
	public void testA2handleCandidate() {
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

		ArrayList<CandidateEntity> cad_detail1 = new ArrayList<CandidateEntity>() {
			{
				add(new CandidateEntity(3, null, new ArrayList<CandidateMail>() {
					{
						add(new CandidateMail(sdate, "SUCCESS", "ACTIVE"));
					}
				}, "Praveen Samal", "praveenkumar54321raja@gmail.com", null, null, null, "ACTIVE"));
			}
		};
		SurveyEntity survey1 = new SurveyEntity(2, null, cad_detail1, "Trackajo", sdate + "@" + stime,
				edate + "@" + stime, 2, 2, "ACTIVE");

		ResponseEntity<JsonNode> postResponse1 = restTemplate.postForEntity(getRootUrl() + "/candidate/saveCandidate",
				survey1, JsonNode.class);

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
		ResponseEntity<JsonNode> postResponse2 = restTemplate
				.postForEntity(getRootUrl() + "/candidate/setCandidateResponse", candidate, JsonNode.class);

		assertNotNull(postResponse);
		assertNotNull(postResponse1);
		assertNotNull(postResponse2);
		assertNotNull(postResponse.getBody());
		assertNotNull(postResponse1.getBody());
		assertNotNull(postResponse2.getBody());
	}
}
