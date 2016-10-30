package com.pm.controllers.databaseControllers;

import com.pm.controllers.contentService.statisticService.CommentRequest;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by izabella on 21.10.16.
 */

@Controller
@RequestMapping("/test")
public class TestController {
    private static final Logger logger = LogManager.getLogger(TestController.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ReadFromDatabase readClass;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SaveUpdateDatabase editClass;


    @RequestMapping(value = "/test2", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> addScore2() {
        String fileName = "Ivan_Ukhov_Doha_2010.jpg";
        int score = 10;
        File file = readClass.searchOneFileByTitle(fileName);
        if (file == null) return  ResponseEntity.status(HttpStatus.OK).body(null);
        System.out.println(file.getAverage());
        int average = 0;
        List<Integer> scores = file.getScores();
        scores.add(score);
        int sum  = 0;
        for (int f: scores) sum = sum + f;
        sum = sum / scores.size();
        file.setAverage(Integer.toString(sum));
        return  ResponseEntity.status(HttpStatus.OK).body(file.getAverage());
    }


    @RequestMapping(value = "/test3", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<File> addComment() {
        String fileName = "Ivan_Ukhov_Doha_2010.jpg";
        String comment = "Dupa test";
        File file = readClass.searchOneFileByTitle(fileName);
        if (file == null) return  ResponseEntity.status(HttpStatus.OK).body(null);
        System.out.println(file);
        List<String> comments = file.getComments();
        comments.add(comment);
        file.setComments(comments);
        return  ResponseEntity.status(HttpStatus.OK).body(file);
    }

}
