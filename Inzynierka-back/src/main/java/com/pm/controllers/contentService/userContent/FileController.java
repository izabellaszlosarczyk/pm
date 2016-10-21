package com.pm.controllers.contentService.userContent;

import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.File;
import com.pm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<File> getDetailsByName(@PathVariable String fileName) {
        System.out.println(fileName);

        File file = readClass.searchOneFileByTitle(fileName);
        if (file == null) return  ResponseEntity.status(HttpStatus.OK).body(null);
        System.out.println(file);
        return  ResponseEntity.status(HttpStatus.OK).body(file);
    }

    @RequestMapping(value = "/deleteFromSubs", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> deleteFile(@RequestBody SubsRequest request) {
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
    public ResponseEntity<String> addFile(@RequestBody SubsRequest request) {
        System.out.println(request);
        User user = readClass.searchOneByEmail(request.getUserEmail());
        List<String> files = user.getSavedFiles();
        if (user == null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        files.add(request.getFileName());
        user.setSavedFiles(files);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

}
