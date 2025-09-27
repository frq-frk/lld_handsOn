package org.practice.lld.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        User u1 = new User("Charlie", new UserMobile());
        User u2 = new User("Michale", new UserMobile());

        u1.getUi().setUser(u1);
        u2.getUi().setUser(u2);

        Channel c1 = new ChatChannel();

        c1.addUser(u1);
        c1.addUser(u2);

        u1.getUi().sendMessage(c1,"Hi this is Charlie");
        u2.getUi().sendMessage(c1,"Hey Bro Wassup!!!");
    }
}

class User{
    String id;
    String name;
    UserInterface ui;

    public User(String name, UserInterface ui) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.ui = ui;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserInterface getUi() {
        return ui;
    }

    public void setUi(UserInterface ui) {
        this.ui = ui;
    }
}

interface UserInterface{
    void displayMessage(Channel channel);
    void sendMessage(Channel channel, String msg);
    public User getUser();
    public void setUser(User user);
}

class UserMobile implements UserInterface{

    User user;
    List<MessageWrapper> msgs = new ArrayList<>();

    int lastIndex = 0;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void displayMessage(Channel channel) {
        msgs = channel.getMsgs();
        for(int i=lastIndex; i<msgs.size(); i++){
            if(msgs.get(i).getU().equals(user)) continue;
            System.out.println(user.getName()+"@Message Received from "+msgs.get(i).getU().getName()+" : "+msgs.get(i).getMsg());
        }
        lastIndex = msgs.size();
    }

    @Override
    public void sendMessage(Channel channel, String msg) {
        System.out.println("Sending Message.....");
        channel.sendMessage(user, msg);
    }

}

interface Channel{
    void addUser(User user);
    void removeUser(User user);
    void transferMessage();
    void sendMessage(User user, String msg);
    List<MessageWrapper> getMsgs();
}

class ChatChannel implements Channel{

    List<UserInterface> members = new ArrayList<>();

    List<MessageWrapper> msgs = new ArrayList<>();

    @Override
    public void addUser(User user) {
        members.add(user.getUi());
    }

    @Override
    public void removeUser(User user) {
        members.remove(user.getUi());
    }

    @Override
    public void transferMessage() {
        for(UserInterface ui : members){
            ui.displayMessage(this);
        }
    }

    @Override
    public void sendMessage(User user, String msg) {
        msgs.add(new MessageWrapper(user, msg));
        transferMessage();
    }

    @Override
    public List<MessageWrapper> getMsgs(){
        return this.msgs;
    }
}

class MessageWrapper{
    User u;
    String msg;

    MessageWrapper(User u, String msg){
        this.u = u;
        this.msg = msg;
    }

    public User getU() {
        return u;
    }

    public String getMsg() {
        return msg;
    }
}