import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.Callable;

/**
 * Created by al on 21.12.2015.
 */
public class Summator implements Callable<Integer> {
    final Logger log = LogManager.getLogger(Summator.class);
    private Queue<Integer> queue;
    private Integer sum = 0;

    public Summator(Queue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public Integer call() throws Exception {
        try {
            while (!queue.isEmpty())
                sum += queue.remove();
        }catch (NoSuchElementException e){
            log.debug("End of queue has been reached in thread #"+Thread.currentThread().getId()
                    + " queue type is " + queue.getClass().getSimpleName());
        }
        return sum;
    }
}
