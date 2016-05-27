package com.pm.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Patient")
public class Patient extends AbstractModel {

	//private String idOfPatient; id dziedziczy po AbstractModel

	private String description;

	public Patient(){}

	public Patient(String des){ this.description = des;}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
