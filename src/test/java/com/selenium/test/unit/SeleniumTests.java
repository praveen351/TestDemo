package com.selenium.test.unit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SeleniumTests {
	static ChromeDriver driver;
	private String address = "localhost";
	private String port = "4200";
	private String baseURL = "http://" + address + ":" + port;

	public SeleniumTests() throws IOException {
		Resource resource = new ClassPathResource("application-test.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);
		address = props.getProperty("location");
		port = props.getProperty("port");
	}

	@Test
	public void contextLoads() {
	}

	@BeforeClass
	public static void setup() {
		System.setProperty("webdriver.chrome.driver", "driver//chromedriver");
		ChromeOptions coption = new ChromeOptions();
		coption.addArguments("--headless","--allowed-ips=172.20.0.1");
		driver = new ChromeDriver(coption);
	}

//
	@AfterClass
	public static void cleanUp() {
		driver.quit();
	}

	@Test
	public void testA1CandidateVisit() throws Exception {
		driver.get(baseURL + "/candidate/loginCandidate");
		Thread.sleep(1000);
		driver.findElementByXPath("//input[@formcontrolname='candidate_email']").sendKeys("rajapraveen4321@gmail.com");
		Thread.sleep(2000);
		driver.findElementByXPath("//input[@formcontrolname='candidate_name']").sendKeys("Praveen");
		Thread.sleep(2000);
		driver.findElementByXPath("//button[@type='submit']").click();
		Thread.sleep(5000);

		List<WebElement> chkbutton = driver.findElements(By.xpath("//input[@formcontrolname='candidate_r_ftextbox']"));
		chkbutton.get(0).sendKeys("JAVA,Node");
		Thread.sleep(2000);

		chkbutton.get(1).sendKeys("3");
		Thread.sleep(2000);
		driver.findElementByXPath("//button[@style='background-color: #ccffcc;']").click();
		Thread.sleep(15000);

		String strUrl = driver.getCurrentUrl();

		boolean status = strUrl.contains("confirmCandidate");
		assertEquals(status, true);
	}

	@Test
	public void testA2SurveyVisit() throws Exception {
		driver.get(baseURL + "/admin/addSurvey");
		Thread.sleep(2000);

		WebElement t = driver.findElementByXPath("//mat-card[@class='mat-card mat-focus-indicator']");
		List<WebElement> c = t.findElements(By.xpath("./child::*"));

		if (c.size() != 0) {
			c.get(0).click();
			Thread.sleep(2000);
			driver.findElementByXPath("//button[@style='background-color: #ffa366;']").click();
			Thread.sleep(3000);
			assertEquals(true, true);
		} else {
			assertEquals(true, false);
		}
	}

	@Test
	public void testA3SurveyDetailVisit() throws Exception {
		driver.get(baseURL + "/admin/addSurveyDetail");
		Thread.sleep(2000);

		WebElement t = driver.findElementByXPath("//mat-card[@class='mat-card mat-focus-indicator']");
		List<WebElement> c = t.findElements(By.xpath("./child::*"));

		if (c.size() != 0) {
			c.get(0).click();
			Thread.sleep(2000);
			driver.findElementByXPath("//button[@style='background-color: #ffa366;']").click();
			Thread.sleep(3000);
			assertEquals(true, true);
		} else
			assertEquals(true, false);
	}

	@Test
	public void testA4CandidateDetailVisit() throws Exception {
		driver.get(baseURL + "/admin/addCandidate");
		Thread.sleep(2000);

		WebElement t = driver.findElementByXPath("//mat-card[@class='mat-card mat-focus-indicator']");
		List<WebElement> c = t.findElements(By.xpath("./child::*"));

		if (c.size() != 0) {
			c.get(0).click();
			Thread.sleep(2000);
			driver.findElementByXPath("//button[@style='background-color: #ffa366;']").click();
			Thread.sleep(3000);
			assertEquals(true, true);
		} else
			assertEquals(true, false);
	}
}
