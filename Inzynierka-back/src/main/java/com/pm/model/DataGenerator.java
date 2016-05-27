package com.pm.model;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataGenerator {

	private List<User> usersList = new ArrayList<User>();
	private List<Pattern> patternsList = new ArrayList<Pattern>();
	private List<Patient> patientsList = new ArrayList<Patient>();
	private List<Session> sessionsList = new ArrayList<Session>();
	private List<Node> nodesList = new ArrayList<Node>();
	private List<Move> movesList = new ArrayList<Move>();

	public DataGenerator(){

	}

	private String[] loginA = {
		"da", "adam", "babe", "bibi", "ali", "gda", "tran", "pran", "eca", "ewa", "huh", "uch", "musl", "wials", "blis", "aszli", "poszq", "lal"
	};

	private String[] lastNamesA = {
			"Pujols", "Edmonds", "Hamilton", "Trout", "Gonzalez", "Fielder", "Strasburg", "Bautista"
	};

	private String[] firstNameA = {
			"Noah",	"Emma", "Liam", "Olivia", "Mason","Sophia", "Jacob","Isabella" ,"Ava","Ethan","Mia","Michael", "Emily",
			"Alexander", "Abigail", "James" ,"Madison", "Daniel", "Charlotte"
	};


	private String[] emailA = {
			"fdsdfjd@gmail.com", "dsgjd@gmail.com", "puyotr@gmail.com" , "fdssad@onet.com", "aaaa@gmail.com", "fada@interia.com", "dfsfhs@interia.com"
	};

	public Move generateMoves(){
		Move move = new Move();

		return move;
	}
	public Pattern generatePattern(int i){
		Pattern p = new Pattern();
		p.setTitle("Pattern number: "+ i);
		p.setBody("something something");
		return p;
	}

	public Patient generatePatient(int i){
		Patient p = new Patient();
		p.setDescription("Patient number: "+ i);
		return p;
	}

	public Session generateSession(){
		Session s = new Session();
		return s;
	}

	public Node generateNode(int i){
		Node n = new Node();
		n.setLabelOfNode("Number of node: "+ i);
		return n;
	}
	//studnet
	public User generateUser(){

		Random rn = new Random();
		int i = Math.abs(rn.nextInt());
		String firstName = firstNameA[i%(this.firstNameA.length)];
		String lastName = lastNamesA[i%(this.lastNamesA.length)];
		String login = loginA[i%(this.loginA.length)];
		String password = login + firstName + lastName;
		String email= emailA[i%(this.emailA.length)];
		User user = new User();
		user.setLogin(login);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setEmail(email);
		user.setPassword(password);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		user.setLastLog(dateFormat.format(date));
		return user;
	}
	//przedmiot


	public void generate(){
		for (int i = 0 ; i < 20; i = i + 1){
			usersList.add(generateUser());
		}
		for (int i = 0; i < 40; i = i + 1){
			movesList.add(generateMoves());
			nodesList.add(generateNode(i));
		}
		for (int i = 0; i < 10; i = i + 1){
			patientsList.add(generatePatient(i));
			patternsList.add(generatePattern(i));
			sessionsList.add(generateSession());
		}
		usersList.add(addMock());
	}

	public User addMock(){
		User u = new User();
		u.setEmail("ab@op.pl");
		u.setFirstName("Iza");
		u.setLastName("Sz");
		u.setPassword("ble");
		List<String> sss = new ArrayList<>();
		sss.add("pref1");
		sss.add("pref2");
		u.setHashPreferences(sss);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		u.setLastLog(dateFormat.format(date));
		return u;
	}

	public List<User> getUsersList() {
		return usersList;
	}
	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}
	public List<Pattern> getPatternsList() {
		return patternsList;
	}
	public void setPatternsList(List<Pattern> patternsList) {
		this.patternsList = patternsList;
	}
	public List<Patient> getPatientsList() {
		return patientsList;
	}
	public void setPatientsList(List<Patient> patientsList) {
		this.patientsList = patientsList;
	}

	public List<Session> getSessionsList() {
		return sessionsList;
	}

	public void setSessionsList(List<Session> sessionsList) {
		this.sessionsList = sessionsList;
	}

	public List<Node> getNodesList() {
		return nodesList;
	}

	public void setNodesList(List<Node> nodesList) {
		this.nodesList = nodesList;
	}

	public List<Move> getMovesList() {
		return movesList;
	}

	public void setMovesList(List<Move> movesList) {
		this.movesList = movesList;
	}
}
