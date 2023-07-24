/*package com.hcl.elch.freshersuperchargers.trainingworkflow.config;


import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;

import com.hcl.elch.freshersuperchargers.trainingworkflow.controller.TaskController;
import com.hcl.elch.freshersuperchargers.trainingworkflow.entity.Task;
import com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions.CamundaException;


@Service
public class Consumer {
	
	@Autowired
	private TaskController cc;
	
	@Bean
	public StringJsonMessageConverter jsonConverter() {
	    return new StringJsonMessageConverter();
	}
	
	
	@KafkaListener(topics = "newTask", groupId = "myGroup1")
	public void receiveFomKafka(Task t) throws KafkaException, CamundaException {
	 try {
		System.out.println("Message received :- "+t.toString());
		cc.getDetails(t);
	}
	catch(KafkaException e)
	 {
		throw new KafkaException("Unable to receive messages from producer",e); 
	 }
	}
}
*/
