package com.fabricio.jobscheduler.models;

import java.time.LocalDateTime;

import com.fabricio.jobscheduler.models.handler.DateHandler;
import com.fabricio.jobscheduler.models.handler.IntegerHandler;
import com.fabricio.jobscheduler.models.handler.LongHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
	
	@JsonProperty("ID")
	@JsonDeserialize(using = LongHandler.class)
	private Long id;
	
	@JsonProperty("Descrição")
	@JsonIgnore
	private String description;
	
	@JsonProperty("Data Máxima de conclusão")
	@JsonDeserialize(using = DateHandler.class)
	private LocalDateTime deadLine;

	@JsonProperty("Tempo estimado")
	@JsonDeserialize(using = IntegerHandler.class)
	private Integer executionTime;

}
