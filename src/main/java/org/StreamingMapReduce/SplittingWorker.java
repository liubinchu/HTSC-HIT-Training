package org.StreamingMapReduce;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * @author liubi
 * @date 2020-08-25 19:12
 **/
@Log4j2
class SplittingWorker extends Thread{
    private int workerId;
    private BlockingQueue<String> input;
    private ArrayList<BlockingQueue<String>> splittingBuckets;

    public SplittingWorker(int workerId, BlockingQueue<String> input, ArrayList<BlockingQueue<String>> splittingBuckets) {
        this.workerId = workerId;
        this.input = input;
        this.splittingBuckets = splittingBuckets;
    }

    @Override
    public void run() {
        int bucketId = workerId % splittingBuckets.size();
        while (true) {
            String s = null;
            try {
                s = input.take();
                log.info("Take " +s+" from Input");
                splittingBuckets.get(bucketId).put(s);
                log.info("Put " +s+" into splittingBucket id:"+bucketId);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
