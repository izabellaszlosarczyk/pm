package com.pm.controllers.contentService.statisticService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.gridfs.GridFSDBFile;
import com.pm.controllers.contentService.userContent.FileOperations;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import com.pm.model.File;
import com.pm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by izabella on 21.10.16.
 */
@Controller
@RequestMapping("/mainContent")
public class AppContent {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ReadFromDatabase readClass;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SaveUpdateDatabase editClass;

    @RequestMapping(value = "/newsContent/", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<GridFSDBFile>> newContent(@RequestBody int numberOfDaysFromToday) {
        LocalDate currentDate = LocalDate.now();

        List<File> fileNames = readClass.searchAllFiles();
        List<String> tmp = new ArrayList<>();
        for (File f: fileNames){
            int numberOFdays = (f.getCreationDate()).until(currentDate).getDays();
            if (numberOFdays < numberOfDaysFromToday){
                tmp.add(f.getTitle());
            }
        }
        List<GridFSDBFile> files = new ArrayList<>();
        for (String t : tmp) { files.add(FileOperations.loadFileFromDatabase(t)); }
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @RequestMapping(value = "/newsDetails", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> newContentDetails(@RequestBody int numberOfDaysFromToday) {
        LocalDate currentDate = LocalDate.now();
        ObjectMapper mapper = new ObjectMapper();
        List<File> files = new ArrayList<>();
        List<File> fileNames = readClass.searchAllFiles();
        List<String> tmp = new ArrayList<>();
        for (File f: fileNames){
            int numberOFdays = (f.getCreationDate()).until(currentDate).getDays();
            if (numberOFdays < numberOfDaysFromToday){
                files.add(readClass.searchOneFileByTitle(f.getTitle()));
            }
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(files));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


    @RequestMapping(value = "/allFiles", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GridFSDBFile>> allContent() {
        List<File> fileNames = readClass.searchAllFiles();
        List<String> tmp = new ArrayList<>();
        for (File f: fileNames){ tmp.add(f.getTitle());}
        List<GridFSDBFile> files = new ArrayList<>();
        for (String t : tmp) { files.add(FileOperations.loadFileFromDatabase(t)); }
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
    @RequestMapping(value = "/allDetails", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> allDetails() {
        System.out.println("wszedlem -all");
        List<File> fileNames = readClass.searchAllFiles();
        ObjectMapper ob = new ObjectMapper();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString(fileNames));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    //TODO: wiele plikow zwracamy, zmienic to
    @RequestMapping(value = "/all/{fileType:.+}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GridFSDBFile>> allContentByType(@PathVariable String fileType) {
        List<File> fileNames = readClass.searchAllByType(fileType);
        List<String> tmp = new ArrayList<>();
        for (File f: fileNames){ tmp.add(f.getTitle());}
        List<GridFSDBFile> files = new ArrayList<>();
        for (String t : tmp) { files.add(FileOperations.loadFileFromDatabase(t)); }
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @RequestMapping(value = "/news", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> newsForUser(@RequestBody String email) {
        System.out.println("wyszukuję dla emaila:"+email);
        User user = readClass.searchOneByEmail(email);
        String lastLog = user.getLastLog();
        System.out.println(lastLog);
//        LocalDate.parse()
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
//        formatter = formatter.withLocale(Locale.ENGLISH);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
//        LocalDate dateOfLog = LocalDate.parse(lastLog, formatter);
        String year = lastLog.substring(0, 4);
        String month = lastLog.substring(5, 7);
        String day = lastLog.substring(8, 10);
        LocalDate dateOfLog = LocalDate.of(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));

        System.out.println(dateOfLog);

        List<File> fileNames = readClass.searchAllFiles();
        System.out.println(fileNames.size());
        List<File> tmp = new ArrayList<>();
        for (File f: fileNames){
            if (f.getCreationDate() != null){
                System.out.println("plik:" + f.getCreationDate());
                if (f.getCreationDate().isBefore(dateOfLog)){
                    tmp.add(f);
                }else {
                    System.out.println("jednak po");
                }
            }else{
                System.out.println(f.getTitle());
            }
        }
        System.out.println(tmp.size());
        ObjectMapper ob = new ObjectMapper();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ob.writeValueAsString(tmp));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/newsDetails/{userEmail:.+}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> newsDetailsForUser(@PathVariable String email) {
        User user = readClass.searchOneByEmail(email);
        System.out.println(user);
        System.out.println("DUPPUPUUPUPUPPUPUPTPRTPRPTRP");
        ObjectMapper mapper = new ObjectMapper();
        String lastLog = user.getLastLog();
        System.out.println(lastLog);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale(Locale.ENGLISH);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate dateOfLog = LocalDate.parse(lastLog, formatter);
        System.out.println(dateOfLog);
        List<File> fileNames = readClass.searchAllFiles();
        List<File> tmp = new ArrayList<>();
        for (File f: fileNames) {
            if (f.getCreationDate().isAfter(dateOfLog)) {
                tmp.add(f);
            }
        }
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(tmp));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/randomFile", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getRandom() {
        List<File> filesDetails = new ArrayList<>();
        filesDetails.addAll(readClass.searchAllFiles());
        Random rand = new Random();

        int  n = rand.nextInt();
        n = Math.abs(n%filesDetails.size());
        File randomFile = filesDetails.get(n);
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("co tu sie dzieje");
        System.out.println(randomFile.getTitle());
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(randomFile));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/randomName", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getRandomFile() {
        List<File> filesDetails = new ArrayList<>();
        filesDetails.addAll(readClass.searchAllFiles());
        Random rand = new Random();
        System.out.println(filesDetails.size());
        ObjectMapper mapper = new ObjectMapper();

        int  n = rand.nextInt();

        n = Math.abs(n%filesDetails.size());
        System.out.println(n);
        String randomFileName = filesDetails.get(n).getTitle();
        //TODO: USUNAC TO I DODAC TYP IMG
        randomFileName = "Ivan_Ukhov_Doha_2010.jpg";
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(randomFileName));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
