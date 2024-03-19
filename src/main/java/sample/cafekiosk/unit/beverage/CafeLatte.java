package sample.cafekiosk.unit.beverage;

import sample.cafekiosk.unit.beverage.Beverage;

public class CafeLatte implements Beverage {
    @Override
    public String getName() {
        return "카페라떼";
    }

    @Override
    public int getPrice() {
        return 4000;
    }
}
