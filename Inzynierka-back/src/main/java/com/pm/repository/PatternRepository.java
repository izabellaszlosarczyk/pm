package com.pm.repository;


import com.pm.model.Patient;
import com.pm.model.Pattern;
import com.pm.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by izabella on 23.04.16.
 */
public interface PatternRepository extends CrudRepository<Pattern, String> {
    List<Pattern> findByTitle(String title);
    Pattern findOneByTitle(String title);

    List<Pattern> findByBody(String body);
    Pattern findOneByBody(String body);

    List<Pattern> findByPatient(Patient patient);
    Pattern findOneByPatient(Patient patient);
}
