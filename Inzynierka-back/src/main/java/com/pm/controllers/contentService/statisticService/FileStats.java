package com.pm.controllers.contentService.statisticService;

import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by izabella on 21.10.16.
 */
@Controller
@RequestMapping("/rate")
public class FileStats {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ReadFromDatabase readClass;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SaveUpdateDatabase editClass;

    @RequestMapping(value = "/score", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addScore(@RequestBody ScoreRequest request) {
        System.out.println(request.getFileName());

        File file = readClass.searchOneFileByTitle(request.getFileName());
        if (file == null) return  ResponseEntity.status(HttpStatus.OK).body(null);
        System.out.println(file);
        int average = 0;
        List<Integer> scores = file.getScores();
        scores.add(Integer.valueOf(request.getScore()));
        int sum  = 0;
        for (int f: scores) sum = sum + f;
        sum = sum / scores.size();
        file.setAverage(Integer.toString(sum));
        return  ResponseEntity.status(HttpStatus.OK).body("ok");
    }


    @RequestMapping(value = "/comment", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addComment(@RequestBody CommentRequest request) {
        System.out.println(request.getFileName());

        File file = readClass.searchOneFileByTitle(request.getFileName());
        if (file == null) return  ResponseEntity.status(HttpStatus.OK).body(null);
        System.out.println(file);
        String comment = request.getComment();
        List<String> comments = file.getComments();
        comments.add(comment);
        file.setComments(comments);
        return  ResponseEntity.status(HttpStatus.OK).body("ok");
    }



}
