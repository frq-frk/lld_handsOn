package org.practice.corejava.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class Main2 {
    public static void main(String[] args) {

        CopyOnWriteArrayList<String> l = new CopyOnWriteArrayList<>();

        l.add("Apple");
        l.add("Banana");
        l.add("Orange");

        for(String s : l){
            System.out.println(s);
            l.add("Orange");
        }

//        ReentrantLock lock = new ReentrantLock();
//        ReadWriteLock lock = new ReentrantReadWriteLock();
//        StampedLock lock = new StampedLock();
//
//        long stamp = lock.readLock();
//
//        lock.unlock(stamp);
//
//        long stampw = lock.writeLock();
//        lock.unlock(stampw);
//
//        long stampo = lock.tryOptimisticRead();
//
//        if(lock.validate(stampo)){
//
//        }

//        Semaphore s = new Semaphore(2);
//
//        s.acquire();
//        s.release();

//        await() == wait()
//        signal() = notify()


    }
}

class Resource{


}
