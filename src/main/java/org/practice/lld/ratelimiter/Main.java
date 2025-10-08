package org.practice.lld.ratelimiter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        User u1 = new User("Charlie");

        LlmModel l1 = new LlmModelV1RateLimiter(new LlmModelV1(), new SlidingWindowRateLimiter());

        System.out.println(l1.prompt(u1, "Hello"));
        System.out.println(l1.prompt(u1, "Hello2"));
        System.out.println(l1.prompt(u1, "Hello3"));
        System.out.println(l1.prompt(u1, "Hello4"));

        try{
            Thread.sleep(3 * 1000);
            System.out.println(l1.prompt(u1, "Hello5"));
            System.out.println(l1.prompt(u1, "Hello6"));
        }catch (InterruptedException e){

        }
    }
}

class User{
    String id;
    String userName;

    public User(String userName) {
        id = UUID.randomUUID().toString();
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}

interface LlmModel{
    String prompt(User user, String promptMsg);
}

class LlmModelV1 implements LlmModel{

    @Override
    public String prompt(User user, String promptMsg) {
        return "Received prompt: "+promptMsg;
    }
}

class LlmModelV1RateLimiter implements LlmModel{

    LlmModelV1 realModel;

    RateLimiterStrategy rateLimiterStrategy;

    public LlmModelV1RateLimiter(LlmModelV1 realModel, RateLimiterStrategy rateLimiterStrategy) {
        this.realModel = realModel;
        this.rateLimiterStrategy = rateLimiterStrategy;
    }

    public void setRateLimiterStrategy(RateLimiterStrategy rateLimiterStrategy) {
        this.rateLimiterStrategy = rateLimiterStrategy;
    }

    @Override
    public String prompt(User user, String promptMsg) {
        return rateLimiterStrategy.isAllowed(user) ? realModel.prompt(user, promptMsg) : "You've reached your limit for now, please try some time later";
    }
}

interface RateLimiterStrategy{
    boolean isAllowed(User user);
}

class FixedWindowRateLimiter implements RateLimiterStrategy{

    static final int WINDOW_SIZE = 3;

    static final int MAX_REQUEST_COUNT = 2;

    Map<User, FixedWindow> map = new HashMap<>();

    @Override
    public boolean isAllowed(User user) {

        long currTime = System.currentTimeMillis();

        FixedWindow currUserWindow = map.get(user);

        if(currUserWindow == null || currTime - currUserWindow.getTimeStamp() > WINDOW_SIZE * 1000L){
            map.put(user, new FixedWindow(1, currTime));
            return true;
        }


        if(currUserWindow.getCount() < MAX_REQUEST_COUNT){
            currUserWindow.setCount(currUserWindow.getCount() + 1);
            map.put(user, currUserWindow);
            return true;
        }

        return false;
    }

    class FixedWindow{
        int count;
        long timeStamp; //first timestamp in window

        public FixedWindow(int count, long timeStamp) {
            this.count = count;
            this.timeStamp = timeStamp;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}

class SlidingWindowRateLimiter implements RateLimiterStrategy{

    static final int WINDOW_SIZE = 3;

    static final int MAX_REQUEST_COUNT = 2;

    Map<User, Deque<Long>> timeWindow = new HashMap<>();

    @Override
    public boolean isAllowed(User user) {
        long currTime = System.currentTimeMillis();
        Deque<Long> q = timeWindow.get(user);
        if(q == null){
            q = new ArrayDeque<>();
            q.addLast(currTime);
            timeWindow.put(user, q);
            return true;
        }

        while (!q.isEmpty() && currTime - q.peekFirst() >= WINDOW_SIZE * 1000L){
            q.pollFirst();
        }

        if(q.size() >= MAX_REQUEST_COUNT){
            return false;
        }
        q.addLast(currTime);
        timeWindow.put(user, q);
        return true;
    }
}

class LeakyBucketRateLimiter implements RateLimiterStrategy{

    @Override
    public boolean isAllowed(User user) {
        return false;
    }
}
