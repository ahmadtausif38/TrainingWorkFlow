package com.hcl.elch.freshersuperchargers.trainingworkflow;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;



@SpringBootApplication
@EnableProcessApplication
@EnableScheduling
@EntityScan(basePackages = {"com.hcl.elch.freshersuperchargers.trainingworkflow.entity"})
public class TrainingWorkflowApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrainingWorkflowApplication.class, args);
	}
}
