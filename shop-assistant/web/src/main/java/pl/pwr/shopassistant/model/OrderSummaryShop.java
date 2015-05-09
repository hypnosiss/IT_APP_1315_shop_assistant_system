package pl.pwr.shopassistant.model;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.shopassistant.entities.Shop;
import pl.pwr.shopassistant.entities.UserProduct;

import java.io.Serializable;
import java.math.BigInteger;

public class OrderSummaryShop implements Serializable {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String address;

    @Getter @Setter
    private OrderSummaryItem[] products;

    @Getter @Setter
    private double price;

    public OrderSummaryShop() {
    }

    public OrderSummaryShop(Shop shop) {
        name = shop.getName();
        address = shop.getAddress();
        // TODO: shop products
        products = new OrderSummaryItem[0];
        price = 5;
    }
}
