package org.StreamingMapReduce;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Hello world!
 * @author liubi
 */
public class StreamingSimpleMapReduce {

    private BlockingQueue<String> input;
    private ArrayList<BlockingQueue<String>> splittingBuckets;
    private ArrayList<BlockingQueue<MappingElement>> mappingBuckets;
    private ConcurrentHashMap<String, LinkedList<Integer>> shuffingBuckets;
    private ConcurrentHashMap<String, Integer> reducingBuckets;
    private ConcurrentHashMap<String, Integer> result;

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

        shuffingBuckets = new ConcurrentHashMap<>();

        reducingBuckets = new ConcurrentHashMap<>();
    }

    public void getResult() {
        result = new ConcurrentHashMap<>(reducingBuckets);
        System.out.println("Result: " + result);
    }

    public void mapReduce(String filePath, int splittingWorkerNum, int mappingWorkerNum, int shufflingWorkerNum, int reducingWorkerNum) {
        Timestamp start = new Timestamp(System.currentTimeMillis());


        Thread fileReader = new SimpleFileReader(filePath, this.input);
        fileReader.start();


        //int splittingWorkerNum = 3;
        for (int i = 0; i < splittingWorkerNum; i++) {
            new SplittingWorker(i, this.input, this.splittingBuckets).start();
        }


        //int mappingWorkerNum = 3;
        for (int i = 0; i < mappingWorkerNum; i++) {
            new MappingWorker(i, this.splittingBuckets, this.mappingBuckets).start();
        }

        //int shufflingWorkerNum = 4;
        for (int i = 0; i < shufflingWorkerNum; i++) {
            new ShufflingWorker(i, this.mappingBuckets, this.shuffingBuckets).start();
        }

        for (int i = 0; i < reducingWorkerNum; i++) {
            new ReducingWorker(this.shuffingBuckets, this.reducingBuckets).start();
        }

       /* System.out.println(shuffingBuckets);
        Timestamp end = new Timestamp(System.currentTimeMillis());
        System.out.println("consuming time:" + (end.getTime() - start.getTime()) + "ms");*/
    }

    public static void main(String[] args) {
        StreamingSimpleMapReduce concurrentSimpleMapReduce = new StreamingSimpleMapReduce(3, 3);
        concurrentSimpleMapReduce.mapReduce("text.txt", 3, 3, 4, 4);
        concurrentSimpleMapReduce.getResult();
    }
}
