package org.practice.corejava.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionHandleFuture {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        }, executorService).thenApplyAsync(res ->{
            System.out.println(res);
            throw new RuntimeException("Just for fun");
        }, executorService).exceptionally((e)->{
            System.out.println(e.getMessage());
            return 0;
        }).thenAccept(System.out::println);

        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10;
        }, executorService).thenComposeAsync((res -> CompletableFuture.supplyAsync(() -> res * 2)))
                .thenAccept(System.out::println);

        try{
            Thread.sleep(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();

        System.out.println("Main thread ends here");
    }
}
