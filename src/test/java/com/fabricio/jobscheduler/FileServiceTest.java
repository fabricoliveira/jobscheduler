package com.fabricio.jobscheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fabricio.jobscheduler.service.FileService;

@SpringBootTest
class FileServiceTest {
	
	@Autowired
	private FileService fileService;
	
	@Test
	void sucessOnReadingExistingTextFile() {
		String text = fileService.readFile("inputtest.txt", 1, 1);
		assertNotEquals("", text);
	}
	
	
	@Test
	void failOnReadingNonExistingTextFile() {
		String text = fileService.readFile("input123.txt", 1, 1);
		assertEquals("", text);
	}
	
	
	@Test
	void sucessOnWritingATextFile() {
		String content = "Success - Test Passed";
		fileService.writeFile(content, "outputtest.txt");
		content = fileService.readFile("outputtest.txt", 1, 0);
		assertEquals("Success - Test Passed", content);
	}
	
}
