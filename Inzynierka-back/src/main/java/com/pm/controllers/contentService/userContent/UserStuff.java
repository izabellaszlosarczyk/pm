package com.pm.controllers.contentService.userContent;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by izabella on 22.10.16.
 */
@Controller
@RequestMapping("/userStuff")
public class UserStuff {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ReadFromDatabase readClass;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SaveUpdateDatabase editClass;

    @RequestMapping(value = "/getSubsFileByType", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GridFSDBFile>> getUserSubsType(@RequestBody UserTypeRequest request) {
        User user = readClass.searchOneByEmail(request.getEmail());

        //ktore tutaj?
        List<String> userFiles = user.getSubscribedFiles();

        List<File> files = new ArrayList<>();
        List<GridFSDBFile> filesTmp = new ArrayList<>();
        for (String f2: userFiles){
            File file = readClass.searchOneFileByTitle(f2);
            if (file.getType().equals(request.getType())){
                files.add(file);
                filesTmp.add(FileOperations.loadFileFromDatabase(file.getTitle()));
            }
        }
        return  ResponseEntity.status(HttpStatus.OK).body(filesTmp);
    }

    @RequestMapping(value = "/getSavFileByType", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GridFSDBFile>> getUserSavedType(@RequestBody UserTypeRequest request) {
        User user = readClass.searchOneByEmail(request.getEmail());

        //ktore tutaj?
        List<String> userFiles = user.getSavedFiles();

        List<File> files = new ArrayList<>();
        List<GridFSDBFile> filesTmp = new ArrayList<>();
        for (String f2: userFiles){
            File file = readClass.searchOneFileByTitle(f2);
            if (file.getType().equals(request.getType())){
                files.add(file);
                filesTmp.add(FileOperations.loadFileFromDatabase(file.getTitle()));
            }
        }
        return  ResponseEntity.status(HttpStatus.OK).body(filesTmp);
    }


}
