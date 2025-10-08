package org.practice.lld.singleton_logger_threadsafe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int i=0;
        for(; i< 200; i++){
            new Thread(() -> {
                Logger logger = Logger.getInstance();
                logger.log("Thread "+Thread.currentThread().getName()+" log here");
            }).start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Logger.getInstance().stopAsyncThread();
    }
}

class Logger{

    private static volatile Logger instance;

    private final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private boolean running = true;

    private Logger(){
        if(instance != null) throw new RuntimeException("Some thing tried to break singleton");
        executor.submit(() -> {
            while (running){
                String msg = queue.poll();
                if(msg != null){
                    System.out.println("["+new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) +"] - "+msg);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public static Logger getInstance(){
        if(instance == null){
            synchronized (Logger.class){
                if(instance == null){
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    synchronized public void log(String message){
        queue.offer(message);
    }

    public void stopAsyncThread(){
        System.out.println("Stopping async log flushing thread");
        this.running = false;
        executor.shutdown();
    }
}
