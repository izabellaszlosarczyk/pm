package com.pm.controllers.contentService.statisticService;

/**
 * Created by izabella on 21.10.16.
 */
public class ScoreRequest {
    private String fileName;
    private int score;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
