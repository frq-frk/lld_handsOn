package org.practice.corejava.threads;

public class Main1 {
    public static void main(String[] args) {
        MonitorLock ml = new MonitorLock();
        Thread t1 = new Thread(() -> ml.task1());
        Thread t2 = new Thread(() -> ml.task2());
        Thread t3 = new Thread(() -> ml.task3());

        t1.start();
        t2.start();
        t3.start();
    }
}

class MonitorLock{
    public synchronized void task1(){
        try {
            Thread.sleep(2000);
            System.out.println("Inside task 1 after 1 sec sleep");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void task2(){
        System.out.println("Inside task 2 before sync");
        synchronized(this){
            System.out.println("Inside task 2 in sync");
        }
    }

    public void task3(){
        System.out.println("Inside task 3");
    }
}
