package com.fabricio.jobscheduler.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fabricio.jobscheduler.models.Job;
import com.fabricio.jobscheduler.service.JobService;
import com.fabricio.jobscheduler.service.JsonService;
import com.fabricio.jobscheduler.service.LocalDateTimeService;

@Component
public class JobSchedulerBusiness {
	
	@Autowired
	private JsonService jsonService;
	@Autowired
	private JobService jobService;
	@Autowired
	private LocalDateTimeService dateTimeService;
	
	
	public List<List<Long>> schedule(String json, LocalDateTime startExecutionDateTime, LocalDateTime finishExecutionDateTime, Integer maxExecutionTime) {
		
		json = jsonService.addQuotesToJsonValues(json);
		
		List<Job> jobs = jsonService.jsonToListOfJobs(json);
		
		return jobService.jobScheduler(jobs, startExecutionDateTime, finishExecutionDateTime, maxExecutionTime);		
	}
	
	
	public List<LocalDateTime> findSchedulePeriod(String text) {
		List<LocalDateTime> dates = new ArrayList<>();
		
		String regex = "\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}";
		Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(text);
	    
	    while(matcher.find()) {
	    	dates.add(dateTimeService.convertStringToLocalDateTime(matcher.group()));
	    }

		return dates.stream().sorted().collect(Collectors.toList());
	}

}
