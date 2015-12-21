import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by al on 14.12.2015.
 */
public class Main {
    public static void main(String[] args) {
        final Logger log = LogManager.getLogger(Main.class);

        //first example
        FeedHedgehogs feedHedgehogs = new FeedHedgehogs();
        feedHedgehogs.feed();
        //waiting for hedgehogs
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        //second example
        Queue<Integer> notSyncQueue = new LinkedList<>();
        ConcurrentLinkedQueue<Integer> syncQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i <= 1000; i++) {
            notSyncQueue.add(i);
            syncQueue.add(i);
        }

        SumCollector sc = new SumCollector();
        Integer sum, syncSum = 0;
        log.info("Not synchronous queue");
        sum = sc.collectSum(notSyncQueue);
        log.info("Sum = " + sum);

        log.info("Synchronous queue");
        syncSum = sc.collectSum(syncQueue);
        log.info("syncSum = " + syncSum);
    }
}
