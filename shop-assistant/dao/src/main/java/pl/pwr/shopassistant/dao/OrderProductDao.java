package pl.pwr.shopassistant.dao;

import org.springframework.stereotype.Repository;
import pl.pwr.shopassistant.entities.OrderProduct; 

@Repository
public class OrderProductDao extends AbstractDao<OrderProduct, Integer> {
    public OrderProductDao() {
        super(OrderProduct.class);
    }
}