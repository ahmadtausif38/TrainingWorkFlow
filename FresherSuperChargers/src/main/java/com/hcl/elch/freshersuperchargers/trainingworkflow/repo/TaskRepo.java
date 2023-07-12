package com.hcl.elch.freshersuperchargers.trainingworkflow.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {

	List<Task> getByuserId(long userId);
	
	Task getBytaskId(long taskId);
	
	Task getById(long id);

}
