package org.ConcurrentSimpleMapReduce;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.BlockingQueue;

/**
 * @author liubi
 * @date 2020-08-25 19:11
 **/
class SimpleFileReader extends Thread{
    private  String FilePath;
    private  BlockingQueue<String> input;


    public SimpleFileReader(String filePath, BlockingQueue<String> input) {
        this.FilePath = filePath;
        this.input = input;
    }

    @Override
    public void run() {
        System.out.println("Starting reading File:" + FilePath + " AT " + new Timestamp(System.currentTimeMillis()));
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
                    System.out.println("Put"+s+"into input");
                }
            }
            System.out.println("Finishing reading File:" + FilePath + " AT " + new Timestamp(System.currentTimeMillis()));
          //  System.out.println("File Content:"+this.input);
       } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}