package com.fabricio.jobscheduler.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fabricio.jobscheduler.models.Job;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonService {
	
	
	public String addQuotesToJsonValues(String json) {
		// Add double quotes to "values"  
		json = json.replaceAll("(?<=: ?)(?![ \\{\\[])(.+?)(?=,|})", "\"$1\"");
		
		// Remove all single quotes
		json = json.replace("'", "\"");
		
		// Remove duplicated double quotes
		do {
			json = json.replaceAll("\"\"", "\"");
		} while(json.contains("\"\""));
		
		return json;
	}
	
	@SuppressWarnings("deprecation")
	public List<Job> jsonToListOfJobs(String json) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			mapper.configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true);
			mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			
			return Arrays.asList(mapper.readValue(json, Job[].class));
		} catch (JsonProcessingException e) {
			System.err.println(e.getMessage());
		}
		return Collections.emptyList();
	}
	
}
