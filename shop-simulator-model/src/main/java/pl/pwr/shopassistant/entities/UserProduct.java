package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.shopassistant.entities.enums.UserProductStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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