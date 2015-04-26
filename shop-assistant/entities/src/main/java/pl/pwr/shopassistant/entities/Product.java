package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;

import java.lang.Integer; 
import java.lang.String; 
import java.util.HashSet; 
import java.util.Set; 
import javax.persistence.CascadeType; 
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.FetchType; 
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id; 
import javax.persistence.OneToMany; 
import javax.persistence.Table; 
import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product extends AbstractEntity implements Serializable {

    @OneToMany(mappedBy = "product", targetEntity = Event.class, cascade = CascadeType.ALL)
    @Getter @Setter
    protected Set<Event> events = new HashSet<Event>();

    @OneToMany(mappedBy = "product", targetEntity = OrderProduct.class, cascade = CascadeType.ALL)
    @Getter @Setter
    protected Set<OrderProduct> ordersProducts = new HashSet<OrderProduct>();

    @OneToMany(mappedBy = "product", targetEntity = UserProduct.class, cascade = CascadeType.ALL)
    @Getter @Setter
    protected Set<UserProduct> usersProducts = new HashSet<UserProduct>();

    @Column(name = "ean")
    @Getter @Setter
    protected String ean;

    @Column(name = "brand")
    @Getter @Setter
    protected String brand;

    @Column(name = "name")
    @Getter @Setter
    protected String name;


}