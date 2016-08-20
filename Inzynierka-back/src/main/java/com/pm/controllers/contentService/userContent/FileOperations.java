package com.pm.controllers.contentService.userContent;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.pm.SpringConfig.DataBaseConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by izabella on 09.08.16.
 */
public class FileOperations {

    @SuppressWarnings("resource")
    public static GridFSDBFile loadFileFromDatabase(String filename){
        AnnotationConfigApplicationContext ctx2 = null;
        GridFSDBFile result= null;
        try {
            ctx2 = new AnnotationConfigApplicationContext(DataBaseConfig.class);
            //ctx2 = new AnnotationConfigApplicationContext(DatabaseConfig.class);
            GridFsOperations gridOperations2 = (GridFsOperations) ctx2.getBean("gridFsTemplate");
            result = gridOperations2.findOne(new Query().addCriteria(Criteria.where("filename").is(filename)));
        } finally {
            ctx2.destroy();
        }
        return result;
    }

    @SuppressWarnings("resource")
    public static GridFSDBFile loadFileFromDatabaseByID(String id){
        AnnotationConfigApplicationContext ctx2 = null;
        GridFSDBFile result = null;
        try {
            ctx2 = new AnnotationConfigApplicationContext(DataBaseConfig.class);
            //ctx2 = new AnnotationConfigApplicationContext(DatabaseConfig.class);
            GridFsOperations gridOperations2 = (GridFsOperations) ctx2.getBean("gridFsTemplate");
            result =  gridOperations2.findOne(Query.query(Criteria.where("_id").is(id)));
        }finally {
            ctx2.destroy();
        }
        return result;
    }

    @SuppressWarnings("resource")
    public static List<GridFSDBFile> loadAllFilesFromDatabaseByID(String id){
        AnnotationConfigApplicationContext ctx2 = null;
        List<GridFSDBFile> result = new ArrayList<>();
        try {
            ctx2 = new AnnotationConfigApplicationContext(DataBaseConfig.class);
            //ctx2 = new AnnotationConfigApplicationContext(DatabaseConfig.class);
            GridFsOperations gridOperations2 = (GridFsOperations) ctx2.getBean("gridFsTemplate");
            result.addAll(gridOperations2.find(Query.query(Criteria.where("_id").is(id))));
            /*for (GridFSDBFile file : result) {
            	try {
            		System.out.println(file.getFilename());
            		System.out.println(file.getContentType());
     				file.writeTo("C://Users//Comarch//Desktop//"+i);
     				i = i + 1;
            	} catch (IOException e) {
            		e.printStackTrace();
            	}
            }*/
        }finally {
            ctx2.destroy();
        }
        return result;
    }

    @SuppressWarnings("resource")
    public static void saveFileToDatabase(String url, String filename) throws MalformedURLException, IOException {
        AnnotationConfigApplicationContext ctx = null;
        InputStream inputStream = null;
        try {
            ctx = new AnnotationConfigApplicationContext(DataBaseConfig.class);
            //ctx = new AnnotationConfigApplicationContext(DatabaseConfig.class);
            GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
            DBObject metaData = new BasicDBObject();
            //metaData.put("extra13232", "anything 1");
            //metaData.put("extra22323", "anything 2");
            final InputStream is = new URL(url + filename).openStream();
            //inputStream = new FileInputStream("C://Users//Comarch//Desktop//ble.png");
            gridOperations.store(is, filename, "profilePic/jpg", metaData);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    ctx.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("resource")
    public static void saveFileToDatabaseWithCustomOptions(String url, String filename, String[] info) throws MalformedURLException, IOException{
        AnnotationConfigApplicationContext ctx = null;
        InputStream inputStream = null;
        try {
            ctx = new AnnotationConfigApplicationContext(DataBaseConfig.class);
            //ctx = new AnnotationConfigApplicationContext(DatabaseConfig.class);
            GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
            DBObject metaData = new BasicDBObject();
            int i = 0;
            while (i < info.length){
                metaData.put(info[i], info[i+1]);
                i = i + 2;
            }
            final InputStream is = new URL(url + filename).openStream();
            //inputStream = new FileInputStream("C://Users//Comarch//Desktop//ble.png");
            gridOperations.store(is, filename, "profilePic/jpg", metaData);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    ctx.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
