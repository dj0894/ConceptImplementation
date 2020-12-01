import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

class Producer extends Thread{
    BlockingQueue<String>  queue;

    Producer(BlockingQueue queue){
            this.queue=queue;
    }

    @Override
    public void run() {
        for(int i=0;i<=10;i++){
            try {
                queue.put(""+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Hello"+i);
        }
    }
}


class Consumer extends Thread{
    BlockingQueue<String> queue;

    Consumer(BlockingQueue queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        for(int i=0;i<=10;i++){
            try {
                String msg=queue.take();
                System.out.println(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}


public class Main {

    public static  void main(String args[]){

        BlockingQueue<String > bq=new ArrayBlockingQueue<String>(1);

        Thread producerThread=new Producer(bq);
        Thread consumerThread=new Consumer(bq);

        producerThread.start();
        consumerThread.start();


    }
}
