package com.pm.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by izabella on 22.07.16.
 */
@Document(collection = "File")
public class File {

    private String type;
    private MultipartFile file;

    public String getType() {
        return type;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setType(String type) {
        this.type = type;
    }
}
