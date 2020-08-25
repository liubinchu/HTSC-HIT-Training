package org.SimpleTimer;

import java.sql.Timestamp;
import java.util.*;

/**
 * @author liubi
 * @date 2020-08-24 16:25
 **/
public class SimpleTimer {
    private class Node {
        int number;
        Long timestamp;

        public Node() {
        }

        public Node(int number, Long timestamp) {
            this.number = number;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "number=" + number +
                    ", time=" + new Timestamp(timestamp) +
                    '}';
        }
    }

    private Node[] arrayList = new Node[60];
    private Map<Integer, Integer> map = new HashMap<>(100);
    // key: number
    // value: index in arraylist
    private int latestIndex = -1;
    // record the latest updated index of arraylist, range from 0-59
    private Random random = new Random(System.currentTimeMillis());

    public void add(Long timestamp) {

        int number = random.nextInt(100);

        int index = (int) ((timestamp / 1000) % 60);
        Node element = new Node(number, timestamp);
        arrayList[index] = element;
        if (map.containsKey(number)) {
            arrayList[index] = null;
            // exist then remove
        }
        map.put(number, index);
        latestIndex = index;
    }

    public void print() {
        for (int i = latestIndex; i >= 0; i--) {
            if(arrayList[i]==null){
                continue;
            }
            System.out.println(arrayList[i]);
        }
        for (int i = 59; i > latestIndex; i--) {
            if(arrayList[i]==null){
                continue;
            }
            System.out.println(arrayList[i]);
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        final SimpleTimer simpleTimer = new SimpleTimer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                simpleTimer.add(System.currentTimeMillis());
                simpleTimer.print();
                System.out.println("execute task!  add a random number to array and print it");
            }
        }, new Timestamp(System.currentTimeMillis()), 1000);
    }
}
