package com.fabricio.jobscheduler;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobschedulerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(JobschedulerApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
	}

	
	@Override
	public void run(String... args) throws Exception {
	}

}
