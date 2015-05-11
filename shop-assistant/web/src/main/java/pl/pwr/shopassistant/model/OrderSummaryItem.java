package pl.pwr.shopassistant.model;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.shopassistant.entities.Product;
import pl.pwr.shopassistant.entities.UserProduct;

import java.io.Serializable;
import java.util.List;

public class OrderSummaryItem implements Serializable {

    @Getter @Setter
    private String ean;

    @Getter @Setter
    private int quantity;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String brand;

    public OrderSummaryItem() {
    }

    public OrderSummaryItem(UserProduct userProduct) {
        Product product = userProduct.getProduct();
        ean = product.getEan();
        quantity = userProduct.getQuantity();
        brand = product.getBrand();
        name = userProduct.getName();
        if(null == name || name.equals("")) {
            name = product.getName();
        }
    }
}
