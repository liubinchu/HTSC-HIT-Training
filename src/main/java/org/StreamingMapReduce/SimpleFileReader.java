package org.StreamingMapReduce;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.BlockingQueue;

/**
 * @author liubi
 * @date 2020-08-25 19:11
 **/
@Log4j2
class SimpleFileReader extends Thread{
    private  String FilePath;
    private  BlockingQueue<String> input;


    public SimpleFileReader(String filePath, BlockingQueue<String> input) {
        this.FilePath = filePath;
        this.input = input;
    }

    @Override
    public void run() {
        log.info("Starting reading File:" + FilePath + " AT " + new Timestamp(System.currentTimeMillis()));
        try {
            BufferedReader in = new BufferedReader(new FileReader(FilePath));
            String str;
            while ((str = in.readLine()) != null) {
                String[] splitRes = str.split("[ ~!@#$%^&*()-=+_{}:;<>,./?]");
                for (String s : splitRes) {
                    if ("".equals(s)) {
                        continue;
                    }
                    this.input.put(s);
                    log.info("Put"+s+"into input");
                }
            }
            log.info("Finishing reading File:" + FilePath + " AT " + new Timestamp(System.currentTimeMillis()));
       } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}