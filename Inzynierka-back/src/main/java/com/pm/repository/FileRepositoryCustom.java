package com.pm.repository;

import com.pm.model.File;
import org.bson.types.ObjectId;

/**
 * Created by izabella on 09.09.16.
 */
public interface FileRepositoryCustom {
    File findFileByID(String id);
    File findFileByID(ObjectId id);
}

