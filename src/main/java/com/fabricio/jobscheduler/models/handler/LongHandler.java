package com.fabricio.jobscheduler.models.handler;

import java.io.IOException;

import com.fabricio.jobscheduler.util.Utils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LongHandler extends StdDeserializer<Long> {
	
	private static final long serialVersionUID = 1L;

	public LongHandler() {
		this(null);
	}
	
	public LongHandler(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public Long deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
		if(Utils.isNullOrEmpty(jsonParser.getText())) {
			return null;
		}
		
		try {
			String[] fieldValue = jsonParser.getText().trim().split(" ");
			return Long.parseLong(fieldValue[0].trim().replaceAll("\\D+", ""));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

}
