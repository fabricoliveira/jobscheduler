package com.fabricio.jobscheduler.models.handler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fabricio.jobscheduler.util.Utils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class DateHandler extends StdDeserializer<LocalDateTime> {

	private static final long serialVersionUID = 1L;

	public DateHandler() {
		this(null);
	}

	public DateHandler(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
		if (Utils.isNullOrEmpty(jsonParser.getText())) {
			return null;
		}

		try {
			String dateTime = jsonParser.getText();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			return LocalDateTime.parse(dateTime, dateTimeFormatter);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}
