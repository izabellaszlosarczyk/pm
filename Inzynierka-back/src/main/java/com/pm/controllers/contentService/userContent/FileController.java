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
    public ResponseEntity<File> getByName(@PathVariable String fileName) {
        System.out.println(fileName);

        File file = readClass.searchOneFileByTitle(fileName);
        if (file == null) return  ResponseEntity.status(HttpStatus.OK).body(null);
        System.out.println(file);
        return  ResponseEntity.status(HttpStatus.OK).body(file);
    }

}
