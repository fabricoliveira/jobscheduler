package com.fabricio.jobscheduler.util;

import java.time.LocalDateTime;

public class Utils {

	private Utils() {
	}

	public static boolean isNullOrEmpty(String text) {
		return text == null || text.trim().isEmpty();
	}

	public static boolean isNullOrEmpty(Integer number) {
		return number == null || isNullOrEmpty(number.toString());
	}

	public static boolean isNullOrEmpty(Long number) {
		return number == null || isNullOrEmpty(number.toString());
	}

	public static boolean isNullOrEmpty(LocalDateTime dateTime) {
		return dateTime == null || isNullOrEmpty(dateTime.toString());
	}

}
