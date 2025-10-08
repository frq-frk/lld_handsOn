package org.practice.corejava.threads.forkjoinpool;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        ForkJoinPool executor = ForkJoinPool.commonPool();

        Future<Integer> obj = executor.submit(new Task(23, 1445));

        try{
            System.out.println(obj.get());
        } catch (Exception e) {

        }

        System.out.println("End of main thread");

    }
}

class Task extends RecursiveTask<Integer>{

    int start;
    int end;

    public Task(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if(end - start <= 4){

            int ans =0;

            for(int i =start;  i<=end; i++){
                ans += i;
            }
            return ans;
        }

        int mid = (start + end) / 2;

        Task left = new Task(start, mid);
        Task right = new Task(mid+1, end);

        left.fork();
        right.fork();

        int l = left.join();
        int r = right.join();

        return l + r;
    }
}
