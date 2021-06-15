package com.selenium.demo.ut;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.selenium.dev.TestDemoApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-ut.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceUTest {
	@Autowired
	Environment env;

	public ServiceUTest() {

	}

	@Test
	public void contextLoads() {
		System.out.println(env.getProperty("demovalue"));
	}

}
