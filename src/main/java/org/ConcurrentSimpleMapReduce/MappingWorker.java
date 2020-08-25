package org.ConcurrentSimpleMapReduce;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * @author liubi
 * @date 2020-08-25 19:13
 **/
class MappingWorker extends Thread{
    private int workerId;
    private ArrayList<BlockingQueue<String>> splittingBuckets;
    private ArrayList<BlockingQueue<MappingElement>> mappingBuckets;

    public MappingWorker(int workerId, ArrayList<BlockingQueue<String>> splittingBuckets, ArrayList<BlockingQueue<MappingElement>> mappingBuckets) {
        this.workerId = workerId;
        this.splittingBuckets = splittingBuckets;
        this.mappingBuckets = mappingBuckets;
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
                System.out.println("Take " +s+" from splittingBucket id:"+splittingBucketId);
                mappingBucket.put(new MappingElement(s, 1));
                System.out.println("Put " +s+" into mappingBucket id:"+mappingBucketId);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
