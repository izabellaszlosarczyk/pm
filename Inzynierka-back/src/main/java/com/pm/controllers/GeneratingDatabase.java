package com.pm.controllers;

import com.pm.model.*;
import com.pm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/database")
public class GeneratingDatabase {

    @Autowired
    NodeRepository nodeRepository;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PatternRepository patternRepository;
    @Autowired
    MoveRepository moveRepository;

    private List<User> usersList = new ArrayList<User>();
    private List<Pattern> patternsList = new ArrayList<Pattern>();
    private List<Patient> patientsList = new ArrayList<Patient>();
    private List<Session> sessionsList = new ArrayList<Session>();
    private List<Node> nodesList = new ArrayList<Node>();
    private List<Move> movesList = new ArrayList<Move>();

    public void generateMockData() {
        DataGenerator d = new DataGenerator();
        d.generate();
        this.usersList.addAll(d.getUsersList());
        this.patientsList.addAll(d.getPatientsList());
        this.patternsList.addAll(d.getPatternsList());
        this.sessionsList.addAll(d.getSessionsList());
        this.nodesList.addAll(d.getNodesList());
        this.movesList.addAll(d.getMovesList());
    }

    public void generateDatabaseCollections() {

        generateMockData();

        Random r = new Random();
        int tmp;
        //dokument dla studenta
        for (int i = 0; i < usersList.size(); i = i + 1) {
            usersList.get(i).setEmail(usersList.get(i).getEmail());
            usersList.get(i).setLogin(usersList.get(i).getLogin());
            usersList.get(i).setFirstName(usersList.get(i).getFirstName());
            usersList.get(i).setLastName(usersList.get(i).getLastName());
            usersList.get(i).setPassword(usersList.get(i).getPassword());
            userRepository.save(usersList.get(i));
        }
        //dokument dla node'a
        for (int i = 0; i < nodesList.size(); i = i + 1){
            nodesList.get(i).setLabelOfNode(nodesList.get(i).getLabelOfNode());
            nodeRepository.save(nodesList.get(i));
        }
        for (int i = 0; i < movesList.size(); i = i + 1){
            tmp = Math.abs(r.nextInt())+1;
            movesList.get(i).setNode(nodesList.get(tmp%nodesList.size()));
            if (tmp %2 == 0)
                movesList.get(i).setWasIn(true);
            else
                movesList.get(i).setWasIn(false);
            moveRepository.save(movesList.get(i));
        }
        //dokument dla pacjenta
        for (int i = 0; i < patientsList.size(); i = i + 1){
            patientsList.get(i).setDescription(patientsList.get(i).getDescription());
            patientRepository.save(patientsList.get(i));
        }
        //dokument dla sesji
        for (int i = 0; i < sessionsList.size(); i = i + 1){
            tmp = Math.abs(r.nextInt())+1;
            sessionsList.get(i).setPatient(patientsList.get(tmp%patientsList.size()));
            for (int k = 0; k < movesList.size(); k = k + 1) {
               sessionsList.get(i).addMove(movesList.get(k));
            }
            sessionRepository.save(sessionsList.get(i));
        }
        //dokument dla patternu
        for (int i = 0; i< patternsList.size(); i = i + 1){
            tmp = Math.abs(r.nextInt())+1;
            patternsList.get(i).setBody(patternsList.get(i).getBody());
            patternsList.get(i).setTitle(patternsList.get(i).getTitle());
            patternsList.get(i).setPatient(patientsList.get(tmp%patientsList.size()));
            patternRepository.save(patternsList.get(i));
        }

    }


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object getHomePage() {

        generateDatabaseCollections();

        return 0;
    }
}
