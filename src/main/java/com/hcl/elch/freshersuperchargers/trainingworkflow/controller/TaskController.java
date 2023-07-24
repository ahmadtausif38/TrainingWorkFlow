package com.hcl.elch.freshersuperchargers.trainingworkflow.controller;


import java.time.LocalDate;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import com.hcl.elch.freshersuperchargers.trainingworkflow.config.Consumer;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Modules;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Category;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.User;
import com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions.CamundaException;
import com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions.UserTaskException;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.ModuleRepo;
//import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.SchedulerRepository;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.TaskRepo;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.UserRepo;
import com.hcl.elch.freshersuperchargers.trainingworkflow.service.SchedulerService;
import com.hcl.elch.freshersuperchargers.trainingworkflow.service.TaskServiceImpl;
import com.hcl.elch.freshersuperchargers.trainingworkflow.service.CategoryService;


@RestController
public class TaskController implements JavaDelegate {
	
	 @Autowired
	 private RuntimeService runtimeService;
	
	 @Autowired
	 private TaskServiceImpl ts;
	 
	 public Task glob;
	@Autowired 
	private CategoryService cr;
	
	@Autowired 
	private UserRepo ur;
	
	@Autowired 
	private ModuleRepo mr;
	
	//@Autowired
	//private SchedulerRepository repo;
	
	@Autowired 
	private TaskRepo tr;
	
	@Autowired 
	RuntimeService  rs;
	
	     @Transactional
		 public void getDetails(Task task) throws CamundaException
		 {
			 try {
			    Map<String, Object> variables = new HashMap<String, Object>();
			    variables.put("glo",task);
		    	System.out.println("Start Event Connected");
		    	glob=task;
		    	RestTemplate rt=new RestTemplate();
		    	HttpHeaders httpHeaders=new HttpHeaders();
		    	httpHeaders.add("Content_Type","application/json");
		    	HttpEntity<Task> httpen=new HttpEntity<>(task,httpHeaders);
		    	ResponseEntity<String> re=rt.postForEntity("http://localhost:9002/engine-rest/process-definition/key/Decision/start", httpen, String.class);
			 }
			 catch(Exception e)
			 {
				 throw new CamundaException("Camunda Exception, Unable to start the process",e);
			 }
		 }
	
	  //returning user object instead -> previous method was "private String camundaTask(Task t)"
	    private User camundaTask(Task t) throws UserTaskException
	    {
	    	try {
	    		User u=ur.findBysapId(t.getUserId());
	    		System.out.println("User EMAIL :- "+u.getEmail()+" Username :- "+u.getName());
	    		//return u.getEmail();
	    		
	    		return u;
	    	}
	    	catch(Exception e) 
	    	{
	    		throw new UserTaskException("Exception Occured, Unable to fetch Email Id",e);
	    	}
	    }
	    
	    
	    
	    
	    public Modules category(Task t) throws UserTaskException
	    {
	    	try {
	    		Modules m=mr.getBymoduleName(t.getTask().toUpperCase());
	    		if(m == null)
	    		{
	    			Task t1=tr.getById(glob.getId());
	    			t1.setStatus("Error");
	    			tr.save(t1);
	    		}
	    		else {
	    			return m;
	    		}
	    	}
	    	catch(Exception e) 
	    	{
	    		throw new UserTaskException("Exception Occured, Module Name is Incorrect or Null",e);
	    	}
			return null;
	    }
	    
	   
	    
	    
	    
		@Override
		public void execute(DelegateExecution execution) throws CamundaException 
		{
		try {
			System.out.println("///////////This is a Service task to send EmailId///////////////");
			Task glo=(Task) execution.getVariable("glob");
			//String s=camundaTask(glob);
			//execution.setVariable("Email",s);
			
			//Adding these three lines
			User s=camundaTask(glob);
			execution.setVariable("Email",s.getEmail());
			execution.setVariable("username",s.getName());
			execution.setVariable("ProjectAssignation", s.getProjectAssignation());
			System.out.println(s.getProjectAssignation());
			
			Modules m=category(glob);
			execution.setVariable("mainid", glob);
			execution.setVariable("test", m.getExam());
			execution.setVariable("groupId",m.getGroupId());
			execution.setVariable("moduleId",m.getModuleId());
			execution.setVariable("poc", m.getPOC());
			execution.setVariable("task", glob.getTask().toUpperCase());
			execution.setVariable("TaskId", Long.toString(glob.getTaskId()));
			execution.setVariable("userId", Long.toString(glob.getUserId()));
			execution.setVariable("status", glob.getStatus());
			LocalDate localDate = glob.getDuedate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String date = localDate.format(formatter);
			execution.setVariable("duedate", date);
			System.out.println(glob.getStatus());
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			throw new BpmnError("Error,  Some Values are missing or wrong in task details",e);
		}
	  } 
		
}
