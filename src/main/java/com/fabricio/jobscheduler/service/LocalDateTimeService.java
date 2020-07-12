package com.fabricio.jobscheduler.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Service;

@Service
public class LocalDateTimeService {
	
	public LocalDateTime convertStringToLocalDateTime(String dateTime) {
		try {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return LocalDateTime.parse(dateTime, dateTimeFormatter);
		} catch(DateTimeParseException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
}
