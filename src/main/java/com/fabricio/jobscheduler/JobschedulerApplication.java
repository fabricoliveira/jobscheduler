package com.fabricio.jobscheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fabricio.jobscheduler.business.JobSchedulerBusiness;
import com.fabricio.jobscheduler.service.FileService;

@SpringBootApplication
public class JobschedulerApplication implements CommandLineRunner {

	@Autowired
	private FileService fileService;
	@Autowired
	private JobSchedulerBusiness jobSchedulerBusiness;
	
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(JobschedulerApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		String firstLine = fileService.readFile("input.txt", 1, 1);
		String json = fileService.readFile("input.txt", 2, 0);
		
		List<LocalDateTime> schedulePeriod = jobSchedulerBusiness.findSchedulePeriod(firstLine);
		
		LocalDateTime startExecutionDateTime = schedulePeriod.get(0);
		LocalDateTime finishExecutionDateTime = schedulePeriod.get(1);
		Integer maxExecutionTime = 8;
		
		List<List<Long>> scheduledJobs = jobSchedulerBusiness.schedule(json, startExecutionDateTime, finishExecutionDateTime, maxExecutionTime);
		
		fileService.writeFile(scheduledJobs.toString(), "output.txt");
		
		System.out.println(scheduledJobs);
	}

}
