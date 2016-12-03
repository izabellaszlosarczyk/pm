package com.pm.controllers.contentService.statisticService;

/**
 * Created by izabella on 21.10.16.
 */
public class CommentRequest {
    private String file;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
