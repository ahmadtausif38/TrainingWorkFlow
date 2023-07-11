package com.hcl.elch.freshersuperchargers.trainingworkflow.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.ProjectWorkflow;

public interface ProjectWorkflowRepo extends JpaRepository<ProjectWorkflow, Long>{

	List<ProjectWorkflow> findByuser_sapId(long l);

}
