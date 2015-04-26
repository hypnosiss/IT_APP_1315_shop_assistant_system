package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;

import java.lang.Integer; 
import java.math.BigDecimal; 
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id; 
import javax.persistence.JoinColumn; 
import javax.persistence.ManyToOne; 
import javax.persistence.Table; 
import java.io.Serializable;

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