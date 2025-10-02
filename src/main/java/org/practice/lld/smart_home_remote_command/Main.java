package org.practice.lld.smart_home_remote_command;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {

        DoorLock dl = new DoorLock();
        LightController l = new LightController();

        Map<Integer, ICommand> map = new HashMap<>();
        map.put(1, new DoorLockCommand(dl));
        map.put(2, new DoorUnLockCommand(dl));
        map.put(3, new TurnOnLightCommand(l));
        map.put(4, new TurnOffLightCommand(l));

        RemoteController rm = new RemoteController(map);

        rm.pressUndo();
        rm.press(1);
        rm.pressUndo();
        rm.press(3);
        rm.press(1);
        rm.pressUndo();

    }
}

class LightController{
    boolean isOn = false;

    public void turnOn(){
        this.isOn = true;
    }

    public void turnOff(){
        this.isOn = false;
    }
}

class Thermostat{
    int temp;

    public void setTemp(int temp){
        this.temp = temp;
    }
}

class DoorLock{
    boolean isLocked = false;

    public void lock(){
        this.isLocked = true;
    }

    public void unLock(){
        this.isLocked = false;
    }
}

interface ICommand{
    void execute();
    void undo();
}

class TurnOnLightCommand implements ICommand{
    LightController controller;

    TurnOnLightCommand(LightController controller){
        this.controller = controller;
    }

    @Override
    public void execute() {
        System.out.println("Light turned on......");
        controller.turnOn();
    }

    @Override
    public void undo() {
        System.out.println("Light turn on Undone");
        controller.turnOff();
    }
}

class TurnOffLightCommand implements ICommand{
    LightController controller;

    public TurnOffLightCommand(LightController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        System.out.println("Light turned off...");
        controller.turnOff();
    }

    @Override
    public void undo() {
        System.out.println("Light turn off undone...");
        controller.turnOn();
    }
}

class DoorLockCommand implements ICommand{
    DoorLock controller;

    public DoorLockCommand(DoorLock controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        System.out.println("Door Locked....");
        controller.lock();
    }

    @Override
    public void undo() {
        System.out.println("Door Lock un done...");
        controller.unLock();
    }
}

class DoorUnLockCommand implements ICommand{
    DoorLock controller;

    public DoorUnLockCommand(DoorLock controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        System.out.println("Door Unlocked..");
        controller.unLock();
    }

    @Override
    public void undo() {
        System.out.println("Undone Door unlock...");
        controller.lock();
    }
}

class RemoteController{

    Map<Integer, ICommand> map;

    Stack<ICommand> stack;

    public RemoteController(Map<Integer, ICommand> map) {
        this.map = map;
        this.stack = new Stack<>();
    }

    void press(int btn){
        ICommand cmd = map.get(btn);
        cmd.execute();
        stack.push(cmd);
    }

    void pressUndo(){
        if(stack.isEmpty()){
            System.out.println("No previous action to be un done...");
            return;
        }
        ICommand cmd = stack.pop();
        cmd.undo();
    }
}
