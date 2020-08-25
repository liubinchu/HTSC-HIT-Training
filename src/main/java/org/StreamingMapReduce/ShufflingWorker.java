package org.StreamingMapReduce;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liubi
 * @date 2020-08-25 19:13
 **/
@Log4j2
class ShufflingWorker extends Thread {
    private final int workerId;
    private final ArrayList<BlockingQueue<MappingElement>> mappingBuckets;
    private final ConcurrentHashMap<String, LinkedList<Integer>> shuffingBuckets;

    public ShufflingWorker(int workerId, ArrayList<BlockingQueue<MappingElement>> mappingBuckets, ConcurrentHashMap<String, LinkedList<Integer>> shuffingBuckets) {
        this.workerId = workerId;
        this.mappingBuckets = mappingBuckets;
        this.shuffingBuckets = shuffingBuckets;
    }

    @Override
    public void run() {
        int mappingBucketId = workerId % mappingBuckets.size();
        BlockingQueue<MappingElement> mappingBucket = mappingBuckets.get(mappingBucketId);
        MappingElement mappingElement;
        while (true) {
            try {
                mappingElement = mappingBucket.take();
                log.info("Take " + mappingElement + " from mappingBucket");
                synchronized (this.shuffingBuckets) {
                    if (!shuffingBuckets.containsKey(mappingElement.getKey())) {
                        shuffingBuckets.put(mappingElement.getKey(), new LinkedList<Integer>());
                    } else {
                        shuffingBuckets.get(mappingElement.getKey()).add(mappingElement.getValue());
                    }
                }
                log.info("Put " + mappingElement + " into shufflingBuckets");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
