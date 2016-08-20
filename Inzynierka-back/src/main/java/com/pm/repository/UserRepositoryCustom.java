package com.pm.repository;

import com.pm.model.User;
import org.bson.types.ObjectId;

/**
 * Created by izabella on 15.08.16.
 */
public interface UserRepositoryCustom {
    User findByID(String id);
    User findByID(ObjectId id);
    User findByEmailAndPassword(String email, String password);
}
