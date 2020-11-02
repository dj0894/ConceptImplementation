import com.example.io.utils.copyFileUtility;

import java.io.IOException;
import java.util.concurrent.*;

public class Main {

    public static void main(String args[]) throws IOException {

        //copying content of file src1->des1 and src2->des2 in serial manner
        String src1= "a.txt";
        String src2="b.txt";
        String des1="c.txt";
        String des2="d.txt";

        copyFileUtility.copyFile(src1,des1);
        copyFileUtility.copyFile(src2,des2);


        // Using MultiThreading
        CopyThread obj1=new CopyThread(src1, des1);
        CopyThread obj2=new CopyThread(src2, des2);
        Thread thread1=new Thread(obj1);
        Thread thread2=new Thread(obj2);

        thread1.start();
        thread2.start();

        //using ThreadPool
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new CopyThread(src1, des1));
        executor.execute(new CopyThread(src2,des2));







    }
}
