package pl.pwr.shopassistant.dao;

import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.dao.AbstractDao;
import pl.pwr.shopassistant.entities.Event;

@Repository
public class EventDao extends AbstractDao<Event, Integer> {
    public EventDao() {
        super(Event.class);
    }
}