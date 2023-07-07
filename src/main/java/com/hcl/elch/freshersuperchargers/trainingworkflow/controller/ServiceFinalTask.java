package com.hcl.elch.freshersuperchargers.trainingworkflow.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.User;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Category;
import com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions.CamundaException;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.CategoryRepo;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.TaskRepo;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.UserRepo;
import com.hcl.elch.freshersuperchargers.trainingworkflow.service.EmailSenderService;
import com.hcl.elch.freshersuperchargers.trainingworkflow.service.TaskServiceImpl;



@Component
public class ServiceFinalTask implements JavaDelegate{
	
	
	@Autowired
	 private TaskServiceImpl ts;
	
	@Autowired
	 private TaskRepo tr;
	 
	@Autowired 
	private CategoryRepo cr;
	
	@Autowired 
	private UserRepo ur;
	
	public Task newTask;
	
	@Autowired
	private EmailSenderService emailSenderService;

	@Override
	public void execute(DelegateExecution execution) throws CamundaException{
	 try {
		System.out.println("/////////////////////Final Service task///////////////////");
		Task task=new Task();
		String date= (String) execution.getVariable("duedate");
		String userid= (String) execution.getVariable("userId");
		String taskid= (String) execution.getVariable("TaskId");
		
		String username= (String) execution.getVariable("username");
		String email=(String) execution.getVariable("Email");
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate  d1 = LocalDate.parse(date, df);
		//System.out.println("Approver By"+execution.getVariable("approver")); 
		task.setTask((String) execution.getVariable("task"));
		task.setStatus((String) execution.getVariable("status"));
		task.setDuedate(d1);
		task.setUserId(Long.parseLong(userid));
		task.setTaskId(Long.parseLong(taskid));
		String Approver= TaskAssignmentListener.Assignee;
		task.setApprover((String) execution.getVariable("approver"));
		System.out.println(d1);
		System.out.println(task.getTask());
		nextTask(task);
		
		System.out.println("Current task obj from ServiceFinalTask :- "+task.toString());
		System.out.println("Next task obj from ServiceFinalTask :- "+newTask.toString());
		
		//boolean hasNext=true;
		if(newTask.getTaskId()==task.getTaskId()) {
			//hasNext=false;
			String s=emailSenderService.mailSendingForTaskCompletion(username, email);
		}else {
			String s=emailSenderService.mailSendingForNextTask(username, email, newTask.getTask(), newTask.getDuedate());
		}
		
	  }
	  catch(Exception e)
	 {
		  System.out.println("An Exception from Final Service Task Due to ");
		  e.printStackTrace();
		  throw new BpmnError("Camunda Exception",e);
	 }
	 
	}
	

    public void nextTask(Task task) {
    try
    {
    	System.out.println(task.getUserId());
    	User u=ur.findBysapId(task.getUserId());
    	System.out.println(u.toString()+" "+u.getCategory().getCategory());
    	Category c=cr.findById(u.getCategory().getUserId()).get();
    	System.out.println(c.getCategory()+" "+c.getUserId());
        //Task st=ts.getStatus(task,c);
    	Task st=ts.getStatus(task,c);
    	//nextTask
    	newTask=st;
    	
        ts.setComplete(task);
        System.out.println(st.getTask());
        ts.save(st);
    }
    catch(Exception e)
    {
    	System.out.println("Unable to move to next Task "+e);
    }
}}
