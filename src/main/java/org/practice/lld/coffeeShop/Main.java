package org.practice.lld.coffeeShop;

//import org.junit.gen5.api.Assertions;
//import org.junit.gen5.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CoffeeMachine cm = new CoffeeMachine();

        CoffeeRequest r1 = new CoffeeRequest.Builder(CoffeeTypes.MEDIUM).addFlavour(CoffeeFlavours.CAPUCCHINO).build();

        Coffee c1 = cm.makeCoffee(r1);

        System.out.println("Your Coffee c1 costs : "+c1.getCost());

        CoffeeRequest r2 = new CoffeeRequest.Builder(CoffeeTypes.LARGE)
                .addFlavour(CoffeeFlavours.CHOCOFILL)
                .addFlavour(CoffeeFlavours.CREAMFILL)
                .build();

        Coffee c2 = cm.makeCoffee(r2);

        System.out.println("Your Coffee c2 costs : "+c2.getCost());
    }

//    @Test
//    public void testMethod1(){
//        CoffeeMachine cm = new CoffeeMachine();
//
//        Coffee c1 = cm.makeCoffee(CoffeeTypes.MEDIUM, Arrays.asList(CoffeeFlavours.CAPUCCHINO));
//
//        Assertions.assertEquals(c1.getCost(), 35);
//    }
}

enum CoffeeTypes{
    LARGE, MEDIUM, SMALL;
}

enum CoffeeFlavours{
    CAPUCCHINO, CHOCOFILL, CREAMFILL;
}

class CoffeeRequest{
    CoffeeTypes type;
    List<CoffeeFlavours> flavours;

    public CoffeeRequest(CoffeeTypes type, List<CoffeeFlavours> flavours) {
        this.type = type;
        this.flavours = flavours;
    }

    static class Builder{
        CoffeeTypes type;
        List<CoffeeFlavours> flavours;

        public Builder(CoffeeTypes type) {
            this.type = type;
            flavours = new ArrayList<>();
        }

        public Builder addFlavour(CoffeeFlavours flavour){
            flavours.add(flavour);
            return this;
        }

        public void resetFlavours(){
            flavours = new ArrayList<>();
        }

        public CoffeeRequest build(){
            return new CoffeeRequest(this.type, this.flavours);
        }
    }
}

class CoffeeMachine{
    Coffee makeCoffee(CoffeeRequest request){
        Coffee base = switch (request.type){
            case LARGE -> new LargeCoffee();
            case SMALL -> new SmallCoffee();
            case MEDIUM -> new MediumCoffee();
            default -> throw new IllegalArgumentException("Please Provide a valid coffee type");
        };

        for (CoffeeFlavours f : request.flavours){
            base = switch (f){
                case CAPUCCHINO -> new Capucchino(base);
                case CHOCOFILL -> new ChocoFill(base);
                case CREAMFILL -> new CreamFill(base);
                default -> throw new IllegalArgumentException("Please Provide a valid coffee flavour");
            };
        }

        return base;
    }
}

interface Coffee{
    public int getCost();
}

class LargeCoffee implements Coffee{
    int cost = 20;
    @Override
    public int getCost() {
        return cost;
    }
}

class MediumCoffee implements Coffee{
    int cost = 15;
    @Override
    public int getCost() {
        return cost;
    }
}

class SmallCoffee implements Coffee{
    int cost = 10;
    @Override
    public int getCost() {
        return cost;
    }
}

abstract class FlavouredCoffee implements Coffee{
    Coffee baseCoffee;
    FlavouredCoffee(Coffee baseCoffee) {
        this.baseCoffee = baseCoffee;
    }
}

class Capucchino extends FlavouredCoffee{

    Capucchino(Coffee baseCoffee){
        super(baseCoffee);
    }

    @Override
    public int getCost() {
        return baseCoffee.getCost() + 20;
    }
}

class ChocoFill extends FlavouredCoffee{

    ChocoFill(Coffee baseCoffee){
        super(baseCoffee);
    }
    @Override
    public int getCost() {
        return baseCoffee.getCost() + 30;
    }
}

class CreamFill extends FlavouredCoffee{

    CreamFill(Coffee baseCoffee){
        super(baseCoffee);
    }
    @Override
    public int getCost() {
        return baseCoffee.getCost() + 15;
    }
}

