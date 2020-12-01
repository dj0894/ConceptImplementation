import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Sample{

    private int x;

    ReadWriteLock rw_lock=new ReentrantReadWriteLock();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void increment(){

       Lock lock =rw_lock.writeLock();
       lock.lock();
        try {

            int y = getX();
            y++;

            // Just for simulation
            try { Thread.sleep(1);
            } catch(Exception e) {
                System.out.println(e);
            }

            setX(y);

        } finally {
            // Unlock
            lock.unlock();
        }


    }
}

class Mythread extends Thread{
    Sample Obj;
    public Mythread(Sample Obj){
        this.Obj=Obj;

    }

    @Override
    public void run() {
        Obj.increment();
    }
}
public class Main {

    public static void main(String arg[]){
        Sample obj =new Sample();
        obj.setX(10);

        Mythread t1=new Mythread(obj);
        Mythread t2=new Mythread(obj);

        t1.start();
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(obj.getX());

    }
}
