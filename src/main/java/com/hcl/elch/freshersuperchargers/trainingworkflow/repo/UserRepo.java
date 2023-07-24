package com.hcl.elch.freshersuperchargers.trainingworkflow.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

	//User findBysapId(long l);

	User findBysapId(long userId);

}
