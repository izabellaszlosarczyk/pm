package com.pm.controllers.contentService.statisticService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        ObjectMapper ob = new ObjectMapper();
        System.out.println(request.getFile());
        System.out.println(request.getScore());
        File file = readClass.searchOneFileByTitle(request.getFile());
        if (file == null) return  ResponseEntity.status(HttpStatus.OK).body(null);
        System.out.println(file);
        List<Integer> scores = file.getScores();
        scores.add(Integer.valueOf(request.getScore()));
        float sum  = 0;

        for (int f: scores) sum = sum + f;
        System.out.println(sum);
        sum = sum / scores.size();
        System.out.println(scores.size());
        System.out.println(sum);

        file.setAverage(Float.toString(sum));
        editClass.saveFile(file);
        try {
            return  ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString("ok"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  ResponseEntity.status(HttpStatus.OK).body(null);

    }


    @RequestMapping(value = "/comment", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addComment(@RequestBody CommentRequest request) {
        ObjectMapper ob = new ObjectMapper();
        System.out.println("DODAJE KOMCIA YOYOYYOYOOYOYOYOY");
        System.out.println(request.getFile());
        System.out.println(request.getComment());

        File file = readClass.searchOneFileByTitle(request.getFile());
        if (file == null) return  ResponseEntity.status(HttpStatus.OK).body(null);
        System.out.println(file);
        String comment = request.getComment();
        file.addComment(comment);
        System.out.println("komcie");

        editClass.saveFile(file);
        try {
            return  ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString("ok"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  ResponseEntity.status(HttpStatus.OK).body(null);
    }



}
