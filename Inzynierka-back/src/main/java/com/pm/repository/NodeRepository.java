package com.pm.repository;


import com.pm.model.Node;
import com.pm.model.Patient;
import com.pm.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by izabella on 23.04.16.
 */
public interface NodeRepository extends CrudRepository<Node, String> {

    List<Node> findByLabelOfNode(String labelOfNode);
    Node findOneByLabelOfNode(String labelOfNode);
}
