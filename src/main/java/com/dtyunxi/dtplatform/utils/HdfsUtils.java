package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.authentication.util.RandomSignerSecretProvider;
import org.bson.Document;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsUtils {

    public static FileSystem getFileSystem(Config config){

        Document server = config.getServer();
        Document document = (Document) server.get("hdfs");
        Configuration conf= new Configuration();
        conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        FileSystem fileSystem=null;
        try {
            fileSystem = FileSystem.get(new URI(document.getString("uri")), conf, document.getString("user"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return fileSystem;
    }


    public static void writeMess(FileSystem fileSystem,String log,String remoteFile){
        FSDataOutputStream fsDataOutputStream=null;
        FSDataOutputStream createOutputStream=null;
        try {
            Path path = new Path(remoteFile);
            if (!fileSystem.exists(path)){
                createOutputStream = fileSystem.create(path);
                createOutputStream.close();
            }
            fsDataOutputStream = fileSystem.append(path);
            fsDataOutputStream.write((log+"\r\n").getBytes(),0,(log+"\r\n").getBytes().length);
            fsDataOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fsDataOutputStream!=null){
                try {
                    fsDataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
