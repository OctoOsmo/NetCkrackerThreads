import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by al on 14.12.2015.
 */
public class FeedHedgehogs {
    final private Logger log = LogManager.getLogger(FeedHedgehogs.class);
    private LinkedList<String> basket = new LinkedList<>();
    private ConcurrentLinkedQueue<String> syncBasket = new ConcurrentLinkedQueue<>();
    ExecutorService executorService = Executors.newCachedThreadPool();

    public FeedHedgehogs() {
        basket.add("Apple");
        basket.add("Pineapple");
        basket.add("Watermelon");
        basket.add("Lemon");
        basket.add("Pear");
        basket.add("Hum");
        basket.add("Milk");
        basket.add("Bread");
        basket.add("Strawberry");
        basket.add("Blueberry");
        basket.add("Egg");
        log.debug("Basket contains: ");
        int basketSize = 0;
        for (String s : basket) {
            log.debug(s);
            syncBasket.add(s);
            basketSize++;
        }
        log.debug("total " + basketSize + " items");
    }

    public void feed(int threadNum){
        Thread[] threads = new Thread[threadNum];
        log.info("Not synchronous streams");
        //creating threads
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Thread(new GreedyHedgehog(i, basket));
        }
        //starting threads
        for (int i = 0; i < threadNum; i++) {
            threads[i].start();
        }
        //joining threads
        for (int i = 0; i < threadNum; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
        log.info("Synchronous streams");
        for (int i = 1; i <= threadNum; i++) {
            executorService.submit(new GreedyHedgehog(i, syncBasket));
        }
    }
}
