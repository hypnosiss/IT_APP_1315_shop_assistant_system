package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "orders_products")
public class OrderProduct extends AbstractEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @Getter @Setter
    protected Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Getter @Setter
    protected Product product;

    @Column(name = "price")
    @Getter @Setter
    protected BigDecimal price;

    @Column(name = "quantity")
    @Getter @Setter
    protected Integer quantity;


}