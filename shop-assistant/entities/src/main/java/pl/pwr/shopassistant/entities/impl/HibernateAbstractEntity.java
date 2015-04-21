package pl.pwr.shopassistant.entities.impl;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.shopassistant.entities.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class HibernateAbstractEntity implements Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    protected Integer id;

    @Override
    public Boolean isNew() {
        return getId() == null;
    }
}
