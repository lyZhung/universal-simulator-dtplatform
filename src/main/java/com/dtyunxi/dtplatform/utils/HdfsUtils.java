package com.dtyunxi.dtplatform.utils;

import com.dtyunxi.dtplatform.model.Config;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.authentication.util.RandomSignerSecretProvider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsUtils {

    public static FileSystem getFileSystem(Config config){
        Configuration conf= new Configuration();
        conf.set("dfs.support.append","true");
        conf .set("dfs.client.block.write.replace-datanode-on-failure.policy" ,"NEVER" );
        conf .set("dfs.client.block.write.replace-datanode-on-failure.enable" ,"true" );
        FileSystem fileSystem=null;
        try {
            fileSystem = FileSystem.get(new URI(config.getUri()), conf, config.getUser());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return fileSystem;
    }

    public static void create(Config config, FileSystem fileSystem){
        FSDataOutputStream createOutputStream=null;
        String[] fsPaths = config.getFsPaths();
        for (int i = 0; i < fsPaths.length; i++) {
            Path path = new Path(fsPaths[i]);
            try {
                if (!fileSystem.exists(path)){
                    createOutputStream = fileSystem.create(path);
                    createOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
