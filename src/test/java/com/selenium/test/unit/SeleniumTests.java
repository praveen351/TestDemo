package com.selenium.test.unit;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.selenium.EmplSeleniumTestApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmplSeleniumTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumTests {
	
	private static String address;
	private static String port;
	private static String baseURL;
	private static WebDriver driver;
	private static String raddress;
	private static String rport;
	private static String nodeURL;

	public SeleniumTests() throws IOException {
		
	}
	
	public static void initialize() throws IOException {
		Properties configProps = new Properties();
		InputStream iStream = new ClassPathResource("application-test.properties").getInputStream();
		configProps.load(iStream);

		address = configProps.getProperty("address");
		port = configProps.getProperty("port");
		raddress = configProps.getProperty("raddress");
		rport = configProps.getProperty("rport");
		baseURL = "http://" + address + ":" + port;
		nodeURL = "http://" + raddress + ":" + rport + "/wd/hub";
	}
	
	@Test
	public void contextLoads() throws InterruptedException {
		driver.get(baseURL);
		Thread.sleep(2000);
	}

	@BeforeClass
	public static void setup() throws IOException {
		initialize();
		
		System.setProperty("webdriver.chrome.driver", "driver//chromedriver.exe");
		ChromeOptions coption = new ChromeOptions();
		coption.addArguments("--headless");

		DesiredCapabilities capability = DesiredCapabilities.chrome();
		capability.setBrowserName("chrome");
		capability.setPlatform(Platform.LINUX);

//		capability.setCapability("chrome.binary", "<Path to binary>");
		capability.setCapability(ChromeOptions.CAPABILITY, coption);

		driver = new RemoteWebDriver(new URL(nodeURL), capability);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(520, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(520, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void cleanUp() {
		driver.quit();
	}

	@Test
	public void testA1CandidateVisit() throws Exception {
		driver.get(baseURL + "/candidate/loginCandidate");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@formcontrolname='candidate_email']"))
				.sendKeys("rajapraveen4321@gmail.com");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@formcontrolname='candidate_name']")).sendKeys("Praveen");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(5000);

		List<WebElement> chkbutton = driver.findElements(By.xpath("//input[@formcontrolname='candidate_r_ftextbox']"));
		chkbutton.get(0).sendKeys("JAVA,Node");
		Thread.sleep(2000);

		chkbutton.get(1).sendKeys("3");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@style='background-color: #ccffcc;']")).click();
		Thread.sleep(15000);

		String strUrl = driver.getCurrentUrl();

		boolean status = strUrl.contains("confirmCandidate");
		assertEquals(status, true);
	}

	@Test
	public void testA2SurveyVisit() throws Exception {
		driver.get(baseURL + "/admin/addSurvey");
		Thread.sleep(2000);

		WebElement t = driver.findElement(By.xpath("//mat-card[@class='mat-card mat-focus-indicator']"));
		List<WebElement> c = t.findElements(By.xpath("./child::*"));

		if (c.size() != 0) {
			c.get(0).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[@style='background-color: #ffa366;']")).click();
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

		WebElement t = driver.findElement(By.xpath("//mat-card[@class='mat-card mat-focus-indicator']"));
		List<WebElement> c = t.findElements(By.xpath("./child::*"));

		if (c.size() != 0) {
			c.get(0).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[@style='background-color: #ffa366;']")).click();
			Thread.sleep(3000);
			assertEquals(true, true);
		} else
			assertEquals(true, false);
	}

	@Test
	public void testA4CandidateDetailVisit() throws Exception {
		driver.get(baseURL + "/admin/addCandidate");
		Thread.sleep(2000);

		WebElement t = driver.findElement(By.xpath("//mat-card[@class='mat-card mat-focus-indicator']"));
		List<WebElement> c = t.findElements(By.xpath("./child::*"));

		if (c.size() != 0) {
			c.get(0).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//button[@style='background-color: #ffa366;']")).click();
			Thread.sleep(3000);
			assertEquals(true, true);
		} else
			assertEquals(true, false);
	}
}
