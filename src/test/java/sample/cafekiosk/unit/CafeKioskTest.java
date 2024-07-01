package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.Local;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.CafeLatte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {


    @Test
    void add_manualTest(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        System.out.println("담긴 음료 수 : "+cafeKiosk.getBeverageList().size());

    }

    @Test
    void add(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new CafeLatte());

        assertThat(cafeKiosk.getBeverageList().size()).isEqualTo(2);
    }

    @Test
    void addSeveralBeverage(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano(), 2);
        assertThat(cafeKiosk.getBeverageList().size()).isEqualTo(2);

        cafeKiosk.clear();
        assertThatThrownBy(()-> cafeKiosk.add(new Americano(), 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1잔 이상만 담을 수 있습니다.");

    }

    @Test
    void remove(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThat(cafeKiosk.getBeverageList()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverageList()).isEmpty();
    }

    @Test
    void clear(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        cafeKiosk.add(new CafeLatte());

        assertThat(cafeKiosk.getBeverageList()).hasSize(2);
        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverageList()).isEmpty();
    }

    @Test
    void createOrderWithCurrentTime(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());
        Order order = cafeKiosk.createOrder(LocalDateTime.of(2024, 7, 1, 10, 0));

        assertThat(order.getBeverageList()).hasSize(1);
        assertThat(order.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void createOrderWithOutsideOpenTIme(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2024, 7, 1, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(cafeKiosk.OUT_OF_OPEN_TIME_MSG);
    }
}