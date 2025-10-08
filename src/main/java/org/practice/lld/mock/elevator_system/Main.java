package org.practice.lld.mock.elevator_system;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {
    public static void main(String[] args) {

    }
}

enum MovingStatus{
    UP, DOWN, IDLE;
}

enum Direction{
    UP, DOWN;
}

enum ElevatorDoorStatus{
    OPEN, CLOSE;
}

class InsidePanel{
    List<Integer> buttons;
    Elevator elevator;

    public InsidePanel(List<Integer> buttons, Elevator elevator) {
        this.buttons = buttons;
        this.elevator = elevator;
    }

    public void press(int btn){
        elevator.addDest(btn);
    }
}

class Elevator{
    int id;
    int currFloor;
    ElevatorDoorStatus doorStatus;
    MovingStatus movingStatus;

    Queue<Integer> upDest;
    Queue<Integer> downDest;

    public Elevator(int id) {
        this.id = id;
        this.currFloor = 0;
        this.upDest = new PriorityBlockingQueue<>(10);
        this.downDest = new PriorityBlockingQueue<>(10, Comparator.reverseOrder());
    }

    public void addDest(int dest){
        if(dest > currFloor){
            upDest.add(dest);
        }else{
            downDest.add(dest);
        }
    }

    public void start(){
//        a thread starts running which keeps moving the lift up and down
    }
}

class ElevaroController{
    List<Elevator> elevators;
    ElevatorAssignmentStrategy strategy;

    public ElevaroController(List<Elevator> elevators, ElevatorAssignmentStrategy strategy) {
        this.elevators = elevators;
        this.strategy = strategy;
    }

    public void requestElevator(int src, Direction direction){
        Elevator target = strategy.assign(src, direction, elevators);
        target.addDest(src);
    }

    public void requestStop(int dest, Elevator elevator){
        elevator.addDest(dest);
    }
}

interface ElevatorAssignmentStrategy{
    public Elevator assign(int src, Direction direction,List<Elevator> elevators);
}

class FastestArrivalStrategy implements ElevatorAssignmentStrategy{
    @Override
    public Elevator assign(int dest, Direction direction,List<Elevator> elevators) {
//        assigns the elevator that might come fastest.
        return elevators.get(0);
    }
}

class PowerSavingAssignStrategy implements ElevatorAssignmentStrategy{
    @Override
    public Elevator assign(int dest, Direction direction, List<Elevator> elevators) {
//        assigns the elevator that might not consume much resources
        return elevators.get(0);
    }
}

/**
 *
 *
 *
 * */
