package com.eugene.demo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.eugene.demo.model.ApplicationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
class DemoApplicationTests {

	@Test
	void testSingleMatchUpperCaseScenario() throws JsonProcessingException {
		given()
		.header("Content-Type", "application/json")
		.when()
		.body(generateRequest("This is a DeMo", "DEMO"))
		.post("http://localhost:8080/match")
		.then()
		.assertThat().statusCode(200)
		.body("matchLocations[0]", equalTo("10"));
	}
	
	@Test
	void testSingleMatchLowerCaseScenario() throws JsonProcessingException {
		given()
		.header("Content-Type", "application/json")
		.when()
		.body(generateRequest("This is a DeMo", "demo"))
		.post("http://localhost:8080/match")
		.then()
		.assertThat().statusCode(200)
		.body("matchLocations[0]", equalTo("10"));
	}

	@Test
	void testMultipleMatchScenario() throws JsonProcessingException {
		given()
		.header("Content-Type", "application/json")
		.when()
		.body(generateRequest("This is a DeMo to test a DeMo to confirm that the DEmo works", "DEMO"))
		.post("http://localhost:8080/match")
		.then()
		.assertThat().statusCode(200)
		.body("matchLocations[0]", equalTo("10"))
		.body("matchLocations[1]", equalTo("25"))
		.body("matchLocations[2]", equalTo("50"));
	}
	
	@Test
	void testNoMatchScenario() throws JsonProcessingException {
		given()
		.header("Content-Type", "application/json")
		.when()
		.body(generateRequest("This is a DeMo to test a DeMo to confirm that the DEmo works", "DEMOnstration"))
		.post("http://localhost:8080/match")
		.then()
		.assertThat().statusCode(200)
		.body("matchLocations", hasSize(0));
	}
	
	@Test
	void testNullTextScenario() throws JsonProcessingException {
		given()
		.header("Content-Type", "application/json")
		.when()
		.body(generateRequest(null, "DEMO"))
		.post("http://localhost:8080/match")
		.then()
		.assertThat().statusCode(400);
	}
	
	@Test
	void testBlankTextScenario() throws JsonProcessingException {
		given()
		.header("Content-Type", "application/json")
		.when()
		.body(generateRequest("", "DEMO"))
		.post("http://localhost:8080/match")
		.then()
		.assertThat().statusCode(400);
	}
	
	@Test
	void testNullSubTextScenario() throws JsonProcessingException {
		given()
		.header("Content-Type", "application/json")
		.when()
		.body(generateRequest("This is a DeMo to test a DeMo to confirm that the DEmo works", null))
		.post("http://localhost:8080/match")
		.then()
		.assertThat().statusCode(400);
	}
	
	@Test
	void testBlankSubTextScenario() throws JsonProcessingException {
		given()
		.header("Content-Type", "application/json")
		.when()
		.body(generateRequest("This is a DeMo to test a DeMo to confirm that the DEmo works", ""))
		.post("http://localhost:8080/match")
		.then()
		.assertThat().statusCode(400);
	}
	
	private String generateRequest(String text, String subText) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(new ApplicationRequest(text, subText));
	}
}
