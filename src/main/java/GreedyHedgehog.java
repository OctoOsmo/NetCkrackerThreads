import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Created by al on 14.12.2015.
 */
public class GreedyHedgehog implements Runnable{
    final private Logger log = LogManager.getLogger(GreedyHedgehog.class);
    private Integer num;
    Queue<String> basket;
    String food;

    public GreedyHedgehog(Integer num, Queue<String> basket) {
        this.num = num;
        this.basket = basket;
    }

    @Override
    public void run() {
        food = "";
        try {
            food = basket.remove();
            log.info("I'am hedgehog " + num + ", i have a " + food);
        }catch (NoSuchElementException e){
            log.info("I'am hedgehog " + num + " and i have nothing :(");
        }
    }
}
