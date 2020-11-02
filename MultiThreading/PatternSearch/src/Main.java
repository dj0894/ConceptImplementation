import patternsearching.util.PatternFinder;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String args[]) throws ExecutionException, InterruptedException {

        String pattern="hello";

        File dir=new File("./src/SampleFiles");
        File[] files=dir.listFiles();

        PatternFinder pfobj=new PatternFinder();

        //Searching Pattern in file in Serial Method
        long startTime=System.currentTimeMillis();
        for(File file: files){

            List<Integer> lineNumber= null;
            try {
                lineNumber = pfobj.find(file,pattern);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!lineNumber.isEmpty()){

                System.out.println("Pattern "+pattern+" found at line number : "+lineNumber+" in file "+ file.getName());
            }

        }
        System.out.println("Time take to execute in serial Manner in millisecond "+ (System.currentTimeMillis()-startTime)+"\n");
        //End of Serial Method


        //Pattern Searching using MultiThreading

        ExecutorService executor= Executors.newFixedThreadPool(5);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        long startTimeThread=System.currentTimeMillis();

        for(File file: files){

            Future<List<Integer>> future=executor.submit(new Callable<List<Integer>>() {
                public List<Integer> call() throws IOException {
                    List<Integer> lineNumbers1=pfobj.find(file, pattern);

                    return  lineNumbers1;
                }

            });

            resultMap.put(file.getName(), future);
        }

        waitForAll(resultMap);
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            System.out.println(
                    pattern + " found at - " + entry.getValue() +
                            " in file " + entry.getKey());
        }


        System.out.println(
                " Time taken for search in millisecond after using thread- "
                        + (System.currentTimeMillis() - startTimeThread));


    }

    private static void waitForAll(Map<String, Object> resultMap) throws ExecutionException, InterruptedException {
        Set<String> keys=resultMap.keySet();

        for(String key: keys){
            Future<List<Integer>> future=(Future<List<Integer>>)resultMap.get(key);
            while (! future.isDone()) {

                // Passing the CPU to other
                // threads so that they can
                // complete the operation.
                // With out this we are simply
                // keeping the CPU in loop and
                // wasting its time.

                Thread.yield();
            }
            // Replace the future object with the obtained result.
            resultMap.put(key, future.get());


        }

    }
}
