package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shops")
public class Shop extends AbstractEntity implements Serializable {

    @OneToMany(mappedBy = "shop", targetEntity = Order.class, cascade = CascadeType.ALL)
    @Getter @Setter
    protected Set<Order> orders = new HashSet<Order>();

    @Column(name = "name")
    @Getter @Setter
    protected String name;

    @Column(name = "address")
    @Getter @Setter
    protected String address;

    @Column(name = "phone")
    @Getter @Setter
    protected String phone;

    @Column(name = "api_key")
    @Getter @Setter
    protected String apiKey;

    @Column(name = "api_url")
    @Getter @Setter
    protected String apiUrl;

    @Column(name = "payment_method_cash")
    @Getter @Setter
    protected Boolean paymentMethodCash;

    @Column(name = "payment_method_card")
    @Getter @Setter
    protected Boolean paymentMethodCard;

    @Transient
	public Boolean isPaymentMethodCash() {
		return this.paymentMethodCash;
	}

    @Transient
	public Boolean isPaymentMethodCard() {
		return this.paymentMethodCard;
	}
}