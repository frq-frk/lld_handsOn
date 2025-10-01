package org.practice.corejava.threads;

import java.util.concurrent.*;

public class Main3 {
    public static void main(String[] args) {

        ThreadPoolExecutor exec = new ThreadPoolExecutor(2, 5, 4000, TimeUnit.MILLISECONDS,
               new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());


        Future<Integer> f = exec.submit(() -> {
            try{
                Thread.sleep(4000);
                System.out.println("Hurray");
            }catch (Exception e){

            }
            return 0;
        });


//        String s = "asas";
//
//        String s1 = new String("asas");
//
//        s.equals(s1);



        try{
            f.get();
        }catch (Exception e){

        }
    }
}


