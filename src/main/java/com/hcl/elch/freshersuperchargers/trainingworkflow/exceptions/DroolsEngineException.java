package com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions;

public class DroolsEngineException extends Exception 
{

	public  DroolsEngineException(String str,Exception e) {	
		super(str);
		e.printStackTrace();
		System.out.println(e);
	}
}
