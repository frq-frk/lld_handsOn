package org.practice.lld.stackImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        MyStack<Integer> s = new MyStack<>();

        System.out.println(s.isEmpty());

        s.push(2);
        s.push(4);
        s.push(6);
        s.push(3);
        System.out.println(s.peek());
        while (!s.isEmpty()){
            System.out.println(s.pop());
        }

    }
}

class MyStack<T>{

    List<T> arr;

    int position = -1;

    MyStack(){
        arr = new ArrayList<>();
    }

    public void push(T ele){
        arr.add(++position, ele);
    }

    public T pop(){
        return arr.get(position--);
    }

    public T peek(){
        return arr.get(position);
    }

    public boolean isEmpty(){
        return position == -1;
    }

}
