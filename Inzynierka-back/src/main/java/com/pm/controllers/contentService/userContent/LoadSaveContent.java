package com.pm.controllers.contentService.userContent;

/**
 * Created by izabella on 22.07.16.
 */
import java.io.*;
import java.io.File;

import com.mongodb.gridfs.GridFSDBFile;
import com.pm.SpringConfig.DataBaseConfig;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/content")
public class LoadSaveContent {



    @RequestMapping(value = "/load/{fileName:.+}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileName) {
        System.out.println(fileName);
        System.out.println("==========================================");
        GridFSDBFile gridFsFile = FileOperations.loadFileFromDatabase(fileName);
        System.out.println(gridFsFile);
        return ResponseEntity.ok().contentLength(gridFsFile.getLength()).contentType(MediaType.parseMediaType(gridFsFile.getContentType())).body(new InputStreamResource(gridFsFile.getInputStream()));
    }
    @RequestMapping(value = "/save/upload/image/",  headers = "content-type=multipart/*", method = RequestMethod.POST)
    public @ResponseBody String uploadFileHandler(@ModelAttribute("name") String name, @ModelAttribute("file") MultipartFile file) {
        FileInputStream fis = null;
        AnnotationConfigApplicationContext ctx = null;
        if (!file.isEmpty()) {
            try {
                ctx = new AnnotationConfigApplicationContext(DataBaseConfig.class);
                GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
                File convFile = new File(file.getOriginalFilename());
                convFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(convFile);
                fos.write(file.getBytes());
                fos.close();
                fis = new FileInputStream(convFile);
                gridOperations.store(fis, name.toString(), "profilePic/jpg");
                System.out.println(name);
                System.out.println(convFile);

            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            } finally {
                if (fis != null)
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        }
        return 	"dupa";
    }

}
