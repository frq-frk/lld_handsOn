package org.practice.lld.parking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        List<ParkingLevel> levels = new ArrayList<>();

        for(int lvl=1; lvl<3; lvl++){
            List<ParkingSlot> slots = new ArrayList<>();
            for(int i=0; i<2; i++) slots.add(new ParkingSlot(VehicleTypes.CAR));
            for(int i=0; i<3; i++) slots.add(new ParkingSlot(VehicleTypes.MOTORCYLCE));
            slots.add(new ParkingSlot(VehicleTypes.BUS));
            levels.add(new ParkingLevel(lvl, slots));
        }

        ParkingLot parking = new ParkingLot(levels);

        parking.setStrategy(new NearestFirstParkingStrategy());

        Vehicle b1 = new Bus();

        Vehicle b2 = new Bus();

        Vehicle m1 = new MotorCycle();

        try {
            ParkingLot.Ticket tb1 =  parking.parkVehicle(b1);
            System.out.println("Ticket booked for tb1 at slot : "+ tb1.getSlot().id);
        }catch (RuntimeException e){
            System.out.println(e);
        }

        try {
            ParkingLot.Ticket tb2 =  parking.parkVehicle(b2);
            System.out.println("Ticket booked for tb2 at slot : "+ tb2.getSlot().id);
            parking.vacateVehicle(tb2);
        }catch (RuntimeException e){
            System.out.println(e);
        }

        Vehicle b3 = new Bus();
        try{
            ParkingLot.Ticket tb3 =  parking.parkVehicle(b3);
            System.out.println("Ticket booked for tb3 at slot : "+ tb3.getSlot().id);
        }catch (RuntimeException e){
            System.out.println(e);
        }

        try{
            ParkingLot.Ticket tm1 =  parking.parkVehicle(m1);
            System.out.println("Ticket booked for tm1 at slot : "+ tm1.getSlot().id);
        }catch (RuntimeException e){
            System.out.println(e);
        }

    }
}

enum VehicleTypes{
    MOTORCYLCE, CAR, BUS;
}

abstract class Vehicle{
    String vehId;
    VehicleTypes type;

    Vehicle(VehicleTypes type){
        vehId = UUID.randomUUID().toString();
        this.type = type;
    }

    VehicleTypes getVehicleType(){
        return type;
    }
}

class MotorCycle extends Vehicle{
    MotorCycle(){
        super(VehicleTypes.MOTORCYLCE);
    }
}

class Car extends Vehicle{
    Car(){
        super(VehicleTypes.CAR);
    }
}

class Bus extends Vehicle{
    Bus(){
        super(VehicleTypes.BUS);
    }
}

class ParkingSlot{
    String id;
    VehicleTypes slotType;

    Vehicle vehicle;

    ParkingSlot(VehicleTypes vehType){
        id = UUID.randomUUID().toString();
        this.slotType = vehType;
    }

    boolean isCompatible(VehicleTypes type){
        if(vehicle == null){
            if(type == VehicleTypes.BUS) return slotType == VehicleTypes.BUS;
            if(type == VehicleTypes.CAR) return slotType == VehicleTypes.BUS || slotType == VehicleTypes.CAR;
            return true;
        }
        return false;
    }

    void park(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    void vacate(){
        this.vehicle = null;
    }

}

class ParkingLevel{
    int level;
    List<ParkingSlot> slots;

    ParkingLevel(int level, List<ParkingSlot> slots){
        this.level = level;
        this.slots = slots;
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }
}

interface ParkingStrategy{
    public ParkingSlot findSlot(List<ParkingLevel> levels, VehicleTypes type);
}

class NearestFirstParkingStrategy implements ParkingStrategy{

    @Override
    public ParkingSlot findSlot(List<ParkingLevel> levels, VehicleTypes type) {
        for(ParkingLevel level : levels){
            for(ParkingSlot slot : level.getSlots()){
                if(slot.isCompatible(type)) return slot;
            }
        }
        return null;
    }
}

class FarthestFirstParkingStrategy implements ParkingStrategy{

    @Override
    public ParkingSlot findSlot(List<ParkingLevel> levels, VehicleTypes type) {
        Collections.reverse(levels);
        for(ParkingLevel level : levels){
            Collections.reverse(level.getSlots());
            for(ParkingSlot slot : level.getSlots()){
                if(slot.isCompatible(type)) return slot;
            }
        }
        return null;
    }
}

class ParkingLot{
    List<ParkingLevel> levels;
    ParkingStrategy strategy;

    ParkingLot(List<ParkingLevel> levels){
        this.levels = levels;
    }

    public void setStrategy(ParkingStrategy strategy) {
        this.strategy = strategy;
    }

    Ticket parkVehicle(Vehicle vehicle) throws RuntimeException{
        ParkingSlot slot = strategy.findSlot(levels, vehicle.getVehicleType());
        if(slot == null) throw new RuntimeException("No Spot Available");

        slot.park(vehicle);
        return new Ticket(slot, vehicle);
    }

    void vacateVehicle(Ticket ticket){
        ticket.getSlot().vacate();
        System.out.println("Parking slot "+ticket.getSlot().id+" vacated");
    }

    class Ticket{
        String id;
        ParkingSlot slot;
        Vehicle vehicle;

        Ticket(ParkingSlot slot, Vehicle vehicle){
            id = UUID.randomUUID().toString();
            this.slot = slot;
            this.vehicle = vehicle;
        }

        public ParkingSlot getSlot() {
            return slot;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }
    }
}
