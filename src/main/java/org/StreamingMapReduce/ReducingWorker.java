package org.StreamingMapReduce;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liubi
 * @date 2020-08-25 19:13
 **/
@Log4j2
class ReducingWorker extends Thread {
    private final int workerId;
    private final ArrayList<BlockingQueue<MappingElement>> mappingBuckets;
    private final ConcurrentHashMap<String, Integer> reducingBuckets;

    public ReducingWorker(int workerId, ArrayList<BlockingQueue<MappingElement>> mappingBuckets, ConcurrentHashMap<String, Integer> reducingBuckets) {
        this.workerId = workerId;
        this.mappingBuckets = mappingBuckets;
        this.reducingBuckets = reducingBuckets;
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
                synchronized (this.reducingBuckets) {
                    String k = mappingElement.getKey();
                    int v = mappingElement.getValue();
                    if (!reducingBuckets.containsKey(mappingElement.getKey())) {
                        reducingBuckets.put(mappingElement.getKey(), v);
                        log.info("Put " + k + " into reducingBuckets, with frequency "+v);
                    } else {
                        int newV = reducingBuckets.get(k) + v;
                        reducingBuckets.put(mappingElement.getKey(), newV);
                        log.info("Put " + k + " into reducingBuckets, with frequency "+newV);
                    }
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
