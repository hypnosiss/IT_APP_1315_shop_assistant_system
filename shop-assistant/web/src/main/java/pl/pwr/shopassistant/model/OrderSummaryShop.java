package pl.pwr.shopassistant.model;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.shopassistant.entities.Shop;
import pl.pwr.shopassistant.entities.UserProduct;
import pl.pwr.shopassistant.shopapiclient.ShopProduct;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

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

    public OrderSummaryShop(Shop shop, OrderSummaryItem[] items) {
        name = shop.getName();
        address = shop.getAddress();
        products = items;

        price = 0.0;
        for(OrderSummaryItem item : items) {
            price += item.getPrice() * item.getQuantity();
        }
    }
}
