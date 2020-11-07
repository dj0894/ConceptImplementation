package deadlockSolution;




class Writer1 extends Thread{
    Object book;
    Object pen;

    Writer1(Object book, Object pen){
        this.book=book;
        this.pen=pen;
    }

    @Override
    public void run() {

        synchronized(book){//same sequencing of lock as for writer 1
            //to simulate the deadlock situation
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (pen){// same sequencing of lock as for writer 2
                System.out.println("Writer1 is writing ");
            }
        }
    }
}

class Writer2 extends Thread{
    Object book;
    Object pen;

    Writer2(Object book, Object pen){
        this.book=book;
        this.pen=pen;
    }

    @Override
    public void run() {
        synchronized(book){ // same sequencing of lock as for writer 1
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (pen){ // same sequencing of lock as for writer 1
                System.out.println("Writer2 is writing ");
            }
        }
    }
}

public class Main {
    public static void main(String args[]){
        Object book=new Object();
        Object pen=new Object();

        Thread writer1obj=new Writer1(book,pen);
        Thread writer2obj=new Writer2(book, pen);

        writer1obj.start();
        writer2obj.start();
        System.out.println("main is done");
    }
}


//deadlock situation occured as writer 1 waiting for pen and writer 2 waiting for book.
// Main thread is completed but other two thread caught into deadloack.
//it can be solved by lock sequencing.