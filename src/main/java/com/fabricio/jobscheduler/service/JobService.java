package com.fabricio.jobscheduler.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fabricio.jobscheduler.models.Job;
import com.fabricio.jobscheduler.util.Utils;

@Service
public class JobService {
	
	
	public List<List<Long>> jobScheduler(List<Job> jobs, LocalDateTime startExecutionDateTime, LocalDateTime finishExecutionDateTime, Integer maxExecutionTime) {
		List<List<Long>> scheduledJobs = new ArrayList<>();
		
		jobs = removeAllJobsNotValidForSpecifiedExecutionPeriod(jobs, startExecutionDateTime, finishExecutionDateTime, maxExecutionTime);
		jobs = sortJobByDateAndExecutionTime(jobs);
		
		while (!jobs.isEmpty()) {
			Iterator<Job> iterator = jobs.iterator();
			List<Job> schedule = new ArrayList<>();
			
			if(iterator.hasNext()) {
				schedule.add(iterator.next());
				iterator.remove();
			}
			
			while (iterator.hasNext()) {
				Job job = iterator.next();
				int sumOfScheduledJobs = schedule.stream().mapToInt(Job::getExecutionTime).sum();
				int sum = job.getExecutionTime() + sumOfScheduledJobs;
				
				if(sum <= 8) {
					schedule.add(job);
					iterator.remove();
				}
				
				if(sum == 8) {
					break;
				}
			}
			
			List<Long> ids = schedule.stream().map(Job::getId).collect(Collectors.toList());
			
			scheduledJobs.add(ids);
		}
		
		return scheduledJobs;
	}
	
	
	public boolean isJobValidForExecution(Job job, LocalDateTime startExecutionDateTime, LocalDateTime finishExecutionDateTime, Integer maxExecutionTime) {
		if(Utils.isNullOrEmpty(job.getId())) return false;
		
		if(Utils.isNullOrEmpty(job.getDeadLine())) return false;
		
		if(Utils.isNullOrEmpty(job.getExecutionTime())) return false;
		
		boolean isExecutionTimeValid = job.getExecutionTime() > 0 && 
									   job.getExecutionTime() <= maxExecutionTime;
		
		boolean isStartDateTimeValid = job.getDeadLine().isEqual(startExecutionDateTime) ||
									   job.getDeadLine().isEqual(finishExecutionDateTime) ||
									   job.getDeadLine().isAfter(startExecutionDateTime) &&
									   job.getDeadLine().isBefore(finishExecutionDateTime);
		 
		boolean isFinishDateTimeValid = job.getDeadLine().isEqual(finishExecutionDateTime) ||
										job.getDeadLine().isEqual(startExecutionDateTime) ||
										job.getDeadLine().isBefore(finishExecutionDateTime) &&
										job.getDeadLine().isAfter(startExecutionDateTime);
		
		return isExecutionTimeValid && isStartDateTimeValid && isFinishDateTimeValid;
	}
	
	
	/*
	 * This Algorithm executes in the following order:
	 * 1 - sort by deadline in ascending order, then  
	 * 2 - sort by execution time in descending order for the same day.  
	 */
	public List<Job> sortJobByDateAndExecutionTime(List<Job> jobs) {
		List<Job> sortedList = new ArrayList<>(jobs);
		sortedList.sort((Job job1, Job job2) -> {
			LocalDateTime deadline1 = job1.getDeadLine();
            LocalDateTime deadline2 = job2.getDeadLine();
            int dateComparison = deadline1.compareTo(deadline2);
            
            if(dateComparison != 0) {
            	return dateComparison;
            }
            
            Integer duration1 = job1.getExecutionTime();
            Integer duration2 = job2.getExecutionTime();
            return duration2.compareTo(duration1);
		});
		
		return sortedList;
	}
	
	
	public List<Job> removeAllJobsNotValidForSpecifiedExecutionPeriod(List<Job> jobs, LocalDateTime startExecutionDateTime, LocalDateTime finishExecutionDateTime, Integer maxExecutionTime) {
		return jobs.
			   stream().
 			   filter(job -> isJobValidForExecution(job, startExecutionDateTime, finishExecutionDateTime, maxExecutionTime)).
			   collect(Collectors.toList());
	}
	
}
