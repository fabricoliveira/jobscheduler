package com.fabricio.jobscheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fabricio.jobscheduler.service.JsonService;

@SpringBootTest
class JsonServiceTest {

	@Autowired
	private JsonService jsonService;
	
	@Test
	void successOnAddQuotesToJsonValues() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-10 12:00:00, \n" + 
				"        \"Tempo estimado\": 2 horas,\n" + 
				"    },\n" + 
				"]");
		
		long count = json.chars().filter(character -> character == "\"".charAt(0)).count();
		
		assertEquals(16L, count);
	}
	
	@Test
	void successOnParseJsonToJob() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-10 12:00:00, \n" + 
				"        \"Tempo estimado\": 2 horas,\n" + 
				"    },\n" + 
				"]");
		
		assertFalse(jsonService.jsonToListOfJobs(json).isEmpty());
	}
	
	@Test
	void failOnParseInvalidJsonToJob() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        Data Máxima de conclusão: 2019-11-10 12:00:00, \n" + 
				"        \"Tempo estimado\": 2 horas,\n" + 
				"    },\n" + 
				"]");
		
		assertTrue(jsonService.jsonToListOfJobs(json).isEmpty());
	}
	
}
