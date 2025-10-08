package org.practice.corejava.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncFileDownload {
    public static void main(String[] args) {

        ExecutorService executors = Executors.newFixedThreadPool(3);

        CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return"File1 Downloaded";
        }, executors).thenAccept(System.out::println);

            CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return"File2 Downloaded";
        }, executors).thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> {
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return"File3 Downloaded";
        }, executors).thenAccept(System.out::println);
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        executors.shutdown();

        System.out.println("Main thread end here");
    }
}
