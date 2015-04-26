package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;

import java.lang.Integer; 
import java.lang.String; 
import java.math.BigDecimal; 
import java.util.Date; 
import java.util.HashSet; 
import java.util.Set; 
import javax.persistence.CascadeType; 
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.FetchType; 
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id; 
import javax.persistence.JoinColumn; 
import javax.persistence.ManyToOne; 
import javax.persistence.OneToMany; 
import javax.persistence.Table; 
import javax.persistence.Temporal; 
import javax.persistence.TemporalType; 
import java.io.Serializable;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity implements Serializable {

    @OneToMany(mappedBy = "order", targetEntity = OrderProduct.class, cascade = CascadeType.ALL)
    @Getter @Setter
    protected Set<OrderProduct> ordersProducts = new HashSet<OrderProduct>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Getter @Setter
    protected User user;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    @Getter @Setter
    protected Shop shop;

    @Column(name = "total_price")
    @Getter @Setter
    protected BigDecimal totalPrice;

    @Temporal(TemporalType.DATE)
    @Column(name = "delivery_date")
    @Getter @Setter
    protected Date deliveryDate;

    @Column(name = "delivery_hour_from")
    @Getter @Setter
    protected Date deliveryHourFrom;

    @Column(name = "delivery_hour_to")
    @Getter @Setter
    protected Date deliveryHourTo;

    @Column(name = "delivery_address")
    @Getter @Setter
    protected String deliveryAddress;

    @Column(name = "order_timestamp")
    @Getter @Setter
    protected Date orderTimestamp;


}