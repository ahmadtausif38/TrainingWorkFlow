/*package com.hcl.elch.freshersuperchargers.trainingworkflow.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;

@Repository
public interface SchedulerRepository extends JpaRepository<Task, Long> {
	@Query(value="SELECT * FROM Task e WHERE e.status = ?1 and CURDATE() > e.duedate",nativeQuery = true)
	public List<Task> findByStatus(String status);
	

}*/
