package com.pm.repository;

import com.pm.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by izabella on 15.08.16.
 */
public class UserRepositoryImpl implements  UserRepositoryCustom{


    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public User findByID(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,  User.class);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("email").is(email),Criteria.where("password").is(password));
        Query query = new Query(criteria);

        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User findByID(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return mongoTemplate.findOne(query,  User.class);
    }

}
