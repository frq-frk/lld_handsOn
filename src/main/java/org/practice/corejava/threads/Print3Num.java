package org.practice.corejava.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Print3Num {
    public static void main(String[] args) {

        Print p = new Print();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(() -> {
            Context.setThreadVal(1);
            p.print();
        });

        executorService.submit(() -> {
            Context.setThreadVal(2);
            p.print();
        });

        executorService.submit(() -> {
            Context.setThreadVal(0);
            p.print();
        });

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        p.stop();

        executorService.shutdownNow();

    }
}

class Print{

    AtomicInteger number = new AtomicInteger(1);

    boolean isRunning = true;

    synchronized public void print(){
        while (isRunning){
            if(Context.getThreadVal() == number.get() % 3){
                System.out.println("Thread : "+Thread.currentThread().getName()+" - "+number.get());
                number.getAndIncrement();
                notifyAll();
            }else {
                try{
                    if(!isRunning) {
                        notifyAll();
                        break;
                    }
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void stop(){
        this.isRunning = false;
    }

}


class Context{
    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();

    public static int getThreadVal(){
        return threadLocal.get();
    }

    public static void setThreadVal(int val){
        threadLocal.set(val);
    }
}