package org.StreamingMapReduce;

import lombok.extern.log4j.Log4j2;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liubi
 * @date 2020-08-25 19:14
 **/
@Log4j2
class ReducingWorker extends Thread {
    private final ConcurrentHashMap<String, LinkedList<Integer>> shuffingBuckets;
    private ConcurrentHashMap<String, Integer> reducingBuckets;

    public ReducingWorker(ConcurrentHashMap<String, LinkedList<Integer>> shuffingBuckets, ConcurrentHashMap<String, Integer> reducingBuckets) {
        this.shuffingBuckets = shuffingBuckets;
        this.reducingBuckets = reducingBuckets;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (shuffingBuckets) {
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
                        log.info("Put " + key + " into reducingBuckets");
                    }
                    shuffingBuckets.remove(key);
                    log.info("Take " + key + " from shufflingBuckets");
                }
            }
        }
    }
}