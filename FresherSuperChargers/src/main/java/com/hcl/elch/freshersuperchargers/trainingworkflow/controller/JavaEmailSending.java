package com.hcl.elch.freshersuperchargers.trainingworkflow.controller;
import camundajar.impl.com.google.gson.JsonObject;
import camundajar.impl.com.google.gson.JsonParser;


import org.camunda.bpm.engine.RuntimeService;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.kie.internal.task.api.TaskVariable.VariableType;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Assessment;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Modules;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;
import com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions.CamundaException;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.AssessmentRepo;
import com.hcl.elch.freshersuperchargers.trainingworkflow.service.EmailSenderService;

@Controller
public class JavaEmailSending implements JavaDelegate {

	@Autowired
	private TaskController tc;

	final Logger LOGGER = Logger.getLogger(JavaEmailSending.class.getName());

	@Autowired
	private AssessmentRepo repo;

	@Autowired
	RuntimeService rs;
	
//	public String Email;
//	public String Task;
//	public String assessmentLink;

	private EmailSenderService 	senderService;
	
	public JavaEmailSending(EmailSenderService senderService) {
		this.senderService = senderService;
	}

	
//	@EventListener(ApplicationReadyEvent.class)
//    public void sendMail() {
//		
//		System.out.println(Email+" -> "+Task+" -> "+assessmentLink);
//		
////		 senderService.sendEmail(Email, Task, assessmentLink);
//		  
//    }
	
	/*
	 
	public String mailSending(String Email, String Task, String assessmentLink) {
		
			//System.out.println(Email + " " + Task + " " + assessmentLink);
		
//	        LOGGER.info("sendMethod() method will be called now........");
//	        senderService.sendEmail(Email, Task, assessmentLink);
	        System.out.println(Email + " " + Task + " " + assessmentLink);
		
		
		
		  final String HOST = "smtp.gmail.com"; final String USER = "ahmadtausif38@gmail.com"; final String PWD = "pyknqtoebyfkunac";
		  
		  final Logger LOGGER = Logger.getLogger(JavaEmailSending.class.getName());
		  
		  String recipient = Email;
		  
		  if (recipient != null && !recipient.isEmpty()) {
		  
		  Email email = new SimpleEmail(); email.setCharset("utf-8");
		  email.setHostName(HOST); email.setAuthentication(USER, PWD); try {
		  email.setFrom("ahmadtausif38@gmail.com");
		  email.setSubject("Exam Link for "+Task);
		  email.setMsg("Please complete: Exam Link-"+assessmentLink);
		 
		  email.addTo(recipient);
		  
		  email.send(); LOGGER.info(
		  "Task Exam Link Email successfully sent to user '"); } catch (Exception e) {
		  LOGGER.log(Level.WARNING, "Could not send email", e); } } else {
		  LOGGER.warning("Not sending email to user, " +
		  " user has no email address."); }
		

		return "Emailing";
	}
	
	*/ 
	
	
//	private void createTeamsMeeting() {
//        try {
//            // Prepare the HTTP request
//            OkHttpClient client = new OkHttpClient();
//            MediaType mediaType = MediaType.parse("application/json");
//            String teamAccessToken=getTeamAccessToken();
//            // Build the JSON request body
//            String jsonBody = "{\"startDateTime\":\"2023-07-01T09:00:00\",\"endDateTime\":\"2023-07-01T10:00:00\"}";
//
// 
//
//            RequestBody body = RequestBody.create(jsonBody, mediaType);
//            Request request = new Request.Builder()
//                    .url("https://graph.microsoft.com/v1.0/me/onlineMeetings")
//                    .post(body)
//                    .addHeader("Authorization", "Bearer"+ teamAccessToken)
//                    .addHeader("Content-Type", "application/json").build();
//
// 
//
//            // Execute the HTTP request
//            Response response = client.newCall(request).execute();
//
// 
//
//            // Parse the response
//            if (response.isSuccessful()) {
//                String responseBody = response.body().string();
//                JsonParser parser=new JsonParser();
//                JsonObject jsonResponse=parser.parse(responseBody).getAsJsonObject();
//                String meetingInviteLink=jsonResponse.get("joinUrl").getAsString();
//                System.out.println("Invite link "+meetingInviteLink);
//
//            } 
//            else {
//                System.out.println("Failed to create meeting: "+response.code()+" "+response.message());
//            }
//        } 
//        catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error creating Teams meeting: " + e.getMessage(), e);
//        }
//    }
//

	
	

	@Override
	public void execute(DelegateExecution execution) throws CamundaException {
		try {
			System.out.println("///////////This is Email Sending Task about status////////////////");
			
//			Email = (String) execution.getVariable("Email");
//			Task = (String) execution.getVariable("task");
			
			String Email = (String) execution.getVariable("Email");
			String Task = (String) execution.getVariable("task");
			String test=(String) execution.getVariable("test");
			String poc=(String) execution.getVariable("poc");
			
			String username=(String) execution.getVariable("username");
			
			//System.out.println("Username from JavaEmailsending class :-"+username);
			
			long moduleId = (long) execution.getVariable("moduleId");
			Assessment assessment=repo.findByModuleId(moduleId);
			String decision= (String) execution.getVariable("Decision");
			if(test.equals("1") && poc.equals("0")) {
				
				String assessmentLink="null";
                if(assessment!=null) {
                    assessmentLink = assessment.getAssessmentLink();
                }
                System.out.println(assessmentLink);
              
				String s = senderService.mailSendingForTask(username,Email, Task.toUpperCase(), assessmentLink);
				
				//System.out.println(s);
			}
			if(test.equals("0") && poc.equals("1")) {
				System.out.println("mailing POC details");
				String s=senderService.mailSendingForPoc(username,Email, Task.toUpperCase());
			}
			if(test.equals("1") && poc.equals("1")) {
				if(decision==null) {
					
					String assessmentLink = assessment.getAssessmentLink();
					String s =senderService.mailSendingForTask(username,Email, Task.toUpperCase(), assessmentLink);
					//System.out.println(s);
				}
				else {
					if(decision.equals("Yes")) {
						System.out.println("mailing POC details");
						String s=senderService.mailSendingForPoc(username,Email, Task.toUpperCase());
						
					}
				}
				
			}
			
			//System.out.println(Email+" -> "+Task+" -> "+assessmentLink);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BpmnError("Exception Occured", e);
		}
	}

}



/*import org.camunda.bpm.engine.RuntimeService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.kie.internal.task.api.TaskVariable.VariableType;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;

import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;
import com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions.CamundaException;

public class JavaEmailSending implements JavaDelegate {
	
	@Autowired
	private TaskController tc;
	
	@Autowired
	RuntimeService rs;
	
  public String mailSending(String Email,String Task) {
	System.out.println(Email+" "+Task); 
	
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
        }

	  return "Emailing";
  }

  	@Override
  	public void execute(DelegateExecution execution) throws CamundaException 
  	{
  		try {
  		System.out.println("///////////This is Email Sending Task about status////////////////");
  		String Email=(String) execution.getVariable("Email");
  		String Task=(String) execution.getVariable("task");
  		String s=mailSending(Email,Task.toUpperCase());
  		System.out.println(s);
  		}catch(Exception e)
  		{
  			e.printStackTrace();
  			throw new BpmnError("Exception Occured", e);
  		}
	}
}*/