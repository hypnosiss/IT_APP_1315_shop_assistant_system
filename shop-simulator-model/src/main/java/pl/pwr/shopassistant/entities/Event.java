package pl.pwr.shopassistant.entities;

import lombok.Getter;
import lombok.Setter;
import pl.pwr.shopassistant.entities.enums.EventType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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