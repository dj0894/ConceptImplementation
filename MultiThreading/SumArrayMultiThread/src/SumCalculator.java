import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//calculate the sum of elements of array using multithread
//Ex: arr[]={1,2 ,3 ,4,5}
//thread1=1+2;
//thread2=3+4;
//thread3=5
//FinalThread=thread1+thread2+thread3;

public class SumCalculator implements Callable<Integer>  {

    int arr[];
    int start;
    int end;


    SumCalculator(int [] arr, int start, int end){
        this.arr=arr;
        this.start=start;
        this.end=end;

    }

    /*
     * Calculate the sum of the elements of the given partition
     * i.e. from the given start index to end index.
     */
    @Override
    public Integer call() throws Exception {

        System.out.println("Thread"+Thread.currentThread().getName());
        int sum=0;
        for(int i=start;i<=end;i++){
            sum=sum+arr[i];
        }
        System.out.println(sum);
        return sum;
    }
}

class Main<futureObjectList> {

    public static void main(String args[]){
        int arr[]={10,20,20,40,24,50,60,70};


        int blockSize=2;
        int noOfBlocks=(int)Math.ceil(((double)arr.length/blockSize));
        System.out.println(noOfBlocks);

        ExecutorService executor= Executors.newFixedThreadPool(4);
        // Future objects are saved to fetch the results after completion.
        List<Future<Integer>> futureObjectList=new ArrayList<Future<Integer>>();
        Future<Integer> future = null;

        int start=0;
        int end;

        for(int i=1;i<=noOfBlocks;i++) {

            start = (i - 1) * blockSize;
            end = start + blockSize - 1;

            if (end >= arr.length) {
                end = arr.length - 1;
            }

            // Submit the SumCalculatorTask a Callable task
            // which is responsible
            // for calculating the partition sum.
           future=executor.submit(new SumCalculator(arr, start, end));

            // We need it to fetch the computed sum,
            // hence added to the list.
            futureObjectList.add(future);
        }

        int totalSum=0;

        for(int i=0;i<futureObjectList.size();i++){

            // Waiting for future object to complete
            while(!future.isDone()){

                // Pass control to other threads;
                // if any; waiting for CPU.
                Thread.yield();

            }

            try {
               // Get the computed result.
                totalSum=totalSum+future.get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("total sum"+totalSum);

    }







}
