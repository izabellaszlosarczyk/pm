package com.pm.controllers.contentService.userContent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.gridfs.GridFSDBFile;
import com.pm.SpringConfig.DataBaseConfig;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.File;
import com.pm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
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

        //TODO: NULLL
        User user = readClass.searchOneByEmail(request.getUserEmail());
        List<String> files = user.getSavedFiles();
        if (user == null) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        for (String f : files){
            if (f.equals(request.getFileName())){
                files.remove(f);
            }
        }
        user.setSavedFiles(files);
        return ResponseEntity.status(HttpStatus.OK).body("{\"value\":\"OK\"}");
    }

    @RequestMapping(value = "/addSubs", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addFileToSubs(@RequestBody SubsRequest request) {
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

//    @RequestMapping(value = "/uploadNew",  headers = "content-type=multipart/*", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<String> uploadFileHandler(@RequestBody MultipartFileRequest request) {
//        System.out.println("ZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPISUJE");
//        FileInputStream fis = null;
//        ObjectMapper ob = new ObjectMapper();
//        AnnotationConfigApplicationContext ctx = null;
//        System.out.println(request.getFile());
//        System.out.println(request.getName());
//        //check
////        if (!request.getFile().isEmpty()) {
////            try {
////                ctx = new AnnotationConfigApplicationContext(DataBaseConfig.class);
////                GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
////                java.io.File convFile = new java.io.File(request.getFile().getOriginalFilename());
////                convFile.createNewFile();
//////	            FileOutputStream fos = new FileOutputStream(convFile);
//////	            fos.write(file.getBytes());
//////	            fos.close();
////                fis = new FileInputStream(convFile);
////                System.out.println(request.getFileName());
////                gridOperations.store(fis, request.getFileName(), "profilePic/jpg");
//////	            return convFile.toString();
////
////            } catch (Exception e) {
////                try {
////                    return  ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString("You failed to upload " + name + " => " + e.getMessage()));
////                } catch (JsonProcessingException e1) {
////                    e1.printStackTrace();
////                }
////            } finally {
////                if (fis != null)
////                    try {
////                        fis.close();
////                    } catch (IOException e) {
////                        // TODO Auto-generated catch block
////                        e.printStackTrace();
////                    }
////            }
////        }
////        try {
////            return ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString("ok"));
////        } catch (JsonProcessingException e) {
////            e.printStackTrace();
////        }
//
//        return null;
//    }

    @RequestMapping(value = "/uploadNew",  headers = "content-type=multipart/*", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> uploadFileHandler(@ModelAttribute("name") String name, @ModelAttribute("file") MultipartFile file) {
        System.out.println("ZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPISUJE");
        FileInputStream fis = null;
        ObjectMapper ob = new ObjectMapper();
        AnnotationConfigApplicationContext ctx = null;
        System.out.println("CZO TU SIE DZIEJE");
        System.out.println(name);
        System.out.println("FILEFILEFILE");
        System.out.println(file);
        //check
        if (!file.isEmpty()) {
            try {
                System.out.println("ZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPISUJE");
                ctx = new AnnotationConfigApplicationContext(DataBaseConfig.class);
                GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
                java.io.File convFile = new java.io.File(file.getOriginalFilename());
                convFile.createNewFile();
//	            FileOutputStream fos = new FileOutputStream(convFile);
//	            fos.write(file.getBytes());
//	            fos.close();
                fis = new FileInputStream(convFile);
                System.out.println(name);
                gridOperations.store(fis, name, "profilePic/jpg");
//	            return convFile.toString();

            } catch (Exception e) {
                try {
                    return  ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString("You failed to upload " + name + " => " + e.getMessage()));
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                }
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
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString("ok"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    //TODO: metoda do testow
    @RequestMapping(value = "/addFile", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> addFile(@RequestBody FileRequest fileRequest) {
        File file = new File();
        file.setCreationDate(LocalDate.now());
        file.setAverage("0");
        List<String> c = new ArrayList<>();
        file.setComments(c);
        file.setTitle("Ivan_Ukhov_Doha_2010.jpg");
        List<Integer> s = new ArrayList<>();
        s.add(5);
        file.setScores(s);
        file.setType(fileRequest.getType());

        return ResponseEntity.status(HttpStatus.OK).body("null");

    }

    @RequestMapping(value = "/addFileDetails", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> addFileDescription(@RequestBody FileRequest fileRequest) throws JsonProcessingException {
        if (fileRequest == null)return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        File file = new File();
        ObjectMapper ob = new ObjectMapper();
        file.setCreationDate(LocalDate.now());
        file.setAverage("0");
        List<String> c = new ArrayList<>();
        file.setComments(c);
        file.setTitle(fileRequest.getTitle());
        List<Integer> s = new ArrayList<>();
        s.add(5);
        file.setScores(s);
        file.setType(fileRequest.getType());

        return ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString("ok"));

    }

}
