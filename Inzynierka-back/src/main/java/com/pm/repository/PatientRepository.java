package com.pm.repository;


import com.pm.model.Patient;
import com.pm.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by izabella on 23.04.16.
 */
public interface PatientRepository extends CrudRepository<Patient, String> {

    List<Patient> findByDescription(String description);
    Patient findOneByDescription(String description);

}
