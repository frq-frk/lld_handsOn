package org.practice.lld.internals.lru_cache_internal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[] args) {

        LruCache<String, String> cache = new LruCache<>(2);

        new Thread(() -> {
            cache.store("Key1", "Thread1_key1_Val");
            cache.displayCache();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cache.displayCache();
        }).start();

        new Thread(() -> {
            cache.store("Key2", "Thread2_key2_Val");
            cache.displayCache();
            cache.store("Key1", "Thread2_key1_Val");
            cache.displayCache();
        }).start();

    }
}

class LruCache<K, V>{
    private int size;
    private LinkedList<Entry<K, V>> list;
    private LinkedHashMap<K, Entry<K, V>> map;

    private ReadWriteLock rwLock = new ReentrantReadWriteLock(true);

    LruCache(int size){
        this.size = size;
        list = new LinkedList<>();
        map = new LinkedHashMap<>();
    }

    public V access(K key){
        try {
            rwLock.readLock().lock();
            if(map.containsKey(key)){
                Entry<K, V> temp = map.get(key);
                list.remove(temp);
                list.addFirst(temp);
                return map.get(key).getVal();
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        finally {
            rwLock.readLock().unlock();
        }

        return null;
    }

    public void store(K key, V val){
        try {
            rwLock.writeLock().lock();
            if(map.containsKey(key)){
                Entry<K, V> temp = map.get(key);
                list.remove(temp);
                temp.setVal(val);
                list.addFirst(temp);
                map.put(key, temp);
                return;
            }

            if(list.size() == size){
                Entry<K, V>  removed = list.removeLast();
                map.remove(removed.getKey());
            }

            Entry<K, V> middleVar = new Entry<>(key, val);

            list.addFirst(middleVar);
            map.put(key, middleVar);
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
        finally {
            rwLock.writeLock().unlock();
        }
    }

    synchronized void displayCache(){
        System.out.println(map);
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
}
