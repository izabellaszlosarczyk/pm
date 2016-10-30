package com.pm.model;


import com.pm.controllers.contentService.userContent.FileOperations;
import com.pm.database.SaveUpdateDatabase;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
	private List<File> filesList = new ArrayList<>();

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
	public List<File> generateFiles(){
		File f = new File();
		List<File> files = new ArrayList<>();
		List<String> c = new ArrayList<>();
		c.add("Comment 1. Ble ble ble , temporary comment for temporary testing. Idk what to write here");
		c.add("Comment 2. Ble ble ble , temporary comment for temporary testing. Idk what to write here");
		f.setComments(c);
		f.setTitle("Ivan_Ukhov_Doha_2010.jpg");
		List<Integer> s = new ArrayList<>();
		s.add(4);
		s.add(3);
		s.add(2);
		s.add(5);
		f.setScores(s);
		int tmp = 0, count = 0;
		for (int i : s){
			tmp = tmp + i;
			count++;
		}
		f.setAverage(String.valueOf(tmp/count) );
		f.setType("image");
		f.setCreationDate(LocalDate.now());
		files.add(f);


		File f3 = new File();
		System.out.println("dupa");
		c.add("Comment 3. Ble ble ble , temporary comment for temporary testing. Idk what to write here");
		c.add("Comment 4. Ble ble ble , temporary comment for temporary testing. Idk what to write here");
		f3.setAverage(String.valueOf(tmp+1/count) );
		f3.setTitle("Ivan_Ukhov_Doha_2011.jpg");
		f3.setComments(c);
		//files.add(f3);
		f3.setAverage(String.valueOf(tmp/count) );
		f3.setType("chart");

		File f2 = new File();
		f2.setAverage(String.valueOf(tmp/count));
		f2.setType("pattern");
		System.out.println("dupa");
		c.add("Comment 5. Ble ble ble , temporary comment for temporary testing. Idk what to write here");
		c.add("Comment 6. Ble ble ble , temporary comment for temporary testing. Idk what to write here");
		f2.setAverage(String.valueOf(tmp+1/count) );
		f2.setComments(c);
		f2.setTitle("Ivan_Ukhov_Doha_2012.jpg");
		files.add(f2);
		return files;
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
		List<String> files = new ArrayList<String>();
		files.add("Ivan_Ukhov_Doha_2010.jpg");
		User user = new User();
		user.setLogin(login);
		user.setLastName(lastName);
		user.setFirstName(firstName);
		user.setEmail(email);
		user.setSavedFiles(files);
		user.setSubscribedFiles(files);
		user.setPassword(password);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		user.setLastLog(dateFormat.format(date));
		System.out.println("dupa");
		try {
			FileOperations.saveFileToDatabase("https://upload.wikimedia.org/wikipedia/commons/c/cc/" , "Ivan_Ukhov_Doha_2010.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		user.setProfilePhoto("Ivan_Ukhov_Doha_2010.jpg");
		return user;
	}

	public void generate(){
		System.out.println("dupcia");
		for (int i = 0 ; i < 20; i = i + 1){
			usersList.add(generateUser());
		}
		for (int i = 0; i < 40; i = i + 1){
			movesList.add(generateMoves());
			nodesList.add(generateNode(i));
		}
		for (int i = 0; i < 10; i = i + 1){
			patientsList.add(generatePatient(i));
			sessionsList.add(generateSession());
		}
		for (int i = 0; i < 30; i = i + 1){
			patternsList.add(generatePattern(i));
		}
		usersList.add(addMock());
		filesList.addAll(generateFiles());
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
		List<String> files = new ArrayList<String>();
		files.add("Ivan_Ukhov_Doha_2010.jpg");
		files.add("Ivan_Ukhov_Doha_2011.jpg");
		files.add("Ivan_Ukhov_Doha_2012.jpg");
		u.setSavedFiles(files);
		u.setSubscribedFiles(files);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		u.setLastLog(dateFormat.format(date));
		u.setProfilePhoto("Ivan_Ukhov_Doha_2010.jpg");
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

	public List<File> getFilesList() {
		return filesList;
	}

	public void setFilesList(List<File> filesList) {
		this.filesList = filesList;
	}
}
