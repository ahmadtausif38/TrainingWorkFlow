package com.hcl.elch.freshersuperchargers.trainingworkflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.User;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Category;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.CategoryRepo;
import com.hcl.elch.freshersuperchargers.trainingworkflow.repo.TaskRepo;


@Service
public class CategoryService {

	@Autowired
	private CategoryRepo cr;
	
	@Autowired
	private TaskRepo tr;
	
	public Category get(long user,long id) throws Exception
	{
		try {
			
			Category c=cr.findById(user).get();
				return c;
		} 
		catch(Exception e)
		{
			Task t=tr.findById(id).get();
			t.setStatus("Error");
			tr.save(t);
			throw new Exception("Exception Occured unable to fetch values of id, Id is null or Incorrect ");
		}
		
	}
	
	
	
}
