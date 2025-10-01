package org.practice.lld;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


//        BufferedWriter writer = null;
//        try {
//            writer = new BufferedWriter(new FileWriter("TestFile.txt", false));
//            writer.write("This is the test line");
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }


        Resource r = new Resource();

        Thread t1 = new Thread(() -> {
            for(int i=1; i<=2000; i++){
                r.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i=1; i<=2000; i++){
                r.increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(r.getCounter());


        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());

        ExecutorService e = Executors.newFixedThreadPool(4);

    }
}

class Resource{
//    int counter = 0;


    AtomicInteger counter = new AtomicInteger(0);
    public void increment(){
//        System.out.println("Incremented by "+Thread.currentThread().getName());
        counter.incrementAndGet();
    }

    public int getCounter(){
        return counter.get();
    }
}