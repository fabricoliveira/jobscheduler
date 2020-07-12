package com.fabricio.jobscheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fabricio.jobscheduler.service.FileService;

@SpringBootApplication
public class JobschedulerApplication implements CommandLineRunner {

	@Autowired
	private FileService fileService;
	
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(JobschedulerApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		String firstLine = fileService.readFile("input.txt", 1, 1);
		String json = fileService.readFile("input.txt", 2, 0);
		
		
	}

}
