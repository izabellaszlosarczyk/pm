package com.pm.controllers.contentService.userContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by izabella on 29.10.16.
 */
public class FilesRequest {
    private List<String> filesNames = new ArrayList<>();

    public List<String> getFilesNames() {
        return filesNames;
    }

    public void setFilesNames(List<String> filesNames) {
        this.filesNames = filesNames;
    }
}
