package com.pm.controllers.contentService.statisticService;

/**
 * Created by izabella on 21.10.16.
 */
public class CommentRequest {
    private String fileName;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
