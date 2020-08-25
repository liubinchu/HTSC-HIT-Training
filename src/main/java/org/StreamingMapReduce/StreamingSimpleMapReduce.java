package org.StreamingMapReduce;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 * @author liubi
 * 流式 mapReduce， 文件可以一直append
 * 如何打印结果 还未实现
 */
public class StreamingSimpleMapReduce {

    private BlockingQueue<String> input;
    private ArrayList<BlockingQueue<String>> splittingBuckets;
    private ArrayList<BlockingQueue<MappingElement>> mappingBuckets;
    private ConcurrentHashMap<String, Integer> reducingBuckets;

    public StreamingSimpleMapReduce(int splittingBucketNum, int mappingBucketNum) {
        input = new LinkedBlockingDeque<>();

        splittingBuckets = new ArrayList<>(splittingBucketNum);
        for (int i = 0; i < splittingBucketNum; i++) {
            splittingBuckets.add(new LinkedBlockingDeque<String>());
        }
        mappingBuckets = new ArrayList<>(mappingBucketNum);
        for (int i = 0; i < mappingBucketNum; i++) {
            mappingBuckets.add(new LinkedBlockingDeque<MappingElement>());
        }

        reducingBuckets = new ConcurrentHashMap<>();
    }

    private void resRecord() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String filePath = "streamingMapReduceRes" + System.currentTimeMillis() + ".json";
                File file = new File(filePath);
                FileWriter fileWriter;
                try {
                    fileWriter = new FileWriter(filePath);
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.writeValue(fileWriter,reducingBuckets );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Timestamp(System.currentTimeMillis()), 1000);
    }

    public void mapReduce(String filePath, int splittingWorkerNum, int mappingWorkerNum, int shufflingWorkerNum, int reducingWorkerNum) {

        new SimpleFileReader(filePath, this.input).start();

        for (int i = 0; i < splittingWorkerNum; i++) {
            new SplittingWorker(i, this.input, this.splittingBuckets).start();
        }

        for (int i = 0; i < mappingWorkerNum; i++) {
            new MappingWorker(i, this.splittingBuckets, this.mappingBuckets).start();
        }

        for (int i = 0; i < reducingWorkerNum; i++) {
            new ReducingWorker(i, this.mappingBuckets, this.reducingBuckets).start();
        }

        resRecord();

    }

    public static void main(String[] args) {
        StreamingSimpleMapReduce concurrentSimpleMapReduce = new StreamingSimpleMapReduce(3, 3);
        concurrentSimpleMapReduce.mapReduce("text.txt", 3, 3, 4, 4);

    }
}
