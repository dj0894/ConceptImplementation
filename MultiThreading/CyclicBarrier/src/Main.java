// Cyclic Barrier is used when multiple threads needs to be invoked at the same time


import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class A extends Thread{
    CyclicBarrier cyclicBarrier;

    A(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier=cyclicBarrier;

    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await(); // wait until all parties invoke await(). Here we have two parties A and B.
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        // This will lock exceution until await() in class A is executed
        System.out.println("A begins");
    }
}

class B extends Thread{
    CyclicBarrier cyclicBarrier;

    B(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier=cyclicBarrier;

    }
    @Override
    public void run() {
        try {
            cyclicBarrier.await();    // wait until all parties invoke await(). Here we have two parties A and B.
                                    // This will lock exceution until await() in class A is executed
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("B begins");
    }
}

public class Main {

    public static  void main(String args[]){

        CyclicBarrier cb=new CyclicBarrier(2);// as two threads needs to start together

       new A(cb).start(); //passing object of Cyclic Barrier to constructor

        try {
            Thread.sleep(500); // to simulate the issue whhen something happened so that two thread were not able to start together
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new B(cb).start();

        //After passing object of Cyclic Barrier both the threads will begin together;
    }
}
