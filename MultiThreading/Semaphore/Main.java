//Semaphore is used to acquire locks for n threads
//for example if you have two Printers  and two printouts can be taken at any given point you need permission for two threads
// which cannot be done using synchronised methods/block.
//so in this case we need to create semaphore




import java.util.concurrent.Semaphore;

class Printer extends Thread{
    int id;
    Semaphore semaphore;
    Printer(int id, Semaphore semaphore){
        this.id=id;
        this.semaphore=semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();// need to acquire for lock

            Thread.sleep(500);
            System.out.println("Printer "+id+" is running");
            semaphore.release();//releasing lock
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}


public class Main {

    public static  void main(String args[]){

        Semaphore sm=new Semaphore(2);//permission for 2 threads is granted
        Thread printer1=new Printer(1,sm);// need to pass same semaphore object to all the printer object
        Thread printer2=new Printer(2,sm);
        Thread printer3=new Printer(3,sm);
        Thread printer4=new Printer(4,sm);

        printer1.start();
        printer2.start();
        printer3.start();
        printer4.start();
    }
}
