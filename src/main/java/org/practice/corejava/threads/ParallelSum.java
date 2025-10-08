package org.practice.corejava.threads;

import java.util.concurrent.*;

public class ParallelSum {
    public static void main(String[] args) {
        int [] arr = new int[200];

        for(int i=1; i<=200; i++){
            arr[i-1] = i;
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<Integer> f1 = executor.submit(new Sum(0, 50, arr));
        Future<Integer> f2 = executor.submit(new Sum(50, 100, arr));
        Future<Integer> f3 = executor.submit(new Sum(100, 150, arr));
        Future<Integer> f4 = executor.submit(new Sum(150, 200, arr));

        try {
            int s1 = f1.get();
            int s2 = f2.get();
            int s3 = f3.get();
            int s4 = f4.get();

            System.out.println(s1+s2+s3+s4);
        } catch (InterruptedException | ExecutionException e) {
        }

    }
}

class Sum implements Callable {

    int start, end;

    int [] arr;

    public Sum(int start, int end, int[] arr) {
        this.start = start;
        this.end = end;
        this.arr = arr;
    }

    @Override
    public Object call() throws Exception {
        int sum = 0;
        for(int i=start; i<end; i++){
            sum += arr[i];
        }
        return sum;
    }
}
