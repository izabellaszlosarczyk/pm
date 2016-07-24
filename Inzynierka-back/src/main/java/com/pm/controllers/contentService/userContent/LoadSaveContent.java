package com.pm.controllers.contentService.userContent;

/**
 * Created by izabella on 22.07.16.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.pm.SpringConfig.DataBaseConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/content")
public class LoadSaveContent {

    @RequestMapping(value = "/saveProfilePhoto", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> savePP(@RequestParam("file") MultipartFile multipartFile){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(DataBaseConfig.class);
        GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
        String response = "";
        DBObject metaData = new BasicDBObject();
        metaData.put("extra1", "anything 1");
        metaData.put("extra2", "anything 2");

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/home/izabella/Pulpit/12.jpg");
            gridOperations.store(inputStream, "12.png", "profilePic/jpg", metaData);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @RequestMapping(value = "/loadProfilePhoto", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> loadPP(@RequestParam("file") MultipartFile multipartFile){
        String response = "";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @RequestMapping(value = "/loadContent", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> loadContent(@RequestParam("file") MultipartFile multipartFile){
        String response = "";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
