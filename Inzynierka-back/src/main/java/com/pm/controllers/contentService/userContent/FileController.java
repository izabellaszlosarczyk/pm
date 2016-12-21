package com.pm.controllers.contentService.userContent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.gridfs.GridFSDBFile;
import com.pm.SpringConfig.DataBaseConfig;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.File;
import com.pm.model.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

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
    public ResponseEntity<String> uploadFileHandlerOld(@ModelAttribute("nameData") String nameData, @ModelAttribute("fileData") MultipartFile fileData, @ModelAttribute("nameDesc") String nameDesc, @ModelAttribute("fileDesc") MultipartFile fileDesc, @ModelAttribute("type") String type) {
        System.out.println("ZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPISUJE");
        FileInputStream fis = null;
        ObjectMapper ob = new ObjectMapper();
        AnnotationConfigApplicationContext ctx = null;
        System.out.println("CZO TU SIE DZIEJE2");
        System.out.println(nameDesc);
        System.out.println(nameData);
        System.out.println("FILEFILEFILE2");
        System.out.println(fileDesc);
        System.out.println(fileData);
        String fileDataString = "data=";
        String fileDescString = "desc=";
        StringBuilder sb= new StringBuilder();
        ByteArrayInputStream stream = null;
        ByteArrayInputStream stream2 = null;
        try {
            stream = new ByteArrayInputStream(fileData.getBytes());
            stream2 = new   ByteArrayInputStream(fileDesc.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fileDataString += IOUtils.toString(stream, "UTF-8");
            fileDescString += IOUtils.toString(stream2, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] postDataFileDesc = new byte[0];
        byte[] postDataFileData = new byte[0];
        try {
            postDataFileDesc = fileDescString.getBytes("UTF-8");
            postDataFileData = fileDataString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URL url = null;
        try {
            url = new URL("http://127.0.0.1:8000/polls/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection httpCon = null;
        try {
            httpCon = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpCon.setDoOutput(true);
        try {
            httpCon.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        httpCon.setUseCaches(false);

        try {
            try (DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream())) {
                wr.write(postDataFileData);
                wr.write(postDataFileDesc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("getting response");
        int responseCode = 0;
        try {
            responseCode = httpCon.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String inputLine = "";
        try {
            //zczytywanie responsa
            while ((inputLine = in.readLine()) != null){
                System.out.println(inputLine);
                sb.append(inputLine);
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        try {
            System.out.println(httpCon.getResponseMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpCon.disconnect();

//        //check
//        if (!fileDesc.isEmpty()) {
//            try {
//                System.out.println("ZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPISUJE");
//                ctx = new AnnotationConfigApplicationContext(DataBaseConfig.class);
//                GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
//                java.io.File convFile = new java.io.File(file.getOriginalFilename());
//                convFile.createNewFile();
////	            FileOutputStream fos = new FileOutputStream(convFile);
////	            fos.write(file.getBytes());
////	            fos.close();
//                fis = new FileInputStream(convFile);
//                System.out.println(name);
//                gridOperations.store(fis, name, "profilePic/jpg");
////	            return convFile.toString();
//
//            } catch (Exception e) {
//                try {
//                    return  ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString("You failed to upload " + name + " => " + e.getMessage()));
//                } catch (JsonProcessingException e1) {
//                    e1.printStackTrace();
//                }
//            } finally {
//                if (fis != null)
//                    try {
//                        fis.close();
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//            }
//        }
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString("ok"));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//
        System.out.println("while if response is empty");
        inputLine = sb.toString();
        System.out.println(sb.toString());
//        while (inputLine.isEmpty()){
//            System.out.println(1);
//            try {
//                sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString(inputLine));
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        System.out.println("koniec funkcji");

        return ResponseEntity.status(HttpStatus.OK).body(inputLine);
}

    @RequestMapping(value = "/saveNew",  headers = "content-type=multipart/*", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> saveFileHandler(@ModelAttribute("name") String name, @ModelAttribute("file") MultipartFile file, @ModelAttribute("type") String type) {
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
                gridOperations.store(fis, name, type);
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
