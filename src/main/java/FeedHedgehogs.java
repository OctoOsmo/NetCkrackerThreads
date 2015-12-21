import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
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
        basket.add("apple");
        basket.add("pineapple");
        basket.add("Watermelon");
        basket.add("Lemon");
        basket.add("pear");
        log.debug("Basket contains: ");
        for (String s : basket) {
            log.debug(s);
            syncBasket.add(s);
        }
    }

    public void feed(){
        log.info("Not synchronous streams");
        Thread h1 = new Thread(new GreedyHedgehog(1, basket));
        Thread h2 = new Thread(new GreedyHedgehog(2, basket));
        Thread h3 = new Thread(new GreedyHedgehog(3, basket));
        Thread h4 = new Thread(new GreedyHedgehog(4, basket));
        Thread h5 = new Thread(new GreedyHedgehog(5, basket));
        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();
        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
            h5.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        log.info("Synchronous streams");
        for (int i = 1; i <= 5; i++) {
            executorService.submit(new GreedyHedgehog(i, syncBasket));
        }
    }
}
