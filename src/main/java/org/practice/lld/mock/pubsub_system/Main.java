package org.practice.lld.mock.pubsub_system;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

    }
}

interface PubSub{

    public void addMessage(String message);
    public void subscribe(Subscriber subscriber);
    public void cancelSubsciption(Subscriber subscriber);
    public void shutdown();
}

class Topic1PubSub implements PubSub{

    ConcurrentHashMap<Subscriber, ExecutorService> subscriberList;
    List<String> messages;

    public Topic1PubSub() {
        this.subscriberList = new ConcurrentHashMap<>();
        this.messages = new ArrayList<>();
    }

    @Override
    public void addMessage(String message) {
        messages.add(message);
        Iterator<Subscriber> iterator = subscriberList.keySet().iterator();
        while (iterator.hasNext()) {
            Subscriber s = iterator.next();
            PubSubWorker worker = new PubSubWorker(s, 3, message);
            subscriberList.get(s).submit(worker);
        }
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscriberList.putIfAbsent(subscriber, new ThreadPoolExecutor(1, 1, 3000, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy()));
    }

    @Override
    public void cancelSubsciption(Subscriber subscriber) {
        ExecutorService executor = subscriberList.get(subscriber);
        executor.shutdown();
        while (!executor.isShutdown()){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        subscriberList.remove(subscriber);
    }

    @Override
    public void shutdown() {
        Iterator<Subscriber> iterator = subscriberList.keySet().iterator();
        while(iterator.hasNext()){
            Subscriber subscriber = iterator.next();
            subscriberList.get(subscriber).shutdown();
        }
    }
}

class PubSubWorker implements Runnable{

    Subscriber subscriber;
    int maxRetries;
    String message;

    public PubSubWorker(Subscriber subscriber, int maxRetries, String message) {
        this.subscriber = subscriber;
        this.maxRetries = maxRetries;
        this.message = message;
    }

    @Override
    public void run() {
        int retries = 0;
        int delay = 1000;
        while(retries < maxRetries){
            try{
                if(subscriber.consume(message)) return;
            } catch (RuntimeException e) {
//                Do nothing retries take care
            }
            retries++;
            try {
                Thread.sleep((delay * retries) + (int) (Math.random() * 10));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}

interface Subscriber{
    boolean consume(String message);
}

class Sub1Impl implements Subscriber{

    @Override
    public boolean consume(String message) {
        System.out.println("Received message at "+this.getClass().getName()+" : "+ message);
        return true;
    }
}

class Sub1Imp2 implements Subscriber{

    @Override
    public boolean consume(String message) {
        System.out.println("Received message at "+this.getClass().getName()+" : "+ message);
        return true;
    }
}

