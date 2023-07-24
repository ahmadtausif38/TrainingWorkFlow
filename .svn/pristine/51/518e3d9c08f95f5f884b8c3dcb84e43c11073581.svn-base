package com.hcl.elch.freshersuperchargers.trainingworkflow.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions.DroolsEngineException;
import com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions.KafkaReceiverException;
 
@Configuration
public class Config {
 
    private static final String RULES_CUSTOMER_RULES_DRL = "rules/tasks.drl";
    private static final KieServices kieServices = KieServices.Factory.get();
 
    @Bean
    public KieContainer kieContainer() throws DroolsEngineException
    {
    	try {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_CUSTOMER_RULES_DRL));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        return kieContainer;
    	}
    	catch(Exception e)
    	{
    		//System.out.println("Caught the Drools Exception");
    		throw new DroolsEngineException("Unable to perform the Drools Task,Because of drl file", e);
    	}
  }
}
