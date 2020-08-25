package org.example;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.*;

/**
 * Hello world!
 */
public class ConcurrentSimpleMapReduce {

    private BlockingQueue<String> input;
    private ArrayList<BlockingQueue<String>> splittingBuckets;
    private ArrayList<BlockingQueue<MappingElement>> mappingBuckets;
    private ConcurrentHashMap<String, LinkedList<Integer>> shuffingBuckets;
    private ConcurrentHashMap<String, Integer> reducingBuckets;
    private ConcurrentHashMap<String, Integer> result;

    @Data
    private class MappingElement {
        String key;
        Integer value;

        public MappingElement(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Integer getValue() {
            return value;
        }
    }

    public ConcurrentSimpleMapReduce(int splittingBucketNum, int mappingBucketNum) {
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

    public class SimpleFileReader implements Runnable {
        public String FilePath;

        public SimpleFileReader(String filePath) {
            FilePath = filePath;
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
                        input.put(s);
                    }
                }
                System.out.println("File Content:" + input);
                System.out.println("Finishing reading File:" + FilePath + " AT " + new Timestamp(System.currentTimeMillis()));
                System.out.println("------------------------------------------------------------------------");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class SplittingWorker implements Runnable {
        private int workerId;

        public SplittingWorker(int workerId) {
            this.workerId = workerId;
        }

        @Override
        public void run() {
            int bucketId = workerId % splittingBuckets.size();
            while (true) {
                String s = null;
                try {
                    s = input.take();
                    splittingBuckets.get(bucketId).put(s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class MappingWorker implements Runnable {
        private int workerId;

        public MappingWorker(int workerId) {
            this.workerId = workerId;
        }

        @Override
        public void run() {
            int splittingBucketId = workerId % splittingBuckets.size();
            int mappingBucketId = workerId % mappingBuckets.size();

            BlockingQueue<String> splittingBucket = splittingBuckets.get(splittingBucketId);
            BlockingQueue<MappingElement> mappingBucket = mappingBuckets.get(mappingBucketId);

            String s;
            while (true) {
                try {
                    s = splittingBucket.take();
                    mappingBucket.put(new MappingElement(s, 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ShufflingWorker implements Runnable {
        private int workerId;

        public ShufflingWorker(int workerId) {
            this.workerId = workerId;
        }

        @Override
        public void run() {
            int mappingBucketId = workerId % mappingBuckets.size();
            BlockingQueue<MappingElement> mappingBucket = mappingBuckets.get(mappingBucketId);
            MappingElement mappingElement;
            while (true) {
                try {
                    mappingElement = mappingBucket.take();
                    if (!shuffingBuckets.containsKey(mappingElement.getKey())) {
                        shuffingBuckets.put(mappingElement.getKey(), new LinkedList<Integer>());
                    } else {
                        shuffingBuckets.get(mappingElement.getKey()).add(mappingElement.getValue());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ReducingWorker implements Runnable {
        @Override
        public void run() {
            while (true) {
                while (shuffingBuckets.keys().hasMoreElements()) {
                    String key = shuffingBuckets.keys().nextElement();
                    LinkedList<Integer> value = shuffingBuckets.get(key);
                    for (int v : value) {
                        if (!reducingBuckets.containsKey(key)) {
                            reducingBuckets.put(key, 1);
                        } else {
                            int tmp = reducingBuckets.get(key);
                            reducingBuckets.put(key, ++tmp);
                        }
                    }
                }
            }
        }
    }

    public void getResult() {
        result = new ConcurrentHashMap<>(reducingBuckets);
        System.out.println("Result: " + result);
    }

    public void mapReduce(String filePath, int splittingWorkerNum, int mappingWorkerNum, int shufflingWorkerNum, int reducingWorkerNum) {
        Timestamp start = new Timestamp(System.currentTimeMillis());

        ExecutorService fileReader = Executors.newSingleThreadExecutor();
        fileReader.execute(new SimpleFileReader(filePath));

        //int splittingWorkerNum = 3;
        ExecutorService splittingWorkers = Executors.newFixedThreadPool(splittingWorkerNum);
        for (int i = 0; i < splittingWorkerNum; i++) {
            splittingWorkers.execute(new SplittingWorker(i));
        }

        //int mappingWorkerNum = 3;
        ExecutorService mappingWorkers = Executors.newFixedThreadPool(mappingWorkerNum);
        for (int i = 0; i < mappingWorkerNum; i++) {
            mappingWorkers.execute(new MappingWorker(i));
        }

        //int shufflingWorkerNum = 4;
        ExecutorService shufflingWorkers = Executors.newFixedThreadPool(shufflingWorkerNum);
        for (int i = 0; i < shufflingWorkerNum; i++) {
            shufflingWorkers.execute(new ShufflingWorker(i));
        }

        //int reducingWorkerNum = 4;
        ExecutorService reducingWorkers = Executors.newFixedThreadPool(reducingWorkerNum);
        for (int i = 0; i < reducingWorkerNum; i++) {
            reducingWorkers.execute(new ReducingWorker());
        }

        Timestamp end = new Timestamp(System.currentTimeMillis());
        System.out.println("consuming time:" + (end.getTime() - start.getTime()) + "ms");
    }

    public static void main(String[] args) {
        ConcurrentSimpleMapReduce concurrentSimpleMapReduce = new ConcurrentSimpleMapReduce(3, 3);
        concurrentSimpleMapReduce.mapReduce("text.txt",3,3,4,4);
        concurrentSimpleMapReduce.getResult();
    }
}
