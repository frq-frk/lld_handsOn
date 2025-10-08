package org.practice.corejava.threads;

import java.util.concurrent.*;

public class TimeoutHandling {
    public static void main(String[] args) {

        ExecutorService executors = Executors.newFixedThreadPool(1);

        Future<?> f1 = executors.submit(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Done task");
        });

        try {
            f1.get(2000, TimeUnit.MILLISECONDS);
        }catch (TimeoutException | InterruptedException | ExecutionException e){
            System.out.println(e);
//            f1.cancel(true);
        }

        executors.shutdown();
        System.out.println("Done main");
    }
}
