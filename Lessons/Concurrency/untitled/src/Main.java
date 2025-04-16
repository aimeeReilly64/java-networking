import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
        public static void main(String[] args) {
            Counter task1 = new Counter("count1");
            Counter task2 = new Counter("count2");
            Counter task3 = new Counter("count3");
            Counter task4 = new Counter("count3");
            Counter task5 = new Counter("count4");
            Counter task6 = new Counter("count5");
            Counter task7 = new Counter("count6");
            //instead of creating each thread use Executors to
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(task1);
            exec.execute(task2);
            exec.execute(new Counter("count New"));
            exec.execute(task3);
            exec.execute(task4);
            exec.execute(task5);
            exec.execute(task6);
            exec.execute(task7);
            //no new tasks can be started but finish what you're doing
            exec.shutdown();
            try {
                exec.awaitTermination(1, TimeUnit.MINUTES);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            //main at end because it needs to wait til everything is finished
            System.out.println("Main has ended");
          //

        }
    }
