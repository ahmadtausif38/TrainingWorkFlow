package com.hcl.elch.freshersuperchargers.trainingworkflow.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;

public class GithubUrlValidation implements JavaDelegate
{
	final Logger LOGGER = Logger.getLogger(JavaEmailSending.class.getName());
	
	/*public static void main(String args[]) throws InvalidRemoteException, TransportException, GitAPIException
	{
		
		Collection<Ref> refs;
        List<String> branches = new ArrayList<String>();
        try {
            refs = Git.lsRemoteRepository()
                    .setHeads(true)
                    .setRemote("https://github.com/ahmadtausif38/TrainingWorkFlow.git")
                    .call();
            for (Ref ref : refs) {
                branches.add(ref.getName().substring(ref.getName().lastIndexOf("/")+1, ref.getName().length()));
            }
            Collections.sort(branches);
        } catch (InvalidRemoteException e) {
            //LOGGER.error(" InvalidRemoteException occured in fetchGitBranches",e);
        	System.out.printf(" InvalidRemoteException occured in fetchGitBranches",e);
            e.printStackTrace();
        } catch (TransportException e) {
            //LOGGER.error(" TransportException occurred in fetchGitBranches",e);
        	System.out.printf("  TransportException occurred in fetchGitBranches",e);
        } catch (GitAPIException e) {
            //LOGGER.error(" GitAPIException occurred in fetchGitBranches",e);
        	System.out.printf(" GitAPIException occurred in fetchGitBranches",e);
        }
        
        System.out.println("List of Branches : "+branches);
	

		
	}*/

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("/****************GITHUB URL VALIDATION TASK******************/");
		String branch= (String) execution.getVariable("BranchName");
		String url=(String) execution.getVariable("githuburl");
		
		Collection<Ref> refs;
        List<String> branches = new ArrayList<String>();
        try {
            refs = Git.lsRemoteRepository()
                    .setHeads(true)
                    .setRemote(url)
                    .call();
            for (Ref ref : refs) {
                branches.add(ref.getName().substring(ref.getName().lastIndexOf("/")+1, ref.getName().length()));
            }
            Collections.sort(branches);
        } catch (InvalidRemoteException e) {
            //LOGGER.error(" InvalidRemoteException occured in fetchGitBranches",e);
        	System.out.printf(" InvalidRemoteException occured in fetchGitBranches",e);
            e.printStackTrace();
        } catch (TransportException e) {
            //LOGGER.error(" TransportException occurred in fetchGitBranches",e);
        	System.out.printf("  TransportException occurred in fetchGitBranches",e);
        } catch (GitAPIException e) {
            //LOGGER.error(" GitAPIException occurred in fetchGitBranches",e);
        	System.out.printf(" GitAPIException occurred in fetchGitBranches",e);
        }
        
        System.out.println("List of Branches : "+branches);
        
        for(String b : branches)
        {
        	if(b.equalsIgnoreCase(branch))
        	{
        		execution.setVariable("Decision","Yes");
        		System.out.println("Decision is Yes");
        		break;
        	}
        	else {
        		execution.setVariable("Decision", "No");
        		System.out.println("Decision is No");
        		break;
        	}
        }
	
		
	}

}
