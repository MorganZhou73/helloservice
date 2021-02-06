package com.unistar.helloservice;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.net.URL;
import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions;

/**
 * full-stack integration test
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerIntegrationTest {
	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port);
		System.out.println("ControllerIntegrationTest base = " + base.toString());
	}

	@Test
	void greetingTest() throws Exception {
		ResponseEntity<String> response = restTemplate.getForEntity(base.toString() + "/greeting",
				String.class);
		assertThat(response.getBody()).contains("Good morning, World!");
	}

	@Test
	public void greeting1Test() throws Exception {
		ResponseEntity<Greeting> response = this.restTemplate.exchange(
				RequestEntity.get(uri("/greeting1/joe")).build(),
				Greeting.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		Greeting obj = response.getBody();
		assertThat(obj.getContent()).isEqualTo("Good morning, joe!");
	}

	@Test
	public void sendmessageTest() throws Exception {
		Greeting obj = new Greeting(1, "hello!");
		// JSON format post
		HttpEntity<String> request = getGreetingJsonRequest(obj);
		String response = this.restTemplate.postForObject(uri("/sendmessage"), request, String.class);

		String expected = GreetingController.MSG_RECEIVED_JSON + obj.toString();
		Assertions.assertTrue(response.equals(expected));

		// XML format post
		request = getGreetingXmlRequest(obj);
		response = this.restTemplate.postForObject(uri("/sendmessage"), request, String.class);
		expected = GreetingController.MSG_RECEIVED_XML + obj.toString();
		Assertions.assertTrue(response.equals(expected));
	}

	@Test
	public void healthcheckTest() throws Exception {

		ResponseEntity<JsonNode> response = restTemplate.exchange(
				RequestEntity.get(uri("/healthcheck?format=full")).build(), JsonNode.class);

		JsonNode map = response.getBody();

		String val = map.get("application").asText();
		Assertions.assertTrue(val.equals("OK"));
	}

	@Test
	public void healthcheck1FullTest() throws Exception {
		ResponseEntity<HealthResponseFull> response =
				restTemplate.exchange(RequestEntity.get(uri("/healthcheck1?format=full")).build(),
				HealthResponseFull.class);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertTrue(response.getBody() instanceof HealthResponseFull);
		HealthResponseFull val = response.getBody();
		Assertions.assertTrue(val.getApplication().equals("OK"));
	}

	@Test
	public void healthcheck1ShortTest() throws Exception {
		ResponseEntity<HealthResponseShort> response =
				restTemplate.exchange(RequestEntity.get(uri("/healthcheck1?format=short")).build(),
						HealthResponseShort.class);

		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		HealthResponseShort val = (HealthResponseShort)response.getBody();
		Assertions.assertTrue(val.getStatus().equals("OK"));
	}

	@Test
	public void healthcheck1BadRequestTest() throws Exception {
		ResponseEntity<String> response =
				restTemplate.exchange(RequestEntity.get(uri("/healthcheck1?format=long")).build(),
						String.class);
		// response.getBody() example:
		// {"timestamp":"2021-02-05T20:42:40.292+00:00","status":400,"error":"Bad Request","message":"","path":"/healthcheck1"}

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	private HttpEntity<String> getGreetingJsonRequest(Greeting obj) throws JSONException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject greeting = new JSONObject();
		greeting.put("id", obj.getId());
		greeting.put("content", obj.getContent());

		return new HttpEntity<String>(greeting.toString(), headers);
	}

	private HttpEntity<String> getGreetingXmlRequest(Greeting obj) throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);

		XmlMapper xmlMapper = new XmlMapper();
		String postContent = xmlMapper.writeValueAsString(obj);

		return new HttpEntity<String>(postContent, headers);
	}

	private URI uri(String path) {
		return restTemplate.getRestTemplate().getUriTemplateHandler().expand(path);
	}

}
