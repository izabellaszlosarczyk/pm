package com.pm.controllers.contentService.userContent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.gridfs.GridFSDBFile;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.File;
import com.pm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by izabella on 07.09.16.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ReadFromDatabase readClass;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SaveUpdateDatabase editClass;

    @RequestMapping(value = "/loadDetails/{fileName:.+}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getDetailsByName(@PathVariable String fileName) {
        System.out.println(fileName);
        ObjectMapper mapper = new ObjectMapper();
        File file = readClass.searchOneFileByTitle(fileName);
        if (file == null){
            System.out.println("DUPSON YOUOYOOUOTRY");
            return  ResponseEntity.status(HttpStatus.OK).body(null);
        }
        try {
            return  ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(file));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/loadDetails/files", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getDetailsByName(@RequestBody List<String> names) {
        System.out.println(names);
        ObjectMapper mapper = new ObjectMapper();
        List<File> filesDeatils = new ArrayList<>();
        for (String fileName: names) filesDeatils.add(readClass.searchOneFileByTitle(fileName));
        if (filesDeatils == null){
            System.out.println("DUPSON YOUOYOOUOTRY");
            return  ResponseEntity.status(HttpStatus.OK).body(null);
        }
        try {
            return  ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(filesDeatils));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/deleteFromSubs", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> deleteFileFromSubs(@RequestBody SubsRequest request) {
        System.out.println(request);
        User user = readClass.searchOneByEmail(request.getUserEmail());
        List<String> files = user.getSavedFiles();
        if (user == null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        for (String f : files){
            if (f.equals(request.getFileName())){
                files.remove(f);
            }
        }
        user.setSavedFiles(files);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @RequestMapping(value = "/addSubs", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addFileFromSubs(@RequestBody SubsRequest request) {
        System.out.println(request);
        User user = readClass.searchOneByEmail(request.getUserEmail());
        List<String> files = user.getSavedFiles();
        if (user == null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        files.add(request.getFileName());
        user.setSavedFiles(files);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @RequestMapping(value = "/subs/{email:.+}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GridFSDBFile>> userSubs(@PathVariable String email) {
        User user = readClass.searchOneByEmail(email);
        List<String> files = user.getSubscribedFiles();
        if (user == null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        List<GridFSDBFile> userFilesSubs = new ArrayList<>();
        for (String f: files){
            userFilesSubs.add(FileOperations.loadFileFromDatabase(f));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userFilesSubs);
    }

}
