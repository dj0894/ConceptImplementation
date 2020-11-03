import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyMath{

    public static int add(int x, int y){
        return x+y;
    }
}

class MyTask implements Callable<Integer>{
    int x;
    int y;
    MyTask(int x, int y){
            this.x=x;
            this.y=y;
        }
    @Override
    public Integer call() throws Exception {

        int z=MyMath.add(x,y);
        return  z;
    }
}

public class Main {

    public static void main(String args[]) throws Exception {

        int x=10;
        int y=20;

        ExecutorService exceutor = Executors.newFixedThreadPool(2);
        //using normal way
        Future<Integer> future= exceutor.submit(new MyTask(x,y));
        while(!future.isDone()){
            ; // waiting for the task to be done;
        }
        int z=future.get();

        System.out.println("result is ->"+z);


        //Using java 8 feature----------------

        ExecutorService executor=Executors.newFixedThreadPool(2);
        Future<Integer> future1=exceutor.submit(new Callable<Integer>(){
            public Integer call(){
                return MyMath.add(10,40);
            }
        });

        while(!future1.isDone()){
            ; //wait for task to complete
        }
        int z1=future1.get();

        System.out.println("result is->"+z1);

    }
}
