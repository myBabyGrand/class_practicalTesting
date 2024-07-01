package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CafeKiosk {

    private LocalTime SHOP_OPEN_TIME = LocalTime.of(10,0);
    private LocalTime SHOP_CLOSE_TIME = LocalTime.of(20,0);

    public static final String OUT_OF_OPEN_TIME_MSG = "주문가능한 시간이 아닙니다.";
    @Getter
    private final List<Beverage> beverageList = new ArrayList<>();
    public void add(Beverage beverage) {
        beverageList.add(beverage);
    }

    public void add(Beverage beverage, int count){
        if(count<= 0){
            throw new IllegalArgumentException("1잔 이상만 담을 수 있습니다.");
        }
        for(int i=0;i<count;i++){
            beverageList.add(beverage);
        }
    }
    public void remove(Beverage beverage){
        beverageList.remove(beverage);
    }

    public void clear(){
        beverageList.clear();
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage beverage : beverageList) {
            totalPrice+=beverage.getPrice();
        }
        return totalPrice;
    }

    public Order createOrder(LocalDateTime localDateTime){
        LocalTime currentTime = localDateTime.toLocalTime();
        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException(OUT_OF_OPEN_TIME_MSG);
        }
        return new Order(LocalDateTime.now(), beverageList);
    }
}
