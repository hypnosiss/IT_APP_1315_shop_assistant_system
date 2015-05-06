package pl.pwr.shopassistant.fridgeapiclient;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopProduct {
    private BigDecimal price;
    private String name;
    private String brand;
    private String EAN;
}
