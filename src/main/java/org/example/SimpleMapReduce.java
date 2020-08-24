package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Hello world!
 */
public class SimpleMapReduce {

    private LinkedList<String> input;
    private ArrayList<LinkedList<String>> splittingBuckets;
    private ArrayList<ArrayList<MappingElement>> mappingBuckets;
    private Map<String, LinkedList<Integer>> shuffingBuckets;
    private Map<String, Integer> reducingBuckets;
    private Map<String, Integer> result;

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

    public SimpleMapReduce(int splittingBucketNum, int mappingBucketNum) {
        input = new LinkedList<>();

        splittingBuckets = new ArrayList<>(splittingBucketNum);
        for (int i = 0; i < splittingBucketNum; i++) {
            splittingBuckets.add(new LinkedList<String>());
        }
        mappingBuckets = new ArrayList<>(mappingBucketNum);
        for (int i = 0; i < mappingBucketNum; i++) {
            mappingBuckets.add(new ArrayList<MappingElement>());
        }

        shuffingBuckets = new HashMap<>();

        reducingBuckets = new HashMap<>();
    }

    public void readFile(String FilePath) throws IOException {
        System.out.println("Starting reading File:" + FilePath);
        BufferedReader in = new BufferedReader(new FileReader(FilePath));
        String str;
        while ((str = in.readLine()) != null) {
            String[] splitRes = str.split(" ");
            for (String s : splitRes) {
                input.add(s);
            }
        }
        System.out.println("Finishing reading File:" + FilePath);
        System.out.println("File Content:" + input);
    }

    private void splitting() {
        int index = 0;
        for (String s : input) {
            splittingBuckets.get(index % splittingBuckets.size()).add(s);
            index++;
        }
    }

    private void mapping() {
        for (int i = 0; i < splittingBuckets.size(); i++) {
            LinkedList<String> splittingBucket = splittingBuckets.get(i);
            ArrayList<MappingElement> mappingBucket = mappingBuckets.get(i % mappingBuckets.size());
            for (String s : splittingBucket) {
                mappingBucket.add(new MappingElement(s, 1));
            }
        }
    }

    private void shuffing() {
        for (int i = 0; i < mappingBuckets.size(); i++) {
            ArrayList<MappingElement> mappingBucket = mappingBuckets.get(i);
            for (MappingElement mappingElement : mappingBucket) {
                if (!shuffingBuckets.containsKey(mappingElement.getKey())) {
                    shuffingBuckets.put(mappingElement.getKey(), new LinkedList<Integer>());
                }
                shuffingBuckets.get(mappingElement.getKey()).add(mappingElement.getValue());
            }

        }
    }

    private void reducing() {
        for (Map.Entry<String, LinkedList<Integer>> shuffingBucket : shuffingBuckets.entrySet()) {
            String key = shuffingBucket.getKey();
            LinkedList<Integer> value = shuffingBucket.getValue();
            for(int v : value){
                if (!reducingBuckets.containsKey(key)) {
                    reducingBuckets.put(shuffingBucket.getKey(), 1);
                } else {
                    int tmp = reducingBuckets.get(shuffingBucket.getKey());
                    reducingBuckets.put(shuffingBucket.getKey(), ++tmp);
                }
            }

        }
    }

    public void getResult() {
        result = new HashMap<>(reducingBuckets);
        System.out.println(result);
    }

    public void mapReduce() {
        splitting();
        mapping();
        shuffing();
        reducing();
    }

    public static void main(String[] args) throws IOException {
        SimpleMapReduce simpleMapReduce = new SimpleMapReduce(3, 3);
        simpleMapReduce.readFile("text.txt");
        simpleMapReduce.mapReduce();
        simpleMapReduce.getResult();
    }
}
