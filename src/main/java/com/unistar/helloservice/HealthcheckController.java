package com.unistar.helloservice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.bind.DatatypeConverter;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class HealthcheckController {


	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/healthcheck", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> healthcheck(@RequestParam(name="format", required=true) String format) {
		Map<String, Object> response = new HashMap<String, Object>();
		if(format.equals("short")){
			response.put("status", "OK");
		}
		else if(format.equals("full")){
			Date dateNow = Calendar.getInstance().getTime();
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
			form.setTimeZone(TimeZone.getTimeZone("UTC"));  // "2021-01-13T18:36:00Z"

			response.put("currentTime", form.format(dateNow));
			response.put("application", "OK");
		}
		else{
			// in this way, not need to change return type
			// ResponseStatusException is only available in Spring version 5+
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		// by spring.jackson.serialization.indent_output=false, we can change the json string style
		return response;
	}

	// if there is no ResponseStatusException in old version
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public class MyStatusException extends RuntimeException {
		public MyStatusException(String message) {
			super(message);
		}
	}

	@GetMapping(value = "/healthcheck1", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> healthcheck1(@RequestParam(name="format", required=true) String format) {
		HealthResponse response;
		if(format.equals("short")){
			response = new HealthResponseShort("OK");
		}
		else if(format.equals("full")){
			Date dateNow = Calendar.getInstance().getTime();
			SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
			form.setTimeZone(TimeZone.getTimeZone("UTC"));  // "2021-01-13T18:36:00Z"
			response = new HealthResponseFull(form.format(dateNow), "OK");
		}
		else{
			// return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			throw new MyStatusException("Bad format");
		}

		return new ResponseEntity<HealthResponse>(response, HttpStatus.OK);
	}

	/**
	 * just show how to mannually add the httpHeader
	 * @param format
	 * @return
	 */
	@GetMapping(value = "/healthcheck2")
	public ResponseEntity<?> healthcheck2(@RequestParam(name="format", required=true) String format) {
		final HttpHeaders httpHeaders= new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<String> response;
		if(format.equals("short")){
			response = new ResponseEntity<String>("{\"status\": \"OK\"}", httpHeaders, HttpStatus.OK);
		}
		else if(format.equals("full")){
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
			String dateStr = DatatypeConverter.printDateTime(calendar);

			response = new ResponseEntity<String>("{\"currentTime\":\"" + dateStr +
					"\", \"application\": \"OK\"}", httpHeaders, HttpStatus.OK);
		}
		else{
			response = new ResponseEntity<String>("Bad format", httpHeaders, HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	@GetMapping(value = "/healthcheck3", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> healthcheck3(@RequestParam(name="format", required=true) String format) {
		ResponseEntity<String> response;
		if(format.equals("short")){
			response = new ResponseEntity<String>("{\"status\": \"OK\"}", HttpStatus.OK);
		}
		else if(format.equals("full")){
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
			String dateStr = DatatypeConverter.printDateTime(calendar); // "2021-01-13T19:31:18.923Z"

			response = new ResponseEntity<String>("{\"currentTime\":\"" + dateStr +
					"\", \"application\": \"OK\"}", HttpStatus.OK);
		}
		else{
			response = new ResponseEntity<String>("Bad format", HttpStatus.BAD_REQUEST);
		}

		return response;
	}
}
