import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by al on 21.12.2015.
 */
public class SumCollector {
    final Logger log = LogManager.getLogger(SumCollector.class);

    public int collectSum(Queue<Integer> queue, int threadNum){
        FutureTask<Integer> ftArr[] = new FutureTask[threadNum];
        //creating future tasks and starting threads
        for (int i = 0; i < threadNum; i++) {
            ftArr[i] = new FutureTask<Integer>(new Summator(queue));
            new Thread(ftArr[i]).start();
        }
        //collecting sum
        Integer sum = 0;
        try {
            for (int i = 0; i < ftArr.length; i++) {
                log.debug("ft" + i + " = " + ftArr[i].get());
            }
            for (FutureTask<Integer> ft : ftArr) {
                sum += ft.get();
            }
            log.debug("sum = "+ sum);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }
        return sum;
    }
}
