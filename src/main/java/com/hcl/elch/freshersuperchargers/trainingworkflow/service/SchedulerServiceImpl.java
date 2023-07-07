package com.hcl.elch.freshersuperchargers.trainingworkflow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hcl.elch.freshersuperchargers.trainingworkflow.controller.TaskController;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.SchedulerRepository;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.TaskRepo;

@Service
public class SchedulerServiceImpl implements SchedulerService{
	
	@Autowired
	private SchedulerRepository repo;
	
	@Autowired
	private TaskRepo rep;
	
	@Autowired
	private TaskController cc;
	
	//scheduler for fetch data daily 12am & 12pm

	@Scheduled(cron = "*/60 * * * * *")
	public void fetchTask() {
	 try {
		List<Task> t=repo.findByStatus("InProgress");
		for(Task task1:t) { 
			System.out.println("Schedular Data : "+task1);
			task1.setStatus("Processing");
            rep.save(task1);
			cc.getDetails(task1);
		}	
	 }
	 catch(Exception e)
	 {
		 System.out.println("Exception occured, Unable to perform Scheduler Task "+e);
	 }
  }
	
}
