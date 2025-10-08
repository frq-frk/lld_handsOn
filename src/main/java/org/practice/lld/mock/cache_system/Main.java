package org.practice.lld.mock.cache_system;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Main {
    public static void main(String[] args) {
        // Step 1: Create and populate the mock DB
        DbMock<Integer, String> db = new DbMock<>();
        db.insert(1, "One");
        db.insert(2, "Two");
        db.insert(3, "Three");
        db.insert(4, "Four");
        db.insert(5, "Five");

        // Step 2: Create LRU eviction strategy with max size 3
        EvictionStrategy<Integer, String> lruStrategy = new LruEvictionStratergy<>(3);

        // Step 3: Create cache layer wrapping the DB
        DataSource<Integer, String> cacheLayer = new CacheLayer1<>(db, lruStrategy);

        // Step 4: Access keys to simulate caching behavior
        System.out.println("Fetching 1: " + cacheLayer.get(1)); // Cache miss → DB
        System.out.println("Fetching 2: " + cacheLayer.get(2)); // Cache miss → DB
        System.out.println("Fetching 3: " + cacheLayer.get(3)); // Cache miss → DB

        System.out.println("Fetching 1 again: " + cacheLayer.get(1)); // Cache hit

        System.out.println("Fetching 4: " + cacheLayer.get(4)); // Cache miss → DB, triggers eviction

        System.out.println("Fetching 2 again: " + cacheLayer.get(2)); // Might be evicted

        // Step 5: Observe eviction behavior
        System.out.println("Fetching 5: " + cacheLayer.get(5)); // Cache miss → DB, triggers another eviction

    }
}

interface DataSource<K, V>{
    V get(K key);
}

interface EvictionStrategy<K, V>{
    void update(Entry<K, V> entry);
    void add(Entry<K, V> entry);
    K evict();
    int getCacheSize();
}

class LruEvictionStratergy<K, V> implements EvictionStrategy<K, V>{

    int MAX_SIZE;

    ConcurrentLinkedDeque<Entry<K, V>> queue;

    public LruEvictionStratergy(int MAX_SIZE) {
        queue = new ConcurrentLinkedDeque<>();
        this.MAX_SIZE = MAX_SIZE;
    }

    @Override
    public void update(Entry<K, V> entry) {
        synchronized (this){
            queue.remove(entry);
            queue.addFirst(entry);
        }
    }

    @Override
    public void add(Entry<K, V> entry) {
        queue.addFirst(entry);
    }

    @Override
    public K evict() {
        synchronized (this){
            Entry<K, V> temp = queue.pollLast();
            return temp.getKey();
        }
    }

    @Override
    public int getCacheSize() {
        return MAX_SIZE;
    }
}

class FifoEvictionStrategy<K, V> implements EvictionStrategy<K, V>{

    int MAX_SIZE;

    ConcurrentLinkedDeque<Entry<K, V>> queue;

    public FifoEvictionStrategy(int MAX_SIZE) {
        this.MAX_SIZE = MAX_SIZE;
        this.queue = new ConcurrentLinkedDeque<>();
    }

    @Override
    public void update(Entry<K, V> entry) {
//        Do nothing
    }

    @Override
    public void add(Entry<K, V> entry) {
        queue.addLast(entry);
    }

    @Override
    public K evict() {
        return Objects.requireNonNull(queue.pollFirst()).getKey();
    }

    @Override
    public int getCacheSize() {
        return MAX_SIZE;
    }
}

class CacheLayer1<K, V> implements DataSource<K, V>{

    DataSource<K, V> db;
    EvictionStrategy<K, V> strategy;

    ConcurrentHashMap<K, Entry<K, V>> cache = new ConcurrentHashMap<>();

    public CacheLayer1(DataSource<K, V> db, EvictionStrategy<K, V> strategy) {
        this.db = db;
        this.strategy = strategy;
    }

    @Override
    public V get(K key) {
       if (cache.containsKey(key)){
           Entry<K, V> cacheObj = cache.get(key);
           strategy.update(cacheObj);
           return  cacheObj.getVal();
       }

       V dbVal = db.get(key);

       if(dbVal != null){
           Entry<K, V> cacheObj = new Entry<>(key, dbVal);
           if(cache.size() >= strategy.getCacheSize()){
               K evictKey = strategy.evict();
               cache.remove(evictKey);
           }
           cache.put(key, cacheObj);
           strategy.add(cacheObj);
       }
       return dbVal;
    }
}

class DbMock<K, V> implements DataSource<K, V>{
    ConcurrentHashMap<K, V> table;

    DbMock(){
        table = new ConcurrentHashMap<>();
    }

    public void insert(K key, V val){
        table.put(key, val);
    }

    @Override
    public V get(K key){
        if(table.containsKey(key)){
            return table.get(key);
        }
        return null;
    }
}

class Entry<K, V>{
    private K key;
    private V val;

    public Entry(K key, V val) {
        this.key = key;
        this.val = val;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getVal() {
        return val;
    }

    public void setVal(V val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return getKey()+" : "+getVal();
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return  true;
        if(!(obj instanceof Entry<?,?>)) return false;

        return Objects.equals(((Entry<?, ?>) obj).getKey(), key);
    }
}
