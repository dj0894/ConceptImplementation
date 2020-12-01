import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class MessageQueue{

    List<String> messages;
    int limit;

    public MessageQueue(int limit){
        messages=new ArrayList<>();
        this.limit=limit;
    }

    public boolean isFull(){
        if(messages.size()==limit){
            return true;
        }

       return false;
    }

    public boolean isEmpty(){
        if(messages.size()==0){
            return true;
        }

        return false;
    }

    public synchronized void enque(String msg){
        while(isFull()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        messages.add(msg);
        this.notify();

    }

    public synchronized String dqueue(){
        while(isEmpty()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        String message=messages.remove(0);
        this.notify();
        return message;
    }

}

class ProducerThread extends Thread{
    MessageQueue queue;

    public ProducerThread(MessageQueue queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        for(int i=0;i<=10;i++){
            String msg="Hello-"+i;
            queue.enque(msg);

            System.out.println(msg);

        }
    }
}

class ConsumerThread extends Thread{
    MessageQueue queue;

    public ConsumerThread(MessageQueue queue){
       this.queue=queue;
    }

    @Override
    public void run() {
        for(int i=0;i<=10;i++){
            String message=queue.dqueue();
            System.out.println(message);
        }
    }
}


public class Main {

    public static void main(String args[]){
        MessageQueue messageQueue=new MessageQueue(1);
        Thread producerThread=new ProducerThread(messageQueue);
        Thread consumerThread=new ConsumerThread(messageQueue);

        producerThread.start();
        consumerThread.start();

    }

}


