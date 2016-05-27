package com.pm.repository;

import com.pm.model.Move;
import com.pm.model.Node;
import com.pm.model.Patient;
import com.pm.model.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by izabella on 27.05.16.
 */
public interface MoveRepository extends CrudRepository<Move, String> {
    List<Move> findByNode(Node node);

    Move findOneByNode(Node node);
}
