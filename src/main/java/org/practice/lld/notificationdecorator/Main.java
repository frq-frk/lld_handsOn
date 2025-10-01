package org.practice.lld.notificationdecorator;

public class Main {
    public static void main(String[] args) {

        Notifier n1 = new EncryptionDecorator(new EmailNotifier());
        n1.send("This is n1 message");

        n1 = new TimeStampDecorator(new EncryptionDecorator(new SmsNotifier()));
        n1.send("Again just a random message");
    }
}

interface Notifier{
    void send(String message);
}

class EmailNotifier implements Notifier{

    @Override
    public void send(String message) {
        System.out.println("Sending through Email - Message: "+message);
    }
}

class SmsNotifier implements Notifier{

    @Override
    public void send(String message) {
        System.out.println("Sending through SMS - Message: "+message);
    }
}

class TimeStampDecorator implements Notifier{

    Notifier baseNotifer;

    public TimeStampDecorator(Notifier baseNotifer) {
        this.baseNotifer = baseNotifer;
    }

    @Override
    public void send(String message) {
        baseNotifer.send("["+System.currentTimeMillis()+"] " + message);
    }
}

class EncryptionDecorator implements Notifier{

    Notifier baseNotifer;

    public EncryptionDecorator(Notifier baseNotifer) {
        this.baseNotifer = baseNotifer;
    }

    @Override
    public void send(String message) {
        baseNotifer.send("<Encrypted>" + message);
    }
}