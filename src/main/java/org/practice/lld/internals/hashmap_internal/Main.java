package org.practice.lld.internals.hashmap_internal;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {

        HashMapCustom<Integer, Integer> m = new HashMapCustom<>();

        m.put(1,1);
        m.put(2,2);

        System.out.print(m.get(1));
        System.out.print(m.get(2));

    }
}

class HashMapCustom<K, V>{
    LinkedList<Pair<K, V>>[] map;
    static final int DEFAULT_CAPACITY = (1 << 4);
    static final int MAX_CAPACITY = 1 << 31;

    int cap;

    HashMapCustom(){
        map = new LinkedList [DEFAULT_CAPACITY];
    }

    HashMapCustom(int cap){
        int n = -1 >>> Integer.numberOfLeadingZeros(cap - 1);

        int capacity = n < 0 ? 1 : (n >= MAX_CAPACITY) ? MAX_CAPACITY : n + 1;

        map = new LinkedList[capacity];
    }

    public void put(K key, V val){
        int index = Math.abs(key.hashCode() % map.length);

        if(map[index] == null){
            map[index] = new LinkedList<>();
        }

        for(Pair<K, V> p : map[index]){
            if(p.getKey().equals(key)){
                p.setValue(val);
                return;
            }
        }
        map[index].add(new Pair<>(key, val));
    }

    public V get(K key){
        int index = Math.abs(key.hashCode() % map.length);

        if(map[index] == null) return null;

        for(Pair<K,V> p : map[index]){
            if(p.getKey().equals(key)) return p.getValue();
        }

        return null;
    }
}

class Pair<K, V>{
    K key;
    V value;

    Pair(K key, V value){
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
