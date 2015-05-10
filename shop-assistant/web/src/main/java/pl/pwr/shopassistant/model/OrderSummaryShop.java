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

    public OrderSummaryShop(Shop shop, List<ShopProduct> shopProducts) {
        name = shop.getName();
        address = shop.getAddress();
        products = new OrderSummaryItem[shopProducts.size()];

        BigDecimal bigPrice = new BigDecimal(0.0);
        for(int i = 0; i < shopProducts.size(); i++) {
            OrderSummaryItem item = new OrderSummaryItem();
            item.setName(shopProducts.get(i).getName());
            item.setBrand(shopProducts.get(i).getName());
            products[i] = item;
            bigPrice = bigPrice.add(shopProducts.get(i).getPrice());
        }

        price = bigPrice.doubleValue();
    }
}
