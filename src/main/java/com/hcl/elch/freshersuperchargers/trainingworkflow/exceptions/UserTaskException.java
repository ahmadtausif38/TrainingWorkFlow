package com.hcl.elch.freshersuperchargers.trainingworkflow.exceptions;

public class UserTaskException extends Exception {
	
	public UserTaskException(String s,Exception e)
	{
		super(s);
		e.printStackTrace();
		System.out.println(s);
	}

}
