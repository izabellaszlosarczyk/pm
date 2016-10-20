package com.pm.repository;

import com.pm.model.File;
import com.pm.model.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 * Created by izabella on 09.09.16.
 */
public class FileRepostioryImpl implements FileRepositoryCustom{
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public File findFileByID(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,  File.class);
    }

    @Override
    public File findFileByID(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        return mongoTemplate.findOne(query,  File.class);
    }
}
