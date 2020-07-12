package com.fabricio.jobscheduler;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fabricio.jobscheduler.service.LocalDateTimeService;

@SpringBootTest
class LocalDateTimeServiceTest {
	
	@Autowired
	private LocalDateTimeService localDateTimeService;
	
	@Test
	void sucessOnConvertingStringToLocalDateTime() {
		String dateTime = "2020-07-12 05:00:00";
		LocalDateTime localDateTime = localDateTimeService.convertStringToLocalDateTime(dateTime);
		assertNotNull(localDateTime);
	}
	
	@Test
	void failOnConvertingBrokenDateToLocalDateTime() {
		String dateTime = "2020-07- 05:00:00";
		LocalDateTime localDateTime = localDateTimeService.convertStringToLocalDateTime(dateTime);
		assertNull(localDateTime);
	}
	
	@Test
	void failOnConvertingBrokenHourToLocalDateTime() {
		String dateTime = "2020-07-12 05:00:";
		LocalDateTime localDateTime = localDateTimeService.convertStringToLocalDateTime(dateTime);
		assertNull(localDateTime);
	}
	
	@Test
	void failOnConvertingInvalidDateToLocalDateTime() {
		String dateTime = "2020-07-32 05:00:00";
		LocalDateTime localDateTime = localDateTimeService.convertStringToLocalDateTime(dateTime);
		assertNull(localDateTime);
	}
	
	@Test
	void failOnConvertingInvalidHourToLocalDateTime() {
		String dateTime = "2020-07-12 25:00:00";
		LocalDateTime localDateTime = localDateTimeService.convertStringToLocalDateTime(dateTime);
		assertNull(localDateTime);
	}

}
