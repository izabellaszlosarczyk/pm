package com.pm.controllers.contentService.userContent;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by izabella on 19.11.16.
 */
public class MultipartFileRequest {

    private String name;
    private MultipartFile file;

    public String getName() {
        return name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
