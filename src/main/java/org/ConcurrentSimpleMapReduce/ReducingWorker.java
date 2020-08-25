package org.ConcurrentSimpleMapReduce;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liubi
 * @date 2020-08-25 19:14
 **/
class ReducingWorker extends Thread{
    private ConcurrentHashMap<String, LinkedList<Integer>> shuffingBuckets;
    private ConcurrentHashMap<String, Integer> reducingBuckets;

    public ReducingWorker(ConcurrentHashMap<String, LinkedList<Integer>> shuffingBuckets, ConcurrentHashMap<String, Integer> reducingBuckets) {
        this.shuffingBuckets = shuffingBuckets;
        this.reducingBuckets = reducingBuckets;
    }

    @Override
    public void run() {
        while (true) {
            while (shuffingBuckets.keys().hasMoreElements()) {
                String key = shuffingBuckets.keys().nextElement();
                LinkedList<Integer> value = shuffingBuckets.get(key);
                for (int v : value) {
                    if (!reducingBuckets.containsKey(key)) {
                        reducingBuckets.put(key, 1);
                        System.out.println("Put " +key+" into reducingBuckets");
                    } else {
                        int tmp = reducingBuckets.get(key);
                        reducingBuckets.put(key, ++tmp);
                        System.out.println("Put " +key+" into reducingBuckets");
                    }
                }
                shuffingBuckets.remove(key);
                System.out.println("Take " +key+" from shufflingBuckets");
            }
        }
    }
}