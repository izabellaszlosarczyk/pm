package com.pm.repository;

import com.pm.model.File;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by izabella on 09.09.16.
 */
public interface FileRepository extends CrudRepository<File, String> {
    File findOneByTitle(String tile);
    List<File> findByType(String type);
    List<File> findByScores(List<String> scores);
    List<File> findByComments(List<String> comments);
    List<File> findByAverage(String average);
}
