package com.eugene.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StringMatcherService {
	
	public List<String> match(String text, String subText) {
	    List<String> locations = new ArrayList<>();

	    int loc = 0;
	    int prevloc = 0;
	    
	    String textUpperCase = text.toUpperCase();
	    String subTextUpperCase = subText.toUpperCase();

	    while((loc = textUpperCase.indexOf(subTextUpperCase.toUpperCase(), prevloc)) != -1) {
	      locations.add(String.valueOf(loc));
	      prevloc = loc + 1;
	    }

	    return locations;
	}
}
