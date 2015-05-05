package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;

import java.lang.String; 
import java.util.Date; 
import javax.persistence.Column; 
import javax.persistence.Entity; 
import javax.persistence.EnumType; 
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn; 
import javax.persistence.ManyToOne; 
import javax.persistence.Table; 
import pl.pwr.shopassistant.entities.enums.EventType; 
import java.io.Serializable;

@Entity
@Table(name = "events")
public class Event extends AbstractEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Getter @Setter
    protected User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @Getter @Setter
    protected Product product;

    @Column(name = "uuid")
    @Getter @Setter
    protected String uuid;

    @Column(name = "trigger_timestamp")
    @Getter @Setter
    protected Date triggerTimestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @Getter @Setter
    protected EventType type;
}