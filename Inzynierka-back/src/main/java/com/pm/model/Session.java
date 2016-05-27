package com.pm.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Document(collection = "Session")
public class Session extends AbstractModel {

	@DBRef
	private Patient patient;
	@DBRef
	private List<Move> moves = new ArrayList<Move>();

	public Session(){}

	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public List<Move> getMoves() {
		return moves;
	}
	public void setMove(List<Move> moves) {
		this.moves = moves;
	}
	public void addMove(Move m){
		this.moves.add(m);
	}

}
