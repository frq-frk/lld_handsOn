package org.practice.corejava.threads.asyncMQ;

import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static void main(String[] args) {

        QueueResource res = new QueueResource(3);

        new Thread(() -> {
            for(int i=0; i<10; i++){
                res.produce();
            }
        }).start();

        new Thread(() -> {
            for(int i=0; i<10; i++){
                res.consume();
            }
        }).start();

    }
}

class QueueResource{

    ArrayBlockingQueue<Integer> queue;

    int MAX_SIZE;


    QueueResource(int n){
        this.MAX_SIZE = n;
        queue = new ArrayBlockingQueue<>(n);
    }

    int i = 1;

    public void produce(){
        try {
//            Thread.sleep(500);
            System.out.println("Producer produced value : "+i);
            queue.put(i++);
        } catch (InterruptedException e) {

        }
    }

    public void consume(){
        try {
            Thread.sleep(1000);
            int val = queue.take();
            System.out.println("Consumer consuming value :" + val);
        } catch (InterruptedException e) {

        }
    }



}
