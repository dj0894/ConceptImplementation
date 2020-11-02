import com.example.io.utils.copyFileUtility;

import java.io.IOException;

public class CopyThread implements Runnable {

    String sourceFile;
    String destinationFile;


    CopyThread(String src, String des){
        this.sourceFile=src;
        this.destinationFile=des;
    }

    @Override
    public void run() {
        try {
            copyFileUtility.copyFile(sourceFile,destinationFile);

            System.out.println("Copying "+sourceFile+" to "+destinationFile);
            //System.out.println("Current thead"+Thread.currentThread().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
