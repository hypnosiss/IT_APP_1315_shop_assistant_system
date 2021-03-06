package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends AbstractEntity implements Serializable {

    @OneToMany(mappedBy = "user", targetEntity = Event.class, cascade = CascadeType.ALL)
    @Getter @Setter
    protected Set<Event> events = new HashSet<Event>();

    @OneToMany(mappedBy = "user", targetEntity = Order.class, cascade = CascadeType.ALL)
    @Getter @Setter
    protected Set<Order> orders = new HashSet<Order>();

    @OneToMany(mappedBy = "user", targetEntity = UserProduct.class, cascade = CascadeType.ALL)
    @Getter @Setter
    protected Set<UserProduct> usersProducts = new HashSet<UserProduct>();

    @Column(name = "username")
    @Getter @Setter
    protected String username;

    @Column(name = "password")
    @Getter @Setter
    protected String password;

    @Column(name = "salt")
    @Getter @Setter
    protected String salt;

    @Column(name = "name")
    @Getter @Setter
    protected String name;

    @Column(name = "address")
    @Getter @Setter
    protected String address;

    @Column(name = "phone")
    @Getter @Setter
    protected String phone;


}