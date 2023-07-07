package com.hcl.elch.freshersuperchargers.trainingworkflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Modules")
public class Modules {
	
	@Id
	@Column(name="moduleId")
	long moduleId;
	
	@Column(name = "moduleName")
	private String moduleName;
	
	@Column(name="Exam")
	private String Exam;
	
	@Column(name="groupId")
	private String groupId;
	
	@Column(name="duration")
	private int duration;
	
	@Column(name="POC")
	private String POC;


	public String getPOC() {
		return POC;
	}

	public void setPOC(String pOC) {
		POC = pOC;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public long getModuleId() {
		return moduleId;
	}

	public void setModuleId(long moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getExam() {
		return Exam;
	}

	public void setExam(String exam) {
		Exam = exam;
	}
}
