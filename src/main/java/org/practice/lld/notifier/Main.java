package org.practice.lld.notifier;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Notifier n1 = new EmailNotifier();

        Observer mo = new MobileObserver();
        Observer po = new PcObserver();

        n1.add(mo);
//        n1.add(po);

        n1.updateState("Test state updation 1");

    }
}

interface Notifier{
    void add(Observer o);
    void remove(Observer o);
    void publishNotification();
    String getMsg();
    void updateState(String msg);
}

class EmailNotifier implements Notifier{
    List<Observer> observerList = new ArrayList<>();
    String msg = "";

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public void add(Observer o) {
        observerList.add(o);
        System.out.println("Added "+o.toString()+" to the observer list");
    }

    @Override
    public void remove(Observer o) {
        observerList.remove(o);
        System.out.println("Removed "+o.toString()+" from the observer list");
    }

    @Override
    public void publishNotification() {
        for(Observer o : observerList){
            System.out.println("Sending email notification to "+o.toString());
            o.consume(this);
        }
    }

    @Override
    public void updateState(String msg){
        setMsg(msg);
        publishNotification();
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Email Notifier";
    }
}

interface Observer{
    void consume(Notifier n);
}

class MobileObserver implements Observer{

    @Override
    public void consume(Notifier n) {
        System.out.println("[ Received msg to mobile ] : "+n.getMsg()+" from "+n.toString());
    }

    @Override
    public String toString() {
        return "Mobile Observer";
    }
}

class PcObserver implements Observer{

    @Override
    public void consume(Notifier n) {
        System.out.println("[ Received msg to PC ] : "+n.getMsg()+" from "+n.toString());
    }

    @Override
    public String toString() {
        return "PC Observer";
    }
}
