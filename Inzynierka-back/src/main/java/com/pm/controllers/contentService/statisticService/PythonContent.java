package com.pm.controllers.contentService.statisticService;

import com.mongodb.gridfs.GridFSDBFile;
import com.pm.database.ReadFromDatabase;
import com.pm.database.SaveUpdateDatabase;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.util.PythonInterpreter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.batch.core.step.tasklet.SystemCommandTasklet;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.util.List;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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


    //metoda2: z pythonowego backu request z plikiem wysyłamy na front-end

@RequestMapping("/send")
 public static void sendPost() throws IOException{


 String FILENAME = "//home//zuchens//Desktop//pm//analytic_module//input_data//data.csv";
 String file = "param1=";

 	try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
			file = file.concat(sCurrentLine+"\n");
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}



 String urlParameters  = file;
byte[] postData       = urlParameters.getBytes("UTF-8" );
int    postDataLength = postData.length;
  URL url = new URL("http://127.0.0.1:8000/polls/");
  HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
  httpCon.setDoOutput(true);
  httpCon.setRequestMethod("POST");
  //OutputStreamWriter out = new OutputStreamWriter(
  //    httpCon.getOutputStream());

  httpCon.setUseCaches( false );

  try( DataOutputStream wr = new DataOutputStream( httpCon.getOutputStream())) {
   wr.write( postData );
}
  System.out.println(httpCon.getResponseCode());
  System.out.println(httpCon.getResponseMessage());
 // out.close();
 }


        //@Autowired
    //    private ResourceLoader resourceLoader;
//    @RequestMapping(value = "/a", method= RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<String> a(){
//        System.out.println("dupa");
//
//        PythonInterpreter pi = new PythonInterpreter();
//        pi.exec("print(1)");
//        String scriptname = "file.py";
//        Resource resource = resourceLoader.getResource("classpath:/static/"+scriptname);
//        try {
//            InputStream in = resource.getInputStream();
//            pi.execfile(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        pi.exec("from pymodule import square");
//        pi.set("integer", new PyInteger(42));
//        pi.exec("result = square(integer)");
//        pi.exec("print(result)");
//        PyInteger result = (PyInteger)pi.get("result");
//        System.out.println("result: "+ result.asInt());
//        PyFunction pf = (PyFunction)pi.get("square");
//        System.out.println(pf.__call__(new PyInteger(5)));
//        return ResponseEntity.status(HttpStatus.OK).body("0");
//    }



}

