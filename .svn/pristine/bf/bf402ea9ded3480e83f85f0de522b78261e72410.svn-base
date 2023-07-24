package com.hcl.elch.freshersuperchargers.trainingworkflow.controller;

import java.time.LocalDate;
import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.User;
import com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions.CamundaException;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.TaskRepo;
import com.hcl.elch.freshersuperchargers.trainingworkflow.service.EmailSenderService;
import com.hcl.elch.freshersuperchargers.trainingworkflow.service.TaskServiceImpl;

@Component
public class EmailForUnSuccess implements JavaDelegate {
	
	@Autowired
	private TaskController tc;
	
	@Autowired
	private TaskRepo tr;
	
	@Autowired
	RuntimeService rs;
	
	@Autowired
	private EmailSenderService 	senderService;
	
	
  public String mailSending(String Email,String Task) {
	System.out.println(Email+" "+Task); 
	//tc.settingStatus();
	  /*final String HOST = "";
	  final String USER = "";
	  final String PWD = "";
	  
	  final Logger LOGGER = Logger.getLogger(JavaEmailSending.class.getName());
	
	  String recipient = Email;
	  
	  if (recipient != null && !recipient.isEmpty()) {

          Email email = new SimpleEmail();
          email.setCharset("utf-8");
          email.setHostName(HOST);
          email.setAuthentication(USER, PWD);
          try {
	            email.setFrom("noreply@camunda.org");
	            email.setSubject("Exam Link for "+Task);
	            email.setMsg("Please complete: Exam Link");

	            email.addTo(recipient);

	            email.send();
	            LOGGER.info(
	                "Task Exam Link Email successfully sent to user '");
	          } 
	          catch (Exception e) {
	            LOGGER.log(Level.WARNING, "Could not send email", e);
	          }
	  }
	  else {
          LOGGER.warning("Not sending email to user, "  + " user has no email address.");
        }*/

	  return "Emailing";
  }
  	@Override
  	public void execute(DelegateExecution execution) throws CamundaException 
  	{
  		try {
  		System.out.println("///////////This is Email Sending Task about status////////////////");
  		String Email=(String) execution.getVariable("Email");
  		String Task=(String) execution.getVariable("task");
  		
  		String username=(String) execution.getVariable("username");
		//System.out.println("Username from EmailForUnsuccess class :-"+username);
		
  		Task s1=(com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task) execution.getVariable("mainid");
  		//System.out.println("Due date : "+s1.getDuedate());
  		LocalDate lt= LocalDate.now();
  		
  		System.out.println("Current date : "+lt);
  		LocalDate datePlus1 = lt.plusDays(2);
  		System.out.println("Due date : "+datePlus1);
 		s1.setDuedate(datePlus1);
  		s1.setStatus("InProgress");
  		tr.save(s1);
  		String s=senderService.mailSendingFailureTask(username, Email,Task.toUpperCase());
  		System.out.println(s1.getStatus());
  		}catch(Exception e)
  		{
  			e.printStackTrace();
  			//System.out.println("Camunda Exception Occured In Mail Sending Task ");
  			throw new BpmnError("Exception Occured", e);
  		}
	}
}
