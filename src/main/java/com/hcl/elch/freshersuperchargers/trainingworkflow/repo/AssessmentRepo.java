package com.hcl.elch.freshersuperchargers.trainingworkflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Assessment;

@Repository
public interface AssessmentRepo extends JpaRepository<Assessment, Long>{
	
	Assessment findByModuleId(long moduleId);
}
