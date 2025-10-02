package org.practice.lld.channel_observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

    }
}

class User{
    int id;
    String name;
    UI ui;

    public User(int id, String name, UI ui) {
        this.id = id;
        this.name = name;
        this.ui = ui;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UI getUi() {
        return ui;
    }
}

interface ObserverUI{
    void sendMsg(String message);
    void display(List<MessageWrapper> messages);
}

class UI implements ObserverUI{
    User user;
    ChannelInterface channelInterface;

    int lastIndex;

    public UI(ChannelInterface channelInterface) {
        this.channelInterface = channelInterface;
    }

    @Override
    public void sendMsg(String message) {
        channelInterface.addMessage(message, user);
    }

    @Override
    public void display(List<MessageWrapper> messages) {
        if(messages.size() <= lastIndex){
            lastIndex = 0;
        }
        for(int i=lastIndex; i<messages.size(); i++){
            System.out.println(messages.get(i).getMessage());
        }
        lastIndex = messages.size();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

interface ChannelInterface{
    void addSubscriber(User user, boolean isAdmin);
    void removeSubscriber(User user);
    void propagateMsg();
    void addMessage(String msg, User u);
}

class ChannelImpl implements ChannelInterface{

    Map<User, Boolean> subscriberList;

    List<MessageWrapper> messages;

    public ChannelImpl(Map<User, Boolean> subscriberList) {
        this.subscriberList = subscriberList;
        this.messages = new ArrayList<>();
    }

    @Override
    public void addSubscriber(User user, boolean isAdmin) {
        subscriberList.put(user, isAdmin);
    }

    @Override
    public void removeSubscriber(User user) {
        subscriberList.remove(user);
    }

    @Override
    public void propagateMsg() {
        for(User u : subscriberList.keySet()){
            u.getUi().display(messages);
        }
    }

    @Override
    public void addMessage(String msg, User user) {
        if(subscriberList.get(user)){
            messages.add(new MessageWrapper(user, msg));
            propagateMsg();
            return;
        }
        System.out.println("User doesn't have appropriate permission to send messages");
    }
}

class Subscriber{
    User user;
    boolean isAdmin;

    public Subscriber(User user, boolean isAdmin) {
        this.user = user;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public User getUser() {
        return user;
    }
}

class MessageWrapper{
    User user;
    String message;

    public MessageWrapper(User user, String message) {
        this.user = user;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }
}

