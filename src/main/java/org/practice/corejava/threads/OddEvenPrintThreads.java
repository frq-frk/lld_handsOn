package org.practice.corejava.threads;

public class OddEvenPrintThreads {
    public static void main(String[] args) {

        ValPrinter printer = new ValPrinter();

        new Thread(() -> {
//            for (int i=0; i<5; i++){
//                printer.printOdd();
//            }
            printer.printOdd();
        }).start();

        new Thread(() -> {
//            for (int i=0; i<5; i++){
//                printer.printEven();
//            }
            printer.printEven();
        }).start();

        try {
            Thread.sleep(1000);
            printer.stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class ValPrinter{

    int val = 1;

    boolean isOdd = true;

    boolean isRunning = true;

    synchronized public void printOdd(){
        while (isRunning){
            if(!isOdd){
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName()+" : "+val);
            val++;
            isOdd = false;
            notifyAll();
        }
    }

    synchronized public void printEven(){
        while (isRunning){
            if(isOdd){
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println(Thread.currentThread().getName()+" : "+val);
            val++;
            isOdd = true;
            notifyAll();
        }
    }

    public void stop(){
        isRunning = false;
    }

}
