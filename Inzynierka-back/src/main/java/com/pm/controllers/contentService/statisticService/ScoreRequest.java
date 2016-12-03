package com.pm.controllers.contentService.statisticService;

/**
 * Created by izabella on 21.10.16.
 */
public class ScoreRequest {
    private String file;
    private String score;


    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
