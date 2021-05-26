package com.eugene.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.eugene.demo.model.ApplicationRequest;
import com.eugene.demo.model.ApplicationResponse;
import com.eugene.demo.service.StringMatcherService;

@Controller
public class StringMatcherController {
	
	@Autowired
	StringMatcherService stringMatcherService;
	
	@PostMapping("/match")
	public @ResponseBody ApplicationResponse match(@RequestBody ApplicationRequest request) {
		
		validateRequestOrThrow(request);
		
		List<String> locations = stringMatcherService.match(request.getText(), request.getSubText());
		
		return ApplicationResponse.builder()
				.matchLocations(locations)
				.build();
	}

	
	private void validateRequestOrThrow(ApplicationRequest request) {
		if(request.getText() == null || request.getText().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input. Main text not found");
		}
		
		if(request.getSubText() == null || request.getSubText().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input. Subtext not found");
		}
	}
}
