package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

public class Config extends AbstractEntity implements Serializable {

    @Getter @Setter
    protected String name;

    @Getter @Setter
    protected String value;
}