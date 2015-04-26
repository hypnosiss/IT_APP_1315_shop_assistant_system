package pl.pwr.shopassistant.dao;

import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.Order; 

@Repository
public class OrderDao extends AbstractDao<Order, Integer> {
    public OrderDao() {
        super(Order.class);
    }
}