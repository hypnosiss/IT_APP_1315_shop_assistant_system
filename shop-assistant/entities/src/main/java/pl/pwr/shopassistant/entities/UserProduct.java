package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;

import java.lang.Integer; 
import java.lang.String; 
import java.util.Date; 
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.EnumType; 
import javax.persistence.Enumerated; 
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id; 
import javax.persistence.JoinColumn; 
import javax.persistence.ManyToOne; 
import javax.persistence.Table; 
import pl.pwr.shopassistant.entities.enums.UserProductStatus; 
import java.io.Serializable;

@Entity
@Table(name = "users_products")
public class UserProduct extends AbstractEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Getter @Setter
    protected User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Getter @Setter
    protected Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Getter @Setter
    protected UserProductStatus status;

    @Column(name = "quantity")
    @Getter @Setter
    protected Integer quantity;

    @Column(name = "name")
    @Getter @Setter
    protected String name;

    @Column(name = "last_order_timestamp")
    @Getter @Setter
    protected Date lastOrderTimestamp;


}