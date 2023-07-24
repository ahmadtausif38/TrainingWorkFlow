package com.hcl.elch.freshersuperchargers.trainingworkflow.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.workflow;

public interface WorkflowRepo extends JpaRepository<workflow, Long>{

	List<workflow> findBycategory(long Id);

}
