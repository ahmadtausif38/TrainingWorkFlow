package com.hcl.elch.freshersuperchargers.trainingworkflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "assessment_master")
public class Assessment {
	
	@Id
	@Column(name="id")
	private long id;

	@Column(name="module_id")
	private long moduleId;
	
	@Column(name="assessment_link")
	private String assessmentLink;
	
}
