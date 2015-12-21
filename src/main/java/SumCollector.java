import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by al on 21.12.2015.
 */
public class SumCollector {
    final Logger log = LogManager.getLogger(SumCollector.class);

    public int collectSum(Queue<Integer> queue){
        FutureTask<Integer> ft1 = new FutureTask<Integer>(new Summator(queue));
        FutureTask<Integer> ft2 = new FutureTask<Integer>(new Summator(queue));
        FutureTask<Integer> ft3 = new FutureTask<Integer>(new Summator(queue));

        new Thread(ft1).start();
        new Thread(ft2).start();
        new Thread(ft3).start();
        Integer sum = 0;
        try {
            log.debug("ft1 = " + ft1.get());
            log.debug("ft2 = " + ft2.get());
            log.debug("ft3 = " + ft3.get());
            sum = ft1.get() + ft2.get() + ft3.get();
            log.debug("sum = "+ sum);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }
        return sum;
    }
}
