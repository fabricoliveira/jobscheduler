package com.fabricio.jobscheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fabricio.jobscheduler.models.Job;
import com.fabricio.jobscheduler.service.JobService;
import com.fabricio.jobscheduler.service.JsonService;

@SpringBootTest
class JobServiceTest {

	private final LocalDateTime startExecutionDateTime = LocalDateTime.of(2019, 11, 10, 9, 0, 0);
	private final LocalDateTime finishExecutionDateTime = LocalDateTime.of(2019, 11, 11, 12, 0, 0);

	
	@Autowired
	private JobService jobService;
	@Autowired
	private JsonService jsonService;
	
	
	@Test
	void successOnValidDeadline() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-10 12:00:00, \n" + 
				"        \"Tempo estimado\": 2 horas,\n" + 
				"    },\n" + 
				"]");
		
		List<Job> jobs = jsonService.jsonToListOfJobs(json);
		
		boolean isValidForExecution = jobService.isJobValidForExecution(jobs.get(0), startExecutionDateTime, finishExecutionDateTime, 8);
		
		assertTrue(isValidForExecution);
	}
	
	@Test
	void failOnInvalidStartExecutionDateTime() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-10 08:59:59, \n" + 
				"        \"Tempo estimado\": 2 horas,\n" + 
				"    },\n" + 
				"]");
		
		List<Job> jobs = jsonService.jsonToListOfJobs(json);
		boolean isValidForExecution = jobService.isJobValidForExecution(jobs.get(0), startExecutionDateTime, finishExecutionDateTime, 8);
		
		assertFalse(isValidForExecution);
	}
	
	@Test
	void failOnInvalidFinishExecutionDateTime() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-11 12:00:01, \n" + 
				"        \"Tempo estimado\": 2 horas,\n" + 
				"    },\n" + 
				"]");
		
		List<Job> jobs = jsonService.jsonToListOfJobs(json);
		boolean isValidForExecution = jobService.isJobValidForExecution(jobs.get(0), startExecutionDateTime, finishExecutionDateTime, 8);
		
		assertFalse(isValidForExecution);
	}
	
	@Test
	void failOnInvalidExecutionTime() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-11 12:00:00, \n" + 
				"        \"Tempo estimado\": 9 horas,\n" + 
				"    },\n" + 
				"]");
		
		List<Job> jobs = jsonService.jsonToListOfJobs(json);
		boolean isValidForExecution = jobService.isJobValidForExecution(jobs.get(0), startExecutionDateTime, finishExecutionDateTime, 8);
		
		assertFalse(isValidForExecution);
	}
	
	@Test
	void successOnRemovingAllJobsNotValidForSpecifiedExecutionPeriod() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-10 12:00:00, \n" + 
				"        \"Tempo estimado\": 2 horas,\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"ID\": 2,\n" + 
				"        \"Descrição\": \"Importação de dados da Base Legada\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-11 12:00:01, \n" + 
				"        \"Tempo estimado\": 4 horas,   \n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"ID\": 3,\n" + 
				"        \"Descrição\": \"Importação de dados de integração\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-10 08:00:00, \n" + 
				"        \"Tempo estimado\": 6 horas,   \n" + 
				"    },\n" + 
				"]");
		
		List<Job> jobs = jsonService.jsonToListOfJobs(json);
		jobs = jobService.removeAllJobsNotValidForSpecifiedExecutionPeriod(jobs, startExecutionDateTime, finishExecutionDateTime, 8);
		
		assertEquals(1, jobs.size());
	}
	
	@Test
	void successOnSortJobByDateAndExecutionTime() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-10 12:00:00, \n" + 
				"        \"Tempo estimado\": 2 horas,\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"ID\": 2,\n" + 
				"        \"Descrição\": \"Importação de dados da Base Legada\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-11 12:00:00, \n" + 
				"        \"Tempo estimado\": 4 horas,   \n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"ID\": 3,\n" + 
				"        \"Descrição\": \"Importação de dados de integração\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-11 08:00:00, \n" + 
				"        \"Tempo estimado\": 6 horas,   \n" + 
				"    },\n" + 
				"]");
		
		List<Job> jobs = jsonService.jsonToListOfJobs(json);
		List<Job> sortedJobs = jobService.sortJobByDateAndExecutionTime(jobs);
		
		assertTrue(sortedJobs.get(0) == jobs.get(0) &&
				   sortedJobs.get(1) == jobs.get(2) &&
				   sortedJobs.get(2) == jobs.get(1));
	}
	
	@Test
	void successOnJobScheduling() {
		String json = jsonService.addQuotesToJsonValues("[\n" + 
				"    {\n" + 
				"        \"ID\": 1,\n" + 
				"        \"Descrição\": \"Importação de arquivos de fundos\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-10 12:00:00, \n" + 
				"        \"Tempo estimado\": 2 horas,\n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"ID\": 2,\n" + 
				"        \"Descrição\": \"Importação de dados da Base Legada\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-11 12:00:00, \n" + 
				"        \"Tempo estimado\": 4 horas,   \n" + 
				"    },\n" + 
				"    {\n" + 
				"        \"ID\": 3,\n" + 
				"        \"Descrição\": \"Importação de dados de integração\", \n" + 
				"        \"Data Máxima de conclusão\": 2019-11-11 08:00:00, \n" + 
				"        \"Tempo estimado\": 6 horas,   \n" + 
				"    },\n" + 
				"]");
		
		Long[][] array = {{1L, 3L}, {2L}};
		List<List<Long>> ids = new ArrayList<>();
		ids.add(Arrays.asList(array[0]));
		ids.add(Arrays.asList(array[1]));
		
		List<Job> jobs = jsonService.jsonToListOfJobs(json);
		jobs = jobService.sortJobByDateAndExecutionTime(jobs);
		List<List<Long>> scheduledJobs = jobService.jobScheduler(jobs, startExecutionDateTime, finishExecutionDateTime, 8);
		
		assertEquals(ids, scheduledJobs);
	}
}
