package com.pm.repository;


import com.pm.model.Patient;
import com.pm.model.Session;
import com.pm.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by izabella on 23.04.16.
 */
public interface SessionRepository extends CrudRepository<Session, String> {
    List<Session> findByPatient(Patient patient);

    Session findOneByPatient(Patient patient);

}
