package com.pm.controllers.contentService.statisticService;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

    @RequestMapping(value = "/news", method= RequestMethod.POST)
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

    @RequestMapping(value = "/all", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GridFSDBFile>> allContent() {
        List<File> fileNames = readClass.searchAllFiles();
        List<String> tmp = new ArrayList<>();
        for (File f: fileNames){ tmp.add(f.getTitle());}
        List<GridFSDBFile> files = new ArrayList<>();
        for (String t : tmp) { files.add(FileOperations.loadFileFromDatabase(t)); }
        return ResponseEntity.status(HttpStatus.OK).body(files);
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

    //TODO: wiele plikow zwracamy, zmienic to
    @RequestMapping(value = "/news/{userEmail:.+}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GridFSDBFile>> newsForUser(@PathVariable String email) {
        User user = readClass.searchOneByEmail(email);
        String lastLog = user.getLastLog();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        formatter = formatter.withLocale(Locale.ENGLISH);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
        LocalDate dateOfLog = LocalDate.parse(lastLog, formatter);
        List<File> fileNames = readClass.searchAllFiles();
        List<String> tmp = new ArrayList<>();
        for (File f: fileNames){
            if (f.getCreationDate().isAfter(dateOfLog)){
                tmp.add(f.getTitle());
            }
        }
        List<GridFSDBFile> files = new ArrayList<>();
        for (String t : tmp) { files.add(FileOperations.loadFileFromDatabase(t)); }
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

}
