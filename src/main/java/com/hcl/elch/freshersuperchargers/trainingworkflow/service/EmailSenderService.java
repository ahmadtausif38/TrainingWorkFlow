package com.hcl.elch.freshersuperchargers.trainingworkflow.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class EmailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	final Logger LOGGER = Logger.getLogger(EmailSenderService.class.getName());
	
	//this template will send email with links -Assesment & Approval links
	private String loadEmailTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/email-template.html");
        byte[] fileBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(fileBytes, StandardCharsets.UTF_8);
    }
	
	//this template will send email without link -> failure ,completion and POC
	private String loadFail_CompleteAndPocEmailTemplate() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/email-template-fail.html");
        byte[] fileBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(fileBytes, StandardCharsets.UTF_8);
    }
	
	
	public String mailSendingForTask(String username, String Email, String Task, String assessmentLink) throws MessagingException {
		LOGGER.info("Inside the mailSendingForTask() method now........");
		//System.out.println("Assesment link :- "+assessmentLink);
		try {
			String htmlTemplate = loadEmailTemplate();
		
			String body="Your assesment for task "+Task+" is started.";
			String msg="Please complete this assesment within 2 days.";
			String n="SuperCharge Team";
			String emailContent = htmlTemplate
                 .replace("{username}", username)
                 .replace("{url}", "https://practice.geeksforgeeks.org/problems/search-an-element-in-an-array-1587115621/1?page=1&difficulty[]=-1&category[]=Arrays&sortBy=submissions")
		 		 .replace("{body}",body)
		 		 .replace("{message}",msg)
		 		 .replace("{name}", n);
		 
		 
		   MimeMessage message = mailSender.createMimeMessage();
	       MimeMessageHelper helper = new MimeMessageHelper(message, true);
	       helper.setSubject("Assesment for "+Task);
	       helper.setTo(Email);
	       helper.setText(emailContent, true);
				
		mailSender.send(message);
		
		System.out.println("Message sent successfully for task to :- "+Email);
		
		
		}catch(Exception e) {
			System.out.println("Exception encountered while sending task mail..");
		}
		return "Message sent successfully for task to :- "+Email;
	}
	
	
	public String mailSendingForNextTask(String username, String Email, String nextTask, LocalDate duedate) throws MessagingException {
		LOGGER.info("Inside the mailSendingForNextTask() method now........");
		
		try {
			String htmlTemplate = loadFail_CompleteAndPocEmailTemplate();
			String body="<b>"+nextTask+"</b> Training has been started and it is due for completion of <b>"+duedate+"</b>";
			String msg="Please complete it before due date";
			String n="SuperCharge Team";
			String emailContent = htmlTemplate
                 .replace("{username}", username)
                // .replace("{url}", "https://practice.geeksforgeeks.org/problems/search-an-element-in-an-array-1587115621/1?page=1&difficulty[]=-1&category[]=Arrays&sortBy=submissions")
		 		 .replace("{body}",body)
		 		 .replace("{message}",msg)
		 		 .replace("{name}", n);
		 
		 
		   MimeMessage message = mailSender.createMimeMessage();
	       MimeMessageHelper helper = new MimeMessageHelper(message, true);
	       helper.setSubject("Next task assigned");
	       helper.setTo(Email);
	       helper.setText(emailContent, true);
				
		mailSender.send(message);
		
		System.out.println("Next task mail has sent successfully..");
		
		
		}catch(Exception e) {
			System.out.println("Exception encountered while sending next task mail..");
		}
		return "Message sent successfully for next task to :- "+username;
	}
	
	public String mailSendingForTaskCompletion(String username, String Email) throws MessagingException {
		LOGGER.info("Inside the mailSendingForNextTask() method now........");
		
		try {
			String htmlTemplate = loadFail_CompleteAndPocEmailTemplate();
			String body="<b>Congratulation!,</b> you have successfully completed the training.";
			String msg="wait for next update";
			String n="SuperCharge Team";
			String emailContent = htmlTemplate
                 .replace("{username}", username)
                // .replace("{url}", "https://practice.geeksforgeeks.org/problems/search-an-element-in-an-array-1587115621/1?page=1&difficulty[]=-1&category[]=Arrays&sortBy=submissions")
		 		 .replace("{body}",body)
		 		 .replace("{message}",msg)
		 		 .replace("{name}", n);
		 
		 
		   MimeMessage message = mailSender.createMimeMessage();
	       MimeMessageHelper helper = new MimeMessageHelper(message, true);
	       helper.setSubject("Training completion");
	       helper.setTo(Email);
	       helper.setText(emailContent, true);
				
		mailSender.send(message);
		
		System.out.println("Task completion mail has sent successfully..");
		
		
		}catch(Exception e) {
			System.out.println("Exception encountered while sending task completion mail..");
		}
		return "Message sent successfully for task completion to :- "+username;
	}
	
	public String mailSendingFailureTask(String username, String Email, String task) {
		LOGGER.info("Inside the mailSendingFailureTask() method now........");

		try {
			String htmlTemplate = loadFail_CompleteAndPocEmailTemplate();
		
			String body="You are unable to clear the <b>"+task +"</b> assesment" ;
			String msg="Please re-attempt the assesment within 2 days.";
			String n="SuperCharge Team";
			String emailContent = htmlTemplate
                 .replace("{username}", username)
                 .replace("{body}",body)
		 		 .replace("{message}",msg)
		 		 .replace("{name}", n);
		 
		 
		   MimeMessage message = mailSender.createMimeMessage();
	       MimeMessageHelper helper = new MimeMessageHelper(message, true);
	       helper.setSubject("Result for "+task +" assesment.");
	       helper.setTo(Email);
	       helper.setText(emailContent, true);
				
		mailSender.send(message);
		
		System.out.println("Message sent for failure task to :- "+Email);
		
		
		}catch(Exception e) {
			System.out.println("Exception encountered while sending task failure mail..");
		}
		return "Message sent for failure task to :- "+Email;
	
	}
	
	public String mailSendingForPoc(String name, String Email, String Task,String branch,String githuburl) throws MessagingException {
		LOGGER.info("Inside the mailSendingForPoc() method now........");
		
		try {
			String htmlTemplate = loadFail_CompleteAndPocEmailTemplate();
		
			String body="Your POC for task <b>"+Task+"</b> is started."+"Commit Your Code into given github repository. <b>"+githuburl+"</b> Create a new branch with given branch name and commit into that branch. <b>"+branch+"</b>" ;
			String msg="Please complete it as soon as possible.";
			String n="SuperCharge Team";
			String emailContent = htmlTemplate
                 .replace("{username}", name)
                // .replace("{url}", "https://practice.geeksforgeeks.org/problems/search-an-element-in-an-array-1587115621/1?page=1&difficulty[]=-1&category[]=Arrays&sortBy=submissions")
		 		 .replace("{body}",body)
		 		 .replace("{message}",msg)
		 		 .replace("{name}", n);
		 
		 
		   MimeMessage message = mailSender.createMimeMessage();
	       MimeMessageHelper helper = new MimeMessageHelper(message, true);
	       helper.setSubject("POC for "+Task);
	       helper.setTo(Email);
	       helper.setText(emailContent, true);
				
		mailSender.send(message);
		
		System.out.println("Message sent successfully for POC to :- "+Email);
		
		
		}catch(Exception e) {
			System.out.println("Exception encountered while sending POC mail..");
		}
		return "Message sent successfully for POC to :- "+Email;
	}
	
	
	public String mailSendingFailurePoc(String username, String Email, String task) {
		LOGGER.info("Inside the mailSendingFailurePoc() method now........");

		try {
			String htmlTemplate = loadFail_CompleteAndPocEmailTemplate();
		
			String body="You are unable to complete the <b>"+task +"</b> POC." ;
			String msg="Please complete the POC as soon as possible.";
			String n="SuperCharge Team";
			String emailContent = htmlTemplate
                 .replace("{username}", username)
                 .replace("{body}",body)
		 		 .replace("{message}",msg)
		 		 .replace("{name}", n);
		 
		 
		   MimeMessage message = mailSender.createMimeMessage();
	       MimeMessageHelper helper = new MimeMessageHelper(message, true);
	       helper.setSubject("Result for "+task +" POC.");
	       helper.setTo(Email);
	       helper.setText(emailContent, true);
				
		mailSender.send(message);
		
		System.out.println("Message sent for failure POC to :- "+Email);
		
		
		}catch(Exception e) {
			System.out.println("Exception encountered while sending failed POC mail..");
		}
		return "Message sent for failure POC to :- "+Email;
	
	
	}

	public String mailSendingToApprover(String team, String Email[], String task, String url, String username) {
		LOGGER.info("Inside the mailSendingToApprover() method now........");
		//System.out.println("Inside mailSendingToApprover() method ...");
		System.out.println("Task name :- "+task);
		
		try {
			String htmlTemplate = loadEmailTemplate();
		//Springboot assesment for user has been completed and pending for your approval
			String body="<b>"+task+"</b> assesment for <b>"+username+"</b> has been completed and pending for your approval, click here to take action";
			String msg="Please review the response as soon as possible.";
			String n="SuperCharge Team";
			
			String emailContent = htmlTemplate
                 .replace("{username}", team)
                 .replace("{url}", url)
		 		 .replace("{body}",body)
		 		 .replace("{message}",msg)
		 		 .replace("{name}", n);
		 
		 
		   MimeMessage message = mailSender.createMimeMessage();
	       MimeMessageHelper helper = new MimeMessageHelper(message, true);
	       helper.setSubject("Approval for "+task);
	       helper.setTo(Email);
	       helper.setText(emailContent, true);
				
		mailSender.send(message);
		
		System.out.println("Message sent successfully to approver:- "+team);
		
		
		}catch(Exception e) {
			System.out.println("Exception encountered while sending mail to Approver..");
		}
		return "Message sent successfully to approver:- "+team;
	}
	
	
	public String mailSendingForPocApprover(String team, String Email[], String task, String url, String username,String branch,String githuburl) {
		LOGGER.info("Inside the mailSendingForPocApprover() method now........");
		//System.out.println("Inside mailSendingToApprover() method ...");
		System.out.println("Task name :- "+task);
		
		try {
			String htmlTemplate = loadEmailTemplate();
		
			String body="<b>"+task+"</b> POC for <b>"+username+"</b> has been completed and pending for your approval, click here to take action";
			String msg="The Code is available in this repository <b>"+githuburl+"</b> under <b>"+branch+"</b> Review it and Please take action as soon as possible.";
			String n="SuperCharge Team";
			
			String emailContent = htmlTemplate
                 .replace("{username}", team)
                 .replace("{url}", url)
		 		 .replace("{body}",body)
		 		 .replace("{message}",msg)
		 		 .replace("{name}", n);
		 
		 
		   MimeMessage message = mailSender.createMimeMessage();
	       MimeMessageHelper helper = new MimeMessageHelper(message, true);
	       helper.setSubject("Approval for "+task+" POC.");
	       helper.setTo(Email);
	       helper.setText(emailContent, true);
				
		mailSender.send(message);
		
		System.out.println("Message sent successfully to POC approver:- "+team);
		
		
		}catch(Exception e) {
			System.out.println("Exception encountered while sending mail for POC Approver..");
		}
		return "Message sent successfully to approver:- "+team;
	}
}
