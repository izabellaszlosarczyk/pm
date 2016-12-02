package com.pm.controllers.contentService.statisticService;

import com.mongodb.gridfs.GridFSDBFile;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;

import java.util.List;

/**
 * Created by izabella on 23.11.16.
 */
@Controller
@RequestMapping("/python")
public class PythonContent {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    ReadFromDatabase readClass;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    SaveUpdateDatabase editClass;

    @RequestMapping(value = "/a", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> a(){
        System.out.println("dupa");

        PythonInterpreter pi = new PythonInterpreter();
        pi.exec("print(1)");
        String scriptname = "file.py";
        pi.execfile(scriptname);
//        pi.exec("from pymodule import square");
//        pi.set("integer", new PyInteger(42));
//        pi.exec("result = square(integer)");
//        pi.exec("print(result)");
//        PyInteger result = (PyInteger)pi.get("result");
//        System.out.println("result: "+ result.asInt());
//        PyFunction pf = (PyFunction)pi.get("square");
//        System.out.println(pf.__call__(new PyInteger(5)));
        return ResponseEntity.status(HttpStatus.OK).body("0");
    }

}

