package pl.pwr.shopassistant.entities.impl;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.shopassistant.entities.ProductEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@Table(name = "products")
public class HibernateProductEntity extends HibernateAbstractEntity implements ProductEntity, Serializable {
    @Column(name = "name")
    @Getter
    @Setter
    protected String name;
}