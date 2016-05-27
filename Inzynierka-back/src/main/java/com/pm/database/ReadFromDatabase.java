package com.pm.database;

import com.pm.model.*;
import com.pm.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by izabella on 19.05.16.
 */
@Service
public class ReadFromDatabase {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatternRepository patternRepository;

    @Autowired
    SessionRepository sessionRepository;

    //session
    public Iterable<Session> searchAllSessions(){return sessionRepository.findAll();}
    //moves

    //pattern
    public Iterable<Pattern> searchAllPatterns(){return patternRepository.findAll();}
    public Pattern searchByTitle(String title){return  patternRepository.findOneByTitle(title);}
    public List<Pattern> searchAllByTitle(String title){return patternRepository.findByTitle(title);}
    public Pattern searchByBody(String body){ return  patternRepository.findOneByBody(body);}
    public List<Pattern> searchAllByBody(String body){ return patternRepository.findByBody(body);}
    public Pattern searchByPatient(Patient patient){return  patternRepository.findOneByPatient(patient);}
    public List<Pattern> searchAllByPatient(Patient patient){return  patternRepository.findByPatient(patient);}
    //patient
    public Iterable<Patient> searchAllPatients() {return patientRepository.findAll();}
    public Patient searchByDescription(String des){return patientRepository.findOneByDescription(des);}
    public List<Patient> searchAllByDescription(String des){ return patientRepository.findByDescription(des);}
    //node
    public Iterable<Node> searchAllNodes() {return  nodeRepository.findAll();}
    public Node searchOneByLabel(String label) {return  nodeRepository.findOneByLabelOfNode(label);}
    public List<Node> searchByLabel(String label) {return nodeRepository.findByLabelOfNode(label);}
    //user
    public Iterable<User> searchAllUsers(){
        return userRepository.findAll();
    }

    public List<User> searchAllByLastName(String lastName){ return userRepository.findByLastName(lastName);}
    public User searchOneByLastName(String lastName){ return userRepository.findOneByLastName(lastName);}

    public List<User> searchAllByFirstName(String firstName){ return userRepository.findByFirstName(firstName);}
    public User searchOneByFirstName(String firstName){ return userRepository.findOneByFirstName(firstName);}

    public User searchOneByLogin(String login){ return userRepository.findOneByLogin(login);}
    public User searchOneByEmail(String email){ return userRepository.findOneByEmail(email);}
}
