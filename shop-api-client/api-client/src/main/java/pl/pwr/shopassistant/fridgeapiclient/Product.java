package pl.pwr.shopassistant.fridgeapiclient;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private BigDecimal price;
    private String name;
    private String EAN;


}
