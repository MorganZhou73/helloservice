package com.unistar.helloservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
	private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

	public static final String TEMPLATE1 = "Good morning, %s!";
	private final AtomicLong counter = new AtomicLong();

	public static final String MSG_RECEIVED_STRING = "String message received! Your message: ";
	public static final String MSG_RECEIVED_JSON = "JSON message received! Your message: ";
	public static final String MSG_RECEIVED_XML = "XML message received! Your message: ";
	public static final String MSG_RECEIVED_MAP = "Map message received! Your message: ";

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		Greeting ret = new Greeting(counter.incrementAndGet(), String.format(TEMPLATE1, name));
		System.out.println("== greeting: " + ret.toString());
		return ret;
	}

	@GetMapping({"/greeting1", "/greeting1/{name}"})
	public Greeting greeting1(@PathVariable(required = false) String name){
		if(name == null)
			name = "my kid";
		
		Greeting ret = new Greeting(counter.incrementAndGet(), String.format(TEMPLATE1, name));
		System.out.println("== greeting1: " + ret.toString());
		return ret;
	}

	/**
	 * Accepts a POST request with a plain text message parameter
	 * @param body contains the body of the POST request
	 * @return a string with the result of the POST
	 */
	@RequestMapping(value = "sendmessage", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String sendMessage(@RequestBody String message) {
		logger.info("String message: " + message);
		return MSG_RECEIVED_STRING + message;
	}

	/**
	 * Accepts a POST request with a JSON message parameter
	 * @param message serialized Message object
	 * @return a string with the result of the POST
	 */
	@RequestMapping(value = "sendmessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String sendMessageJson(@RequestBody Greeting message) {
		logger.info("JSON message: " + message.toString());
		return MSG_RECEIVED_JSON + message.toString();
	}

	/**
	 * Accepts a POST request with an XML message parameter
	 * @param message serialized Message object
	 * @return a string with the result of the POST
	 */
	@RequestMapping(value = "sendmessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody String sendMessageXml(@RequestBody Greeting message) {
		logger.info("XML message: " + message.toString());
		return MSG_RECEIVED_XML + message.toString();
	}

	/**
	 * Accepts a POST request with an Map message parameter, and creates a new Message object from the Map parameters.
	 * @param map serialized LinkedMultiValueMap<String, String> object
	 * @return a string with the result of the POST
	 */
@PostMapping(value = "sendmessagemap", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public String sendMessageMap(@RequestParam MultiValueMap<String, String> map) {
		Greeting message = new Greeting();

		try {
			message.setId(Integer.parseInt(map.getFirst("id")));
		} catch (NumberFormatException e) {
			message.setId(0);
		}

		message.setContent(map.getFirst("content"));

		logger.info("Map message: " + message.toString());
		return MSG_RECEIVED_MAP + message.toString();
	}
}
